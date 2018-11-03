package com.test.logparser.parser;

import com.test.logparser.entity.LogObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class LogParser {

    public List<LogObject> readAndParse(String fileName) {
        List<String> logLines = readFileByLines(fileName);
        return parseToLogObject(logLines);
    }

    private List<String> readFileByLines(String fileName) {
        log.info("Start reading the log file...");
        List<String> logLines;
        Path path = Paths.get(fileName);
        try {
            logLines = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read log file: " + fileName);
        }
        log.info("Successfully read log file");
        return logLines;
    }

    private List<LogObject> parseToLogObject(List<String> logLines) {
        log.info("Start parsing logs to POJO...");
        List<LogObject> logObjects = new ArrayList<>();
        logLines.forEach(line -> {
            String[] values = line.split("\\|");
            if (values.length == 5) {
                logObjects.add(LogObject.builder()
                        .timestamp(Timestamp.valueOf(values[0]))
                        .ip(values[1])
                        .request(values[2])
                        .status(values[3])
                        .userAgent(values[4])
                        .build());
            }
        });
        log.info("Successfully parse logs to POJO");
        return logObjects;
    }
}
