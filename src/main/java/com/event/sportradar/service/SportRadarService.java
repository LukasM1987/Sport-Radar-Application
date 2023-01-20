package com.event.sportradar.service;

import com.event.sportradar.domain.Result;
import com.event.sportradar.input.ObjectCreator;
import com.event.sportradar.output.Printer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SportRadarService {

    private final ObjectCreator objectCreator;
    private static final List<String> teams = new ArrayList<>();
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
        if (!isRangeValid(range)) {
            System.err.println("Range is to big, or is equals 0, or less then 0.");
            return;
        }
        for (int i = 0; i < objectCreator.getEvents().size(); i++) {
            addTeams(i);
            compareCompetitors(i);
        }
        sortListByProbability();
        Printer.printResults(range, results);
        teams.clear();
        results.clear();
    }

    private void addTeams(int i) {
        if (i == 0) {
            for (int j = 0; j < objectCreator.getEvents().get(i).getCompetitors().size(); j++) {
                teams.add(objectCreator.getEvents().get(i).getCompetitors().get(j).getName());
            }
        }
    }

    public void getAllCompetitorsAlphabetically() {
        for (int i = 0; i < objectCreator.getEvents().size(); i++) {
            for (int j = 0; j < objectCreator.getEvents().get(i).getCompetitors().size(); j++) {
                teamsSet.add(objectCreator.getEvents().get(i).getCompetitors().get(j).getName());
            }
        }
        TreeSet<String> treeSet = new TreeSet<>(teamsSet);
        Printer.printCompetitors(treeSet);
        teamsSet.clear();
    }

    private void compareCompetitors(int i) {
        if (objectCreator.getEvents().get(i).getProbabilityHomeTeamWinner()
                > objectCreator.getEvents().get(i).getProbabilityAwayTeamWinner()
                && objectCreator.getEvents().get(i).getProbabilityHomeTeamWinner()
                > objectCreator.getEvents().get(i).getProbabilityDraw()) {
            addResult(i, HOME_TEAM_WIN, objectCreator.getEvents().get(i).getProbabilityHomeTeamWinner());
        } else if (objectCreator.getEvents().get(i).getProbabilityAwayTeamWinner()
                > objectCreator.getEvents().get(i).getProbabilityHomeTeamWinner()
                && objectCreator.getEvents().get(i).getProbabilityAwayTeamWinner()
                > objectCreator.getEvents().get(i).getProbabilityDraw()) {
            addResult(i, AWAY_TEAM_WIN, objectCreator.getEvents().get(i).getProbabilityAwayTeamWinner());
        } else {
            addResult(i, RESULT_DRAW, objectCreator.getEvents().get(i).getProbabilityDraw());
        }
    }

    private void addResult(int i, String skirmishResult, double probability) {
        int first = i * 2;
        int second = first + 1;
        if (objectCreator.getEvents().get(i).getVenue() == null) {
            results.add(new Result(
                    teams.get(first),
                    teams.get(second),
                    null,
                    null,
                    objectCreator.getEvents().get(i).getStartDate().substring(0, DATE_LENGTH),
                    skirmishResult,
                    probability)
            );
        } else {
            results.add(new Result(
                    teams.get(first),
                    teams.get(second),
                    objectCreator.getEvents().get(i).getVenue().getCityName(),
                    objectCreator.getEvents().get(i).getVenue().getName(),
                    objectCreator.getEvents().get(i).getStartDate().substring(0, DATE_LENGTH),
                    skirmishResult,
                    probability)
            );
        }
    }

    private void sortListByProbability() {
        results.sort(Comparator.comparing(Result::getHighestProbability));
    }

    private boolean isRangeValid(Integer range) {
        if (range > objectCreator.getEvents().size() || range <= 0) {
            return false;
        }
        return true;
    }
}
