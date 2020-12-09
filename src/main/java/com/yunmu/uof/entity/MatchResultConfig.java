package com.yunmu.uof.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@Document(collection = "match_result_config")
@EqualsAndHashCode
public class MatchResultConfig implements Serializable {

    @Id
    private String id;

    private String sportId;

    private String desc;

    private List<String> marketIds;

    private Boolean needTimeLine;
}
