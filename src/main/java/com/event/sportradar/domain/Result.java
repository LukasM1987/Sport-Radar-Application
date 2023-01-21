package com.event.sportradar.domain;

import lombok.Data;

@Data
public class Result {

    private String homeTeam;
    private String awayTeam;
    private String city;
    private String stadium;
    private String date;
    private String skirmishResult;
    private double highestProbability;

    public static class ResultBuilder {

        private String homeTeam;
        private String awayTeam;
        private String city;
        private String stadium;
        private String date;
        private String skirmishResult;
        private double highestProbability;

        public ResultBuilder homeTeam(String homeTeam) {
            this.homeTeam = homeTeam;
            return this;
        }

        public ResultBuilder awayTeam(String awayTeam) {
            this.awayTeam = awayTeam;
            return this;
        }

        public ResultBuilder city(String city) {
            this.city = city;
            return this;
        }

        public ResultBuilder stadium(String stadium) {
            this.stadium = stadium;
            return this;
        }

        public ResultBuilder date(String date) {
            this.date = date;
            return this;
        }

        public ResultBuilder skirmishResult(String skirmishResult) {
            this.skirmishResult = skirmishResult;
            return this;
        }

        public ResultBuilder highestProbability(double highestProbability) {
            this.highestProbability = highestProbability;
            return this;
        }

        public Result build() {
            return new Result(homeTeam, awayTeam, city, stadium, date, skirmishResult, highestProbability);
        }
    }

    private Result(String homeTeam, String awayTeam, String city, String stadium, String date, String skirmishResult, double highestProbability) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.city = city;
        this.stadium = stadium;
        this.date = date;
        this.skirmishResult = skirmishResult;
        this.highestProbability = highestProbability;
    }
}
