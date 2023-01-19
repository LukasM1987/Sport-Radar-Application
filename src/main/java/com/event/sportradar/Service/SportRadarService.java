package com.event.sportradar.Service;

import com.event.sportradar.input.ObjectCreator;
import com.event.sportradar.output.Printer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SportRadarService {

    private final ObjectCreator objectCreator;
    private static final List<String> teamsList = new ArrayList<>();
    private static final Set<String> teamsSet = new HashSet<>();
    private static final int HOME_TEAM = 0;
    private static final int AWAY_TEAM = 1;
    private static final int DATE_LENGTH = 19;
    private static final Integer DEFAULT_RANGE = 10;
    private static final String BLUE = "\033[0;34m";
    private static final String RESET = "\033[0m";

    public void compareProbability(Integer range) {
        if (range == null) {
            range = DEFAULT_RANGE;
        }
        if (!isRangeValid(range)) {
            System.err.println("Range is to big, or is equals 0, or less then 0.");
            return;
        }
        for (int i = 0; i < range; i++) {
            for (int j = 0; j < objectCreator.getEvents().get(i).getCompetitors().size(); j++) {
                teamsList.add(objectCreator.getEvents().get(i).getCompetitors().get(j).getName());
            }
            if (objectCreator.getEvents().get(i).getVenue() == null) {
                compareProbabilityWithoutVenue(i);
            } else {
                compareProbabilityWithVenue(i);
            }
            teamsList.clear();
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

    private void compareProbabilityWithVenue(final int i) {
        if (objectCreator.getEvents().get(i).getProbabilityAwayTeamWinner() > objectCreator.getEvents().get(i).getProbabilityHomeTeamWinner()
                && objectCreator.getEvents().get(i).getProbabilityAwayTeamWinner() > objectCreator.getEvents().get(i).getProbabilityDraw()) {
            Printer.printResultWithVenue(SkirmishResult.WIN.getValue(), teamsList.get(HOME_TEAM), teamsList.get(AWAY_TEAM),
                    objectCreator.getEvents().get(i).getVenue().getCityName(),
                    objectCreator.getEvents().get(i).getVenue().getName(),
                    objectCreator.getEvents().get(i).getStartDate(),
                    teamsList.get(AWAY_TEAM), DATE_LENGTH, BLUE, RESET);
        } else if (objectCreator.getEvents().get(i).getProbabilityHomeTeamWinner() > objectCreator.getEvents().get(i).getProbabilityAwayTeamWinner()
                && objectCreator.getEvents().get(i).getProbabilityHomeTeamWinner() > objectCreator.getEvents().get(i).getProbabilityDraw()) {
            Printer.printResultWithVenue(SkirmishResult.WIN.getValue(), teamsList.get(HOME_TEAM), teamsList.get(AWAY_TEAM),
                    objectCreator.getEvents().get(i).getVenue().getCityName(),
                    objectCreator.getEvents().get(i).getVenue().getName(),
                    objectCreator.getEvents().get(i).getStartDate(),
                    teamsList.get(HOME_TEAM), DATE_LENGTH, BLUE, RESET);
        } else {
            Printer.printResultWithVenue(SkirmishResult.DRAW.getValue(), teamsList.get(HOME_TEAM), teamsList.get(AWAY_TEAM),
                    objectCreator.getEvents().get(i).getVenue().getCityName(),
                    objectCreator.getEvents().get(i).getVenue().getName(),
                    objectCreator.getEvents().get(i).getStartDate(),
                    null, DATE_LENGTH, BLUE, RESET);
        }
    }

    private void compareProbabilityWithoutVenue(final int i) {
        if (objectCreator.getEvents().get(i).getProbabilityAwayTeamWinner() > objectCreator.getEvents().get(i).getProbabilityHomeTeamWinner()
                && objectCreator.getEvents().get(i).getProbabilityAwayTeamWinner() > objectCreator.getEvents().get(i).getProbabilityDraw()) {
            Printer.printResultWithoutVenue(SkirmishResult.WIN.getValue(), teamsList.get(HOME_TEAM), teamsList.get(AWAY_TEAM),
                    objectCreator.getEvents().get(i).getStartDate(),
                    teamsList.get(AWAY_TEAM), DATE_LENGTH, BLUE, RESET);
        } else if (objectCreator.getEvents().get(i).getProbabilityHomeTeamWinner() > objectCreator.getEvents().get(i).getProbabilityAwayTeamWinner()
                && objectCreator.getEvents().get(i).getProbabilityHomeTeamWinner() > objectCreator.getEvents().get(i).getProbabilityDraw()) {
            Printer.printResultWithoutVenue(SkirmishResult.WIN.getValue(), teamsList.get(HOME_TEAM), teamsList.get(AWAY_TEAM),
                    objectCreator.getEvents().get(i).getStartDate(),
                    teamsList.get(HOME_TEAM), DATE_LENGTH, BLUE, RESET);
        } else {
            Printer.printResultWithoutVenue(SkirmishResult.DRAW.getValue(), teamsList.get(HOME_TEAM), teamsList.get(AWAY_TEAM),
                    objectCreator.getEvents().get(i).getStartDate(),
                    null, DATE_LENGTH, BLUE, RESET);
        }
    }

    private boolean isRangeValid(Integer range) {
        System.out.println("ROZMIAR: " + objectCreator.getEvents().size());
        if (range > objectCreator.getEvents().size() || range <= 0) {
            return false;
        }
        return true;
    }
}
