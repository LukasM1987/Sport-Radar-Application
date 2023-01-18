package com.event.sportradar.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Event {

    private String sport_event_id;
    private String startDate;
    private String sportName;
    private String competitionName;
    private String competitionId;
    private String seasonName;
    private List<Competitor> competitors = new ArrayList<>();
    private Venue venue;
    private double probabilityHomeTeamWinner;
    private double probabilityDraw;
    private double probabilityAwayTeamWinner;

}
