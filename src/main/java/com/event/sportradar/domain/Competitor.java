package com.event.sportradar.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Competitor {

    private String id;
    private String name;
    private String country;
    private String countryCode;
    private String abbreviation;
    private String qualifier;
    private String gender;

}