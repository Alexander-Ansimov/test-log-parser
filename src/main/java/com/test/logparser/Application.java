package com.test.logparser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication
public class Application implements ApplicationRunner {

    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    public static final String DURATION = "duration";
    public static final String THRESHOLD = "threshold";
    public static final String DAILY = "daily";
    public static final String HOURLY = "hourly";

    @Autowired
    private Processor processor;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Map<String, String> parameters = initParameters(args);
//        processor.startProcess(parameters);
    }

    private Map<String, String> initParameters(ApplicationArguments args) {

        if (!args.containsOption(START_DATE) || !args.containsOption(DURATION) || !args.containsOption(THRESHOLD)) {
            throw new RuntimeException("All arguments must be defined !");
        }

        Map<String, String> parameters = new HashMap<>();
        parameters.put(START_DATE, args.getOptionValues(START_DATE).get(0).replace(".", " "));
        parameters.put(DURATION, args.getOptionValues(DURATION).get(0));
        parameters.put(THRESHOLD, args.getOptionValues(THRESHOLD).get(0));
        enrichEndDate(parameters);

        parameters.forEach((k, v) -> log.info(k + " : " + v));
        return parameters;
    }

    private void enrichEndDate(Map<String, String> parameters) {
        if (parameters.get(DURATION).equals(DAILY)) {
            parameters.put(END_DATE, addOneDay(parameters.get(START_DATE)));
        } else if (parameters.get(DURATION).equals(HOURLY)) {
            parameters.put(END_DATE, addOneHour(parameters.get(START_DATE)));
        } else {
            throw new RuntimeException("In threshold key you can use only 'daily' or 'hourly'");
        }
    }

    private String addOneHour(String startDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(startDate, formatter);
        String resultTime = startTime.plusHours(1).toString();

        System.out.println("ADDED ONE HOUR  ->  " + resultTime);

        return null;
    }

    private String addOneDay(String startDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd hh.mm.ss");
        LocalDateTime startTime = LocalDateTime.parse(startDate.replace(":", "."), formatter);
        String resultTime = startTime.plusDays(1).toString();

        System.out.println("ADDED ONE DAY  ->  " + resultTime);

        return null;
    }
}
