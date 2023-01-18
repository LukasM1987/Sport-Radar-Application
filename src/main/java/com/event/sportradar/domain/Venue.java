package com.event.sportradar.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Venue {

    private String id;
    private String name;
    private int capacity;
    private String cityName;
    private String countryName;
    private String mapCoordinates;
    private String countryCode;
}
