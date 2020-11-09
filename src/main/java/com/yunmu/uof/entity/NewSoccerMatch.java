package com.yunmu.uof.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

@Data
@Document(collection = "soccer_match")
@EqualsAndHashCode
public class NewSoccerMatch implements Serializable {

    @Id
    public String matchId;

    @Field("sport_id")
    public String sportId;

    public Date scheduled;

    @Field("status")
    public String status;

    @Field("start_time_tbd")
    public Boolean startTimeTbd;

    @Field("tournament_name")
    public String tournamentName;

    public String home;

    public String away;

    @Field("category_name")
    public String categoryName;
}

