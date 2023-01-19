package com.event.sportradar.input;

import com.event.sportradar.domain.Competitor;
import com.event.sportradar.domain.Event;
import com.event.sportradar.domain.Venue;
import com.github.tsohr.JSONException;
import com.github.tsohr.JSONObject;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class ObjectCreator {

    private static List<Event> events = new ArrayList<>();
    private static List<Competitor> competitors = new ArrayList<>();

    public static void addEvent(JSONObject eventObject, Venue venue) {
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

    public static boolean addCompetitor(JSONObject competitorObject) {
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

    public static Venue getVenue(JSONObject eventObject) {
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

    public List<Event> getEvents() {
        return events;
    }
}
