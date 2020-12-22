/*
 * Copyright (C) Sportradar AG. See LICENSE for full license governing this code
 */

package com.yunmu.uof.conn;

import com.sportradar.unifiedodds.sdk.MessageInterest;
import com.sportradar.unifiedodds.sdk.OddsFeed;
import com.sportradar.unifiedodds.sdk.ProducerManager;
import com.sportradar.unifiedodds.sdk.cfg.OddsFeedConfiguration;
import com.sportradar.unifiedodds.sdk.exceptions.InitException;
import com.yunmu.uof.constant.SdkConstants;
import com.yunmu.uof.listener.GlobalEventsListener;
import com.yunmu.uof.listener.MessageListener;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * A basic example demonstrating on how to start the SDK with a single session
 */
@Slf4j
public class SingleSessionSetup {
    private final OddsFeed oddsFeed;

    public SingleSessionSetup(String token) {
        log.info("Running the OddsFeed SDK - single session");
        log.info("Building the configuration using the provided token");
        OddsFeedConfiguration configuration = OddsFeed.getOddsFeedConfigurationBuilder()
                .setAccessToken(token)
                .selectIntegration()
                .setSdkNodeId(SdkConstants.NODE_ID)
                .setDefaultLocale(Locale.CHINESE)
                .build();

        log.info("Creating a new OddsFeed instance");
        oddsFeed = new OddsFeed(new GlobalEventsListener(), configuration);
    }

    public void run(boolean doRecoveryFromTimestamp) throws InitException {
        if (doRecoveryFromTimestamp) {
            setProducersRecoveryTimestamp();
        }
        log.info("Building a simple session which will receive all messages");
        oddsFeed.getSessionBuilder()
                .setMessageInterest(MessageInterest.AllMessages)
                .setListener(new MessageListener("SingleSessionSetup"))
                .build();

        log.info("Opening the feed instance");
        oddsFeed.open();
        log.info("UOF successfully started");
    }

    private void setProducersRecoveryTimestamp() {
        log.info("Setting last message timestamp(used for recovery) for all the active producers to two hours back");

         /*using the timestamp from 2 hours back, in real case scenarios you need to monitor the timestamp for recovery
         with the producerManager.getProducer(producerId).getTimestampForRecovery(); method*/
        long recoveryFromTimestamp = System.currentTimeMillis() - TimeUnit.MILLISECONDS.convert(2, TimeUnit.HOURS);

        ProducerManager producerManager = oddsFeed.getProducerManager();

        producerManager.getActiveProducers().values().forEach(p -> producerManager.setProducerRecoveryFromTimestamp(p.getId(), recoveryFromTimestamp));
    }
}
