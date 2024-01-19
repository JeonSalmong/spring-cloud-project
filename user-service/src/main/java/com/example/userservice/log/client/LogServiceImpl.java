package com.example.userservice.log.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class LogServiceImpl implements LogService {

    private final LogServiceClient logServiceClient;

    @Override
    public void saveActionLog(Map<String, Object> logParam) {
        logServiceClient.saveActionLog(logParam);
    }
}
