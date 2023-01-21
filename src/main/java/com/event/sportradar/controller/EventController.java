package com.event.sportradar.controller;

import com.event.sportradar.service.SportRadarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sportRadar")
@RequiredArgsConstructor
public class EventController {

    private final SportRadarService sportRadarService;

    @GetMapping(value = "/competitors")
    public void getAllCompetitors() {
        sportRadarService.printAllCompetitorsAlphabetically();
    }

    @GetMapping(value = "probabilities")
    public void getProbabilities(@RequestParam(required = false) final Integer range) {
        sportRadarService.compareProbability(range);
    }
}
