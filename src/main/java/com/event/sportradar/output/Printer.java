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
                        + " VS. Away team: "
                        + results.get(i).getAwayTeam()
                        + ", Probability result: "
                        + results.get(i).getSkirmishResult());
            } else {
                System.out.println("Event: Home team: "
                        + results.get(i).getHomeTeam()
                        + " VS. Away team: "
                        + results.get(i).getAwayTeam()
                        + ", Where: "
                        + results.get(i).getCity()
                        + ", "
                        + results.get(i).getStadium()
                        + ", Probability result: "
                        + results.get(i).getSkirmishResult());
            }
        }
    }

    public static void printCompetitors(TreeSet<String> teams) {
        for (String competitor : teams) {
            System.out.println(competitor);
        }
    }
}
