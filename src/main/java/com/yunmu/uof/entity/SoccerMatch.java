package com.yunmu.uof.entity;

import com.yunmu.uof.entity.secondary.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@Data
@Document(collection = "soccer_match")
@EqualsAndHashCode
public class SoccerMatch implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    public String matchId;

    @Field("sport_id")
    public String sportId;

    public Date scheduled;

    public String name;

    @Field("match_status")
    public MatchStatus matchStatus;

    @Field("booking_status")
    public String bookingStatus;

    @Field("event_status")
    public String eventStatus;

    @Field("start_time_tbd")
    public Boolean startTimeTbd;

    public Season season;

    @Field("tournament_round")
    public TournamentRound tournamentRound;

    public Tournament tournament;

    @Field("delayed_info")
    public DelayedInfo delayedInfo;

    public Competitor home;

    public Competitor away;

    public Fixture fixture;

    public Venue venue;

    public SoccerMatch() {
        this.tournament = new Tournament();
        this.matchStatus = new MatchStatus();
        this.tournamentRound = new TournamentRound();
        this.season = new Season();
        this.home = new Competitor();
        this.away = new Competitor();
        this.venue = new Venue();
        this.delayedInfo = new DelayedInfo();
        this.fixture = new Fixture();
        this.fixture.tvChannels = new ArrayList<>();
        this.fixture.scheduledStartTimeChanges = new ArrayList<>();
        this.fixture.replacedBy = new ReplacedBy();
        this.fixture.coverageInfo = new CoverageInfo();
        this.fixture.producerInfo = new ProducerInfo();
        this.fixture.producerInfo.streamingChannels = new ArrayList<>();
        this.fixture.producerInfo.producerInfoLinks = new ArrayList<>();
    }
}

