package com.event.sportradar.input;

import com.event.sportradar.domain.Competitor;
import com.event.sportradar.domain.Event;
import com.event.sportradar.domain.Venue;
import com.github.tsohr.JSONArray;
import com.github.tsohr.JSONObject;
import lombok.Getter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class Reader {

    private List<Event> events = new ArrayList<>();
    private List<Competitor> competitors = new ArrayList<>();

    @EventListener(ApplicationReadyEvent.class)
    public void readFile() {
        JSONObject jsonObject = convertFile();
        JSONArray jsonArray = jsonObject.getJSONArray("Events");
        readEvents(jsonArray);
    }

    private void readEvents(JSONArray jsonArray) {
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject eventObject = jsonArray.getJSONObject(i);
            JSONArray competitorsArray = eventObject.getJSONArray("competitors");
            Venue venue = readVenue(eventObject);
            readCompetitors(competitorsArray);
            ObjectCreator.addEvent(eventObject, venue);
        }
    }

    private void readCompetitors(JSONArray competitorsArray) {
        for (int i = 0; i < competitorsArray.length(); i++) {
            JSONObject competitorObject = competitorsArray.getJSONObject(i);
            ObjectCreator.addCompetitor(competitorObject);
        }
    }

    private Venue readVenue(JSONObject eventObject) {
        Venue venue;
        venue = ObjectCreator.getVenue(eventObject);
        return venue;
    }

    private JSONObject convertFile() {
        String convertedFile = null;
        try {
            convertedFile = new String(Files.readAllBytes(Paths.get("src\\main\\resources\\BE_data.json")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject(convertedFile);
        return jsonObject;
    }
}
