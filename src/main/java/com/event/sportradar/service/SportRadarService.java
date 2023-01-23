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
            getCompetitors(i);
            compareProbability(i);
        }
        sortListByProbability();
        Printer.printResults(range, dataStorage.getResults());
    }

    private List<Competitor> getCompetitors(int currentIndex) {
        return dataStorage.getEvents().get(currentIndex).getCompetitors();
    }

    public void printAllCompetitorsAlphabetically() {
        for (int i = 0; i < dataStorage.getEvents().size(); i++) {
            for (int j = 0; j < dataStorage.getEvents().get(i).getCompetitors().size(); j++) {
                dataStorage.addTeamsNames(i, j);
            }
        }
        TreeSet<String> treeSet = new TreeSet<>(dataStorage.getTeamNamesSet());
        Printer.printCompetitors(treeSet);
    }

    private void compareProbability(int currentIndex) {
        double home = getProbabilityHomeTeamWinner(currentIndex);
        double away = getProbabilityAwayTeamWinner(currentIndex);
        double draw = getProbabilityDraw(currentIndex);
        if (home > away && home > draw) {
            addResult(currentIndex, HOME_TEAM_WIN, getProbabilityHomeTeamWinner(currentIndex));
        } else if (away > home && away > draw) {
            addResult(currentIndex, AWAY_TEAM_WIN, getProbabilityAwayTeamWinner(currentIndex));
        } else {
            addResult(currentIndex, RESULT_DRAW, getProbabilityDraw(currentIndex));
        }
    }

    private double getProbabilityDraw(int currentIndex) {
        return dataStorage.getEvents().get(currentIndex).getProbabilityDraw();
    }

    private double getProbabilityAwayTeamWinner(int currentIndex) {
        return dataStorage.getEvents().get(currentIndex).getProbabilityAwayTeamWinner();
    }

    private double getProbabilityHomeTeamWinner(int currentIndex) {
        return dataStorage.getEvents().get(currentIndex).getProbabilityHomeTeamWinner();
    }

    private void addResult(int currentIndex, String skirmishResult, double probability) {
        int homeTeamIndex = calculateHomeTeamIndex(currentIndex);
        int awayTeamIndex = calculateAwayTeamIndex(homeTeamIndex);
        String homeTeamName = getCompetitors(currentIndex).get(homeTeamIndex).getName();
        String homeTeamCountry = getCompetitors(currentIndex).get(homeTeamIndex).getCountry();
        String awayTeamName = getCompetitors(currentIndex).get(awayTeamIndex).getName();
        String awayTeamCountry = getCompetitors(currentIndex).get(awayTeamIndex).getCountry();
        dataStorage.addResult(currentIndex, homeTeamName, awayTeamName, homeTeamCountry, awayTeamCountry, DATE_LENGTH, skirmishResult, probability);
    }

    private int calculateAwayTeamIndex(int homeTeam) {
        return homeTeam + 1;
    }

    private int calculateHomeTeamIndex(int currentIndex) {
        return currentIndex * 2;
    }

    private void sortListByProbability() {
        dataStorage.getResults().sort(Comparator.comparing(Result::getHighestProbability));
    }

    private boolean isRangeValid(Integer range) {
        return range > dataStorage.getEvents().size() || range <= 0;
    }
}
