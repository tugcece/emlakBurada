package com.patika.emlakburadaloggingservice.service;

import com.patika.emlakburadaloggingservice.model.LogMessage;
import com.patika.emlakburadaloggingservice.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DbLogStrategy implements LogStrategy {

    private final LogRepository logRepository;

    @Override
    public void log(String message) {
        LogMessage logMessage= new LogMessage();
        logMessage.setMessage(message);
        logMessage.setTimestamp(LocalDateTime.now());
        logRepository.save(logMessage);
    }
}
