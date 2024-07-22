package com.patika.emlakburadaloggingservice.controller;

import com.patika.emlakburadaloggingservice.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/logs")
public class LogController {

    private final LogService logService;

    @PostMapping
    public ResponseEntity<String> log(@RequestParam String strategy, @RequestParam String message) {
        logService.log(strategy, message);
        return ResponseEntity.ok("Logged successfully using " + strategy);
    }
}
