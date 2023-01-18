package com.event.sportradar.controller;

import com.event.sportradar.Service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
public class EventController {

    private final AppService appService;

    @Autowired
    public EventController(AppService appService) {
        this.appService = appService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getAllCompetitors")
    public void getAllCompetitors() {
        appService.getAllCompetitorsAlphabetically();
    }

    @RequestMapping(method = RequestMethod.POST, value = "createProbability")
    public void createProbability(@RequestParam final int range) {
        appService.compareProbability(range);
    }
}
