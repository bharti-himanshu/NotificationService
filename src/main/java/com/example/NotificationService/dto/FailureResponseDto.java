package com.example.NotificationService.dto;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class FailureResponseDto {
    Map<String,String> error;

    public FailureResponseDto(String message) {
        error = new LinkedHashMap<>();
        error.put("code","INVALID_REQUEST");
        error.put("message",message);
    }
}
