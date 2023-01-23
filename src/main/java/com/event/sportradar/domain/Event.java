package com.event.sportradar.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
public class Event {

    private String sport_event_id;
    private String startDate;
    private String sportName;
    private String competitionName;
    private String competitionId;
    private String seasonName;
    private List<Competitor> competitors;
    private Venue venue;
    private double probabilityHomeTeamWinner;
    private double probabilityDraw;
    private double probabilityAwayTeamWinner;

    public Event(String sport_event_id, String startDate, String sportName, String competitionName, String competitionId, String seasonName, List<Competitor> competitors, Venue venue, double probabilityHomeTeamWinner, double probabilityDraw, double probabilityAwayTeamWinner) {
        this.sport_event_id = sport_event_id;
        this.startDate = startDate;
        this.sportName = sportName;
        this.competitionName = competitionName;
        this.competitionId = competitionId;
        this.seasonName = seasonName;
        this.competitors = new ArrayList<>(competitors);
        this.venue = venue;
        this.probabilityHomeTeamWinner = probabilityHomeTeamWinner;
        this.probabilityDraw = probabilityDraw;
        this.probabilityAwayTeamWinner = probabilityAwayTeamWinner;
    }
}
