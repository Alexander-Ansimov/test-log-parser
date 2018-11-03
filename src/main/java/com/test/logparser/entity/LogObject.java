package com.test.logparser.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LogObject {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private Timestamp timestamp;
    private String ip;
    private String request;
    private String status;
    private String userAgent;
}
