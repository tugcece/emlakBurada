package com.patika.emlakburadauserservice.client.log.service;

import com.patika.emlakburadauserservice.client.log.LogClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class LogService {

    private final LogClient logClient;

    public void log(String strategy, String message){
        logClient.log(strategy,message);
    }


}
