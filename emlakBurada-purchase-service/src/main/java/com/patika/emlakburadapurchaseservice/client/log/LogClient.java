package com.patika.emlakburadapurchaseservice.client.log;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "logClient", url = "http://localhost:8099/api/v1/logs")
public interface LogClient {

    @PostMapping("")
    void log(@RequestParam("strategy") String strategy, @RequestParam("message") String message);
}
