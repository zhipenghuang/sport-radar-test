/*
 * Copyright (C) Sportradar AG. See LICENSE for full license governing this code
 */

package com.yunmu.uof.listener;

import com.sportradar.unifiedodds.sdk.OddsFeedListener;
import com.sportradar.unifiedodds.sdk.OddsFeedSession;
import com.sportradar.unifiedodds.sdk.entities.SportEvent;
import com.sportradar.unifiedodds.sdk.oddsentities.*;
import com.yunmu.uof.entity.markets.Market;
import com.yunmu.uof.entity.markets.Outcome;
import com.yunmu.uof.entity.markets.*;
import com.yunmu.uof.service.MarketService;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.*;

/**
 * A basic feed listener implementation which outputs the data to the provided log
 */
@Slf4j
public class MessageListener implements OddsFeedListener {

    private final MarketService msgService;


    public MessageListener(String listener_version, MarketService msgService) {
        this.msgService = msgService;
    }

    /**
     * Any kind of odds update, or betstop signal results in an OddsChanges Message.
     *
     * @param sender      the session
     * @param oddsChanges the odds changes message
     */
    @Override
    public void onOddsChange(OddsFeedSession sender, OddsChange<SportEvent> oddsChanges) {
        log.info("Received odds change for: " + oddsChanges.getEvent().getId().toString() + ",markets:" + oddsChanges.getMarkets().size());
        // Now loop through the odds for each market
        SoccerMatch soccerMatch = new SoccerMatch();
        soccerMatch.setMatchId(oddsChanges.getEvent().getId().toString());
        List<Market> markets = new ArrayList<>();
        for (MarketWithOdds marketOdds : oddsChanges.getMarkets()) {
            // Now loop through the outcomes within this particular market
            Market market = new Market();
            market.setId(marketOdds.getId());
            market.setName(marketOdds.getName(Locale.CHINESE));
            market.setIsFavourite(marketOdds.isFavourite());
            market.setStatus(marketOdds.getStatus().name());

            for (String key : marketOdds.getSpecifiers().keySet()) {
                market.setSpecifiers(key + "=" + marketOdds.getSpecifiers().get(key));
            }
            List<Outcome> outcomes = new ArrayList<>();
            log.info("Received odds information for:'" + marketOdds.getName(Locale.CHINESE) + "'");
            log.info("Market status is: " + marketOdds.getStatus());
            for (OutcomeOdds outcomeOdds : marketOdds.getOutcomeOdds()) {
//                String outcomeDesc = outcomeOdds.getName();
                Outcome outcome = new Outcome();
                outcome.setId(outcomeOdds.getId());
                outcome.setIsActive(outcomeOdds.isActive());
                outcome.setIsPlayerOutcome(outcomeOdds.isPlayerOutcome());
                outcome.setOdds(BigDecimal.valueOf(outcomeOdds.getOdds()));
                outcome.setName(outcomeOdds.getName());
                outcome.setProbability(BigDecimal.valueOf(outcomeOdds.getProbability()));
                outcomes.add(outcome);
                log.info("Outcome " + outcomeOdds.getName() + "'s odds is " + outcomeOdds.getOdds() + " , "
                        + outcomeOdds.getProbability());
            }
            market.setOutcomes(outcomes);
            markets.add(market);
        }
        soccerMatch.setMarkets(markets);

        SoccerMatchDb soccerMatchDb = new SoccerMatchDb();
        soccerMatchDb.setMatchId(soccerMatch.getMatchId());

        Map<Integer, MarketDb> map = new HashMap<>();
        for (Market market : soccerMatch.getMarkets()) {
            MarketDb marketDb = map.get(market.getId());
            if (marketDb == null) {
                marketDb = new MarketDb();
                marketDb.setId(market.getId());
                marketDb.setIsFavourite(market.getIsFavourite());
                marketDb.setName(market.getName());
                marketDb.setStatus(market.getStatus());
            }
            List<Specifier> specifiers = marketDb.getSpecifiers();
            if (specifiers == null) {
                specifiers = new ArrayList<>();
            }
            Specifier specifier = new Specifier();
            specifier.setName(market.getSpecifiers());
            specifier.setOutcomes(market.getOutcomes());
            specifiers.add(specifier);

            marketDb.setSpecifiers(specifiers);

            map.put(marketDb.getId(), marketDb);
        }

        List<MarketDb> marketDbs = new ArrayList<>();
        for (Integer marketId : map.keySet()) {
            marketDbs.add(map.get(marketId));
        }
        soccerMatchDb.setMarkets(marketDbs);
        msgService.handleMarkets(soccerMatchDb);
    }

    /**
     * Send to rapidly suspend a set of markets (often all)
     *
     * @param sender  the session
     * @param betStop the betstop message
     */
    @Override
    public void onBetStop(OddsFeedSession sender, BetStop<SportEvent> betStop) {
        log.info("Received betstop for sport event " + betStop.getEvent());
    }

