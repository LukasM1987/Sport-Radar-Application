package com.event.sportradar.input;

import com.event.sportradar.domain.Competitor;
import com.event.sportradar.domain.Event;
import com.event.sportradar.domain.Result;
import com.event.sportradar.domain.Venue;
import com.github.tsohr.JSONException;
import com.github.tsohr.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataStorage {

    private final List<Event> events = new ArrayList<>();
    private final List<Competitor> competitors = new ArrayList<>();
    private final List<Result> results = new ArrayList<>();
    private final Set<String> teamsSet = new HashSet<>();

    public void addEvent(JSONObject eventObject, Venue venue) {
        events.add(new Event(
                eventObject.getString("sport_event_id"),
                eventObject.getString("start_date"),
                eventObject.getString("sport_name"),
                eventObject.getString("competition_name"),
                eventObject.getString("competition_id"),
                eventObject.getString("season_name"),
                competitors,
                venue,
                eventObject.getDouble("probability_home_team_winner"),
                eventObject.getDouble("probability_draw"),
                eventObject.getDouble("probability_away_team_winner")
        ));
    }

    public boolean addCompetitor(JSONObject competitorObject) {
        return competitors.add(new Competitor(
                competitorObject.getString("id"),
                competitorObject.getString("name"),
                competitorObject.getString("country"),
                competitorObject.getString("country_code"),
                competitorObject.getString("abbreviation"),
                competitorObject.getString("qualifier"),
                competitorObject.getString("gender")
        ));
    }

    public Venue createVenue(JSONObject eventObject) {
        Venue venue;
        try {
            JSONObject venueObject = eventObject.getJSONObject("venue");
            venue = new Venue(
                    venueObject.getString("id"),
                    venueObject.getString("name"),
                    venueObject.getInt("capacity"),
                    venueObject.getString("city_name"),
                    venueObject.getString("country_name"),
                    venueObject.getString("map_coordinates"),
                    venueObject.getString("country_code")
            );
        } catch (JSONException ignored) {
            venue = null;
        }
        return venue;
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

    public void addTeamsNames(int firstIndex, int secondIndex) {
        teamsSet.add(events.get(firstIndex).getCompetitors().get(secondIndex).getName());
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
