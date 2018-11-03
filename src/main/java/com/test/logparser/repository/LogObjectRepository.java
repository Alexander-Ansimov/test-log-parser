package com.test.logparser.repository;

import com.test.logparser.entity.LogObject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface LogObjectRepository extends CrudRepository<LogObject, Long> {

    @Query("select ip from LogObject " +
            "where timestamp between :startTime and :endTime " +
            "group by(ip) having count(ip) > :threshold")
    List<String> find(@Param("startTime") Timestamp startTime,
                         @Param("endTime") Timestamp endTime,
                         @Param("threshold") Long threshold);
}
