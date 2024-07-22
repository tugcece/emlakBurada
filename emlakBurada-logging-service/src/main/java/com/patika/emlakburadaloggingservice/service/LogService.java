package com.patika.emlakburadaloggingservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LogService {

    private final Map<String, LogStrategy> logStrategies = new HashMap<>();

    @Autowired
    public LogService(List<LogStrategy> strategies) {
        for (LogStrategy strategy : strategies) {
            logStrategies.put(strategy.getClass().getSimpleName(), strategy);
        }
    }

    public void log(String strategyName, String message) {
        LogStrategy strategy = logStrategies.get(strategyName);
        if (strategy != null) {
            strategy.log(message);
        } else {
            throw new IllegalArgumentException("No such logging strategy: " + strategyName);
        }
    }
}
