package com.event.sportradar.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Result {

    private String homeTeam;
    private String homeTeamCountry;
    private String awayTeam;
    private String awayTeamCountry;
    private String city;
    private String stadium;
    private String date;
    private String skirmishResult;
    private double highestProbability;
}
