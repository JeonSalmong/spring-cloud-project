package com.example.apigateway.exception;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
public class GWErrorResponse {

    private String errorMessage;
    private LocalDateTime localDateTime;
    private Map<String, Object> erroInfo = new HashMap<>();

    public GWErrorResponse(String errorMessage, LocalDateTime localDateTime) {
        this.errorMessage = errorMessage;
        this.localDateTime = localDateTime;
    }

    public static GWErrorResponse defaultError(String errorMessage){
        return new GWErrorResponse(errorMessage, LocalDateTime.now());
    }
}
