package com.event.sportradar.input;

import com.event.sportradar.domain.Event;
import com.event.sportradar.domain.Result;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataStorage {

    private final List<Event> events = new ArrayList<>();
    private final List<Result> results = new ArrayList<>();
    private final Set<String> teamsSet = new HashSet<>();

    public void addEvent(Event event) {
        events.add(event);
    }

    public void addResult(int currentIndex, String homeTeamName, String awayTeamName, String homeTeamCountry, String awayTeamCountry, int dateLength, String skirmishResult, double probability) {
        if (events.get(currentIndex).getVenue() == null) {
            results.add(Result.builder()
                    .homeTeam(homeTeamName)
                    .homeTeamCountry(homeTeamCountry)
                    .awayTeam(awayTeamName)
                    .awayTeamCountry(awayTeamCountry)
                    .date(events.get(currentIndex).getStartDate().substring(0, dateLength))
                    .skirmishResult(skirmishResult)
                    .highestProbability(probability)
                    .build()
            );
        } else {
            results.add(Result.builder()
                    .homeTeam(homeTeamName)
                    .homeTeamCountry(homeTeamCountry)
                    .awayTeam(awayTeamName)
                    .awayTeamCountry(awayTeamCountry)
                    .city(events.get(currentIndex).getVenue().getCityName())
                    .stadium(events.get(currentIndex).getVenue().getName())
                    .date(events.get(currentIndex).getStartDate().substring(0, dateLength))
                    .skirmishResult(skirmishResult)
                    .highestProbability(probability)
                    .build()
            );
        }
    }

    public void addTeamsNames(String name) {
        teamsSet.add(name);
    }

    public List<Event> getEvents() {
        return events;
    }

    public Set<String> getTeamNamesSet() {
        return teamsSet;
    }

    public List<Result> getResults() {
        return results;
    }
}
