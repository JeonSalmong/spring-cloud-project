package com.example.userservice.log.client;

import java.util.Map;

public interface LogService {
    void saveActionLog(Map<String, Object> logParam);
}
