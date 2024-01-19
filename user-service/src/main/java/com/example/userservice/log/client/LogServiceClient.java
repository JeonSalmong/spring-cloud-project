package com.example.userservice.log.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(name = "logger-service", url = "${clients.api-gateway.url}")
public interface LogServiceClient {
    @PostMapping("/logger-service/saveActionLog")
    void saveActionLog(Map<String, Object> logParam);
}
