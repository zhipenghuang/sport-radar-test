package com.yunmu.uof.repository;

import com.yunmu.uof.entity.SoccerMatch_temp;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SoccerMatchRepository extends MongoRepository<SoccerMatch_temp, String> {
}

