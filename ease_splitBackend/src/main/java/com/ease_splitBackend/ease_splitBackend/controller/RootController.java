package com.ease_splitBackend.ease_splitBackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RootController {

    @GetMapping({"/", "/api"})
    public Map<String, Object> rootStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "UP");
        status.put("application", "EaseSplit Backend API");
        status.put("version", "1.0.0");
        status.put("eventsEndpoint", "/api/events");
        return status;
    }
}
