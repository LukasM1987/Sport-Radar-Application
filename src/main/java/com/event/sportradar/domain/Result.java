package com.event.sportradar.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {

    private String homeTeam;
    private String awayTeam;
    private String city;
    private String stadium;
    private String date;
    private String skirmishResult;
    private double highestProbability;
}
