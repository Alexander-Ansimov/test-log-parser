package com.test.logparser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Slf4j
public class Processor {

    @Value("${logfile.name}")
    private String logFileName;
    private LogParser logParser;
    private LogObjectRepository repository;

    public Processor(LogParser logParser, LogObjectRepository repository) {
        this.logParser = logParser;
        this.repository = repository;
    }

    @PostConstruct
    public void process() {
        log.info("start reading and parsing log file");
        List<LogObject> logObjects = logParser.readAndParse("access.log");
        repository.saveAll(logObjects);

        System.out.println(repository.findById(1L));
        System.out.println("SUCCESS      !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

    }

}
