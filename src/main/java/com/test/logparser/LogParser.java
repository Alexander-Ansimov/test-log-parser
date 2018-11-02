package com.test.logparser;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogParser {

    public List<LogObject> readAndParse(String fileName) {

        List<String> logLines = readFileByLines(fileName);
        return parseToLogObject(logLines);
    }

    private List<String> readFileByLines(String fileName) {
        List<String> logLines;
        Path path = Paths.get(fileName);
        try {
            logLines = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read log file: " + fileName);
        }
        return logLines;
    }

    private List<LogObject> parseToLogObject(List<String> logLines) {

        logLines.forEach(System.out::println);

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
        return logObjects;
    }
}
