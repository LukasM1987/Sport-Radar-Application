package com.event.sportradar.output;

import com.event.sportradar.domain.Result;

import java.util.List;
import java.util.TreeSet;

public class Printer {

    public static void printResults(int range, List<Result> results) {
        for (int i = results.size() - range; i < results.size(); i++) {
            if (results.get(i).getCity() == null) {
                System.out.println("Event: Home team: "
                        + results.get(i).getHomeTeam()
                        + " (" + results.get(i).getHomeTeamCountry() + ") VS. Away team: "
                        + results.get(i).getAwayTeam()
                        + " (" + results.get(i).getAwayTeamCountry() + "), Probability result: "
                        + results.get(i).getSkirmishResult()
                        + " (" + results.get(i).getHighestProbability() + ')');
            } else {
                System.out.println("Event: Home team: "
                        + results.get(i).getHomeTeam()
                        + " (" + results.get(i).getHomeTeamCountry() + ") VS. Away team: "
                        + results.get(i).getAwayTeam()
                        + " (" + results.get(i).getAwayTeamCountry() + "), Where: "
                        + results.get(i).getCity()
                        + ", "
                        + results.get(i).getStadium()
                        + ", Probability result: "
                        + results.get(i).getSkirmishResult()
                        + " (" + results.get(i).getHighestProbability() + ')');
            }
        }
    }

    public static void printCompetitors(TreeSet<String> teams) {
        for (String competitor : teams) {
            System.out.println(competitor);
        }
    }
}