    /**
     * The onBetSettlement callback is received whenever a BetSettlement message is received. It
     * contains information about what markets that should be settled how. All markets and outcomes
     * that you have received odds changes messages for at some point in time you will receive
     * betsettlement messages for at some later point in time. That is if you receive odds for
     * outcome X for market Y, you will at a later time receive a BetSettlement message that
     * includes outcome X for market Y.
     *
     * @param sender    the session
     * @param clearBets the BetSettlement message
     */
    @Override
    public void onBetSettlement(OddsFeedSession sender, BetSettlement<SportEvent> clearBets) {
        log.info("Received bet settlement for sport event " + clearBets.getEvent());

        // Iterate through the betsettlements for each market
        for (MarketWithSettlement marketSettlement : clearBets.getMarkets()) {
            // Then iterate through the result for each outcome (win or loss)
            for (OutcomeSettlement result : marketSettlement.getOutcomeSettlements()) {
                if (result.isWinning())
                    log.info("Outcome " + result.getId() + " is a win");
                else
                    log.info("Outcome " + result.getId() + " is a loss");
            }
        }
    }

    /**
     * If a BetSettlement was generated in error, you may receive a RollbackBetsettlement and have
     * to try to do whatever you can to undo the BetSettlement if possible.
     *
     * @param sender                the session
     * @param rollbackBetSettlement the rollbackBetSettlement message referring to a previous
     *                              BetSettlement
     */
    @Override
    public void onRollbackBetSettlement(OddsFeedSession sender, RollbackBetSettlement<SportEvent> rollbackBetSettlement) {
        log.info("Received rollback betsettlement for sport event " + rollbackBetSettlement.getEvent());
    }

    /**
     * If the markets were cancelled you may receive a
     * {@link BetCancel} describing which markets were
     * cancelled
     *
     * @param sender    the session
     * @param betCancel A {@link BetCancel} instance
     *                  specifying which markets were cancelled
     */
    @Override
    public void onBetCancel(OddsFeedSession sender, BetCancel<SportEvent> betCancel) {
        log.info("Received bet cancel for sport event " + betCancel.getEvent());
    }

    /**
     * If the bet cancellations were send in error you may receive a
     * {@link RollbackBetCancel} describing the
     * erroneous cancellations
     *
     * @param sender      the session
     * @param rbBetCancel A {@link RollbackBetCancel}
     *                    specifying erroneous cancellations
     */
    @Override
    public void onRollbackBetCancel(OddsFeedSession sender, RollbackBetCancel<SportEvent> rbBetCancel) {
        log.info("Received rollback betcancel for sport event " + rbBetCancel.getEvent());
    }

    /**
     * If there are important fixture updates you will receive fixturechange message. The thinking
     * is that most fixture updates are queried by you yourself using the SportInfoManager. However,
     * if there are important/urgent changes you will also receive a fixture change message (e.g. if
     * a match gets delayed, or if Sportradar for some reason needs to stop live coverage of a match
     * etc.). This message allows you to promptly respond to such changes
     *
     * @param sender        the session
     * @param fixtureChange the SDKFixtureChange message - describing what sport event and what type
     *                      of fixture change
     */
    @Override
    public void onFixtureChange(OddsFeedSession sender, FixtureChange<SportEvent> fixtureChange) {
        log.info("Received fixture change for sport event:'" +
                fixtureChange.getEvent().getId().toString() + "'---" +
                fixtureChange.getChangeType() + "---" +
                new String(fixtureChange.getRawMessage()));
    }

    /**
     * This handler is called when the SDK detects that it has problems parsing a certain message.
     * The handler can decide to take some custom action (shutting down everything etc. doing some
     * special analysis of the raw message content etc) or just ignore the message. The SDK itself
     * will always log that it has received an unparseable message and will ignore the message so a
     * typical implementation can leave this handler empty.
     *
     * @param sender     the session
     * @param rawMessage the raw message received from Betradar
     * @param event      if the SDK was able to extract the event this message is for it will be here
     *                   otherwise null
     * @deprecated in favour of {{@link #onUnparsableMessage(OddsFeedSession, UnparsableMessage)}} from v2.0.11
     */
    @Override
    @Deprecated
    public void onUnparseableMessage(OddsFeedSession sender, byte[] rawMessage, SportEvent event) {
        if (event != null) {
            log.info("Problems deserializing received message for event " + event.getId());
        } else {
            log.info("Problems deserializing received message"); // probably a system message deserialization failure
        }
    }

    /**
     * This handler is called when the SDK detects that it has problems parsing/dispatching a message.
     * The handler can decide to take some custom action (shutting down everything etc. doing some
     * special analysis of the raw message content etc) or just ignore the message. The SDK itself
     * will always log that it has received an unparseable message.
     *
     * @param sender            the session
     * @param unparsableMessage A {@link UnparsableMessage} instance describing the message that had issues
     * @since v2.0.11
     */
    @Override
    public void onUnparsableMessage(OddsFeedSession sender, UnparsableMessage unparsableMessage) {
        Producer possibleProducer = unparsableMessage.getProducer(); // the SDK will try to provide the origin of the message

        if (unparsableMessage.getEvent() != null) {
            log.info("Problems detected on received message for event " + unparsableMessage.getEvent().getId());
        } else {
            log.info("Problems detected on received message"); // probably a system message deserialization failure
        }
    }
}
