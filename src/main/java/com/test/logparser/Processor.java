package com.test.logparser;

import com.test.logparser.entity.BannedIp;
import com.test.logparser.entity.LogObject;
import com.test.logparser.parser.LogParser;
import com.test.logparser.repository.BannedIpRepository;
import com.test.logparser.repository.LogObjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class Processor {

    @Value("${logfile.name}")
    private String logFileName;
    private LogParser logParser;
    private LogObjectRepository logObjectRepository;
    private BannedIpRepository ipRepository;

    public Processor(LogParser logParser, LogObjectRepository logObjectRepository, BannedIpRepository ipRepository) {
        this.logParser = logParser;
        this.logObjectRepository = logObjectRepository;
        this.ipRepository = ipRepository;
    }

    @PostConstruct
    public void process() {

        List<LogObject> logObjects = logParser.readAndParse(logFileName);
        logObjectRepository.saveAll(logObjects);
        log.info("Logs are saved into DB");

        List<String> ips = logObjectRepository.find(Timestamp.valueOf("2017-01-01 00:00:41"),
                Timestamp.valueOf("2017-01-01 00:50:37"), 10L);

        System.out.println("Banned IP's:");
        ips.forEach(System.out::println);

        List<BannedIp> bannedIps = new ArrayList<>();
        ips.forEach(ip -> bannedIps
                .add(BannedIp.builder()
                        .ip(ip)
                        .reason("This ip make more then limit requests in threshold period")
                        .build()));

        ipRepository.saveAll(bannedIps);
        log.info("Banned IP's are saved in DB");

        System.out.println("SUCCESS      !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}
