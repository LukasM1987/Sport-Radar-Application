package com.event.sportradar.Service;

import com.event.sportradar.input.Reader;
import com.event.sportradar.output.Printer;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AppService {

    private static final Reader reader = new Reader();
    private static final List<String> teamsList = new ArrayList<>();
    private static final Set<String> teamsSet = new HashSet<>();
    private static final int HOME_TEAM = 0;
    private static final int AWAY_TEAM = 1;
    private static final int DATE_LENGTH = 19;
    private static final String BLUE = "\033[0;34m";
    private static final String RESET = "\033[0m";

    public void compareProbability(final int range) {
        reader.readFile();
        if (range > reader.getEvents().size() || range <= 0) {
            System.err.println("Range is to big, or is equals 0, or less then 0.");
        } else {
            for (int i = 0; i < range; i++) {
                for (int j = 0; j < reader.getEvents().get(i).getCompetitors().size(); j++) {
                    teamsList.add(reader.getEvents().get(i).getCompetitors().get(j).getName());
                }
                if (reader.getEvents().get(i).getVenue() == null) {
                    compareProbabilityWithoutVenue(i);
                } else {
                    compareProbabilityWithVenue(i);
                }
                teamsList.clear();
            }
        }
    }

    public void getAllCompetitorsAlphabetically() {
        reader.readFile();
        for (int i = 0; i < reader.getEvents().size(); i++) {
            for (int j = 0; j < reader.getEvents().get(i).getCompetitors().size(); j++) {
                teamsSet.add(reader.getEvents().get(i).getCompetitors().get(j).getName());
            }
        }
        TreeSet<String> treeSet = new TreeSet<>(teamsSet);
        Printer.printCompetitors(treeSet);
        teamsSet.clear();
    }

    private void compareProbabilityWithVenue(final int i) {
        if (reader.getEvents().get(i).getProbabilityAwayTeamWinner() > reader.getEvents().get(i).getProbabilityHomeTeamWinner()
                && reader.getEvents().get(i).getProbabilityAwayTeamWinner() > reader.getEvents().get(i).getProbabilityDraw()) {
            Printer.printResultWithVenue(SkirmishResult.WIN.getValue(), teamsList.get(HOME_TEAM), teamsList.get(AWAY_TEAM),
                    reader.getEvents().get(i).getVenue().getCityName(),
                    reader.getEvents().get(i).getVenue().getName(),
                    reader.getEvents().get(i).getStartDate(),
                    teamsList.get(AWAY_TEAM), DATE_LENGTH, BLUE, RESET);
        } else if (reader.getEvents().get(i).getProbabilityHomeTeamWinner() > reader.getEvents().get(i).getProbabilityAwayTeamWinner()
                && reader.getEvents().get(i).getProbabilityHomeTeamWinner() > reader.getEvents().get(i).getProbabilityDraw()) {
            Printer.printResultWithVenue(SkirmishResult.WIN.getValue(), teamsList.get(HOME_TEAM), teamsList.get(AWAY_TEAM),
                    reader.getEvents().get(i).getVenue().getCityName(),
                    reader.getEvents().get(i).getVenue().getName(),
                    reader.getEvents().get(i).getStartDate(),
                    teamsList.get(HOME_TEAM), DATE_LENGTH, BLUE, RESET);
        } else {
            Printer.printResultWithVenue(SkirmishResult.DRAW.getValue(), teamsList.get(HOME_TEAM), teamsList.get(AWAY_TEAM),
                    reader.getEvents().get(i).getVenue().getCityName(),
                    reader.getEvents().get(i).getVenue().getName(),
                    reader.getEvents().get(i).getStartDate(),
                    null, DATE_LENGTH, BLUE, RESET);
        }
    }

    private void compareProbabilityWithoutVenue(final int i) {
        if (reader.getEvents().get(i).getProbabilityAwayTeamWinner() > reader.getEvents().get(i).getProbabilityHomeTeamWinner()
                && reader.getEvents().get(i).getProbabilityAwayTeamWinner() > reader.getEvents().get(i).getProbabilityDraw()) {
            Printer.printResultWithoutVenue(SkirmishResult.WIN.getValue(), teamsList.get(HOME_TEAM), teamsList.get(AWAY_TEAM),
                    reader.getEvents().get(i).getStartDate(),
                    teamsList.get(AWAY_TEAM), DATE_LENGTH, BLUE, RESET);
        } else if (reader.getEvents().get(i).getProbabilityHomeTeamWinner() > reader.getEvents().get(i).getProbabilityAwayTeamWinner()
                && reader.getEvents().get(i).getProbabilityHomeTeamWinner() > reader.getEvents().get(i).getProbabilityDraw()) {
            Printer.printResultWithoutVenue(SkirmishResult.WIN.getValue(), teamsList.get(HOME_TEAM), teamsList.get(AWAY_TEAM),
                    reader.getEvents().get(i).getStartDate(),
                    teamsList.get(HOME_TEAM), DATE_LENGTH, BLUE, RESET);
        } else {
            Printer.printResultWithoutVenue(SkirmishResult.DRAW.getValue(), teamsList.get(HOME_TEAM), teamsList.get(AWAY_TEAM),
                    reader.getEvents().get(i).getStartDate(),
                    null, DATE_LENGTH, BLUE, RESET);
        }
    }
}
