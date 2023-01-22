package com.event.sportradar.service;

import com.event.sportradar.domain.Competitor;
import com.event.sportradar.domain.Result;
import com.event.sportradar.input.DataStorage;
import com.event.sportradar.output.Printer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SportRadarService {

    private final DataStorage dataStorage;
    private static final List<String> teamNames = new ArrayList<>();
    private static final List<String> teamCountries = new ArrayList<>();
    private static final List<Result> results = new ArrayList<>();
    private static final Set<String> teamsSet = new HashSet<>();
    private static final int DATE_LENGTH = 19;
    private static final Integer DEFAULT_RANGE = 10;
    private static final String HOME_TEAM_WIN = "home team will win";
    private static final String AWAY_TEAM_WIN = "away team will win";
    private static final String RESULT_DRAW = "draw";

    public void compareProbability(Integer range) {
        if (range == null) {
            range = DEFAULT_RANGE;
        }
        if (isRangeValid(range)) {
            System.err.println("Range is to big, or is equals 0, or less then 0.");
            return;
        }
        for (int i = 0; i < dataStorage.getEvents().size(); i++) {
            addTeamsInfo(i);
            compareProbability(i);
        }
        sortListByProbability();
        Printer.printResults(range, results);
    }

    private void addTeamsInfo(int i) {
        if (i == 0) {
            List<Competitor> competitors = dataStorage.getEvents().get(i).getCompetitors();
            for (int j = 0; j < competitors.size(); j++) {
                teamNames.add(dataStorage.getEvents().get(i).getCompetitors().get(j).getName());
                teamCountries.add(dataStorage.getEvents().get(i).getCompetitors().get(j).getCountry());
            }
        }
    }

    public void printAllCompetitorsAlphabetically() {
        for (int i = 0; i < dataStorage.getEvents().size(); i++) {
            for (int j = 0; j < dataStorage.getEvents().get(i).getCompetitors().size(); j++) {
                teamsSet.add(dataStorage.getEvents().get(i).getCompetitors().get(j).getName());
            }
        }
        TreeSet<String> treeSet = new TreeSet<>(teamsSet);
        Printer.printCompetitors(treeSet);
    }

    private void compareProbability(int i) {
        if (getProbabilityHomeTeamWinner(i) > getProbabilityAwayTeamWinner(i)
                && getProbabilityHomeTeamWinner(i) > getProbabilityDraw(i)) {
            addResult(i, HOME_TEAM_WIN, getProbabilityHomeTeamWinner(i));
        } else if (getProbabilityAwayTeamWinner(i) > getProbabilityHomeTeamWinner(i)
                && getProbabilityAwayTeamWinner(i) > getProbabilityDraw(i)) {
            addResult(i, AWAY_TEAM_WIN, getProbabilityAwayTeamWinner(i));
        } else {
            addResult(i, RESULT_DRAW, getProbabilityDraw(i));
        }
    }

    private double getProbabilityDraw(int i) {
        return dataStorage.getEvents().get(i).getProbabilityDraw();
    }

    private double getProbabilityAwayTeamWinner(int i) {
        return dataStorage.getEvents().get(i).getProbabilityAwayTeamWinner();
    }

    private double getProbabilityHomeTeamWinner(int i) {
        return dataStorage.getEvents().get(i).getProbabilityHomeTeamWinner();
    }

    private void addResult(int i, String skirmishResult, double probability) {
        int homeTeam = calculateHomeTeamIndex(i);
        int awayTeam = calculateAwayTeamIndex(homeTeam);
        if (dataStorage.getEvents().get(i).getVenue() == null) {
            results.add(new Result.ResultBuilder()
                    .homeTeam(teamNames.get(homeTeam))
                    .homeTeamCountry(teamCountries.get(homeTeam))
                    .awayTeam(teamNames.get(awayTeam))
                    .awayTeamCountry(teamCountries.get(awayTeam))
                    .date(dataStorage.getEvents().get(i).getStartDate().substring(0, DATE_LENGTH))
                    .skirmishResult(skirmishResult)
                    .highestProbability(probability)
                    .build()
            );
        } else {
            results.add(new Result.ResultBuilder()
                    .homeTeam(teamNames.get(homeTeam))
                    .homeTeamCountry(teamCountries.get(homeTeam))
                    .awayTeam(teamNames.get(awayTeam))
                    .awayTeamCountry(teamCountries.get(awayTeam))
                    .city(dataStorage.getEvents().get(i).getVenue().getCityName())
                    .stadium(dataStorage.getEvents().get(i).getVenue().getName())
                    .date(dataStorage.getEvents().get(i).getStartDate().substring(0, DATE_LENGTH))
                    .skirmishResult(skirmishResult)
                    .highestProbability(probability)
                    .build()
            );
        }
    }

    private int calculateAwayTeamIndex(int homeTeam) {
        return homeTeam + 1;
    }

    private int calculateHomeTeamIndex(int i) {
        return i * 2;
    }

    private void sortListByProbability() {
        results.sort(Comparator.comparing(Result::getHighestProbability));
    }

    private boolean isRangeValid(Integer range) {
        return range > dataStorage.getEvents().size() || range <= 0;
    }
}
