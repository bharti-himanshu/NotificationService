package com.example.NotificationService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ElasticSearchIntervalDto {
    ElasticSearchIntervalHelper data;
    String scrollId;

    @Data
    @AllArgsConstructor
    public class ElasticSearchIntervalHelper {
        String phoneNumber;
        LocalDateTime startTime;
        LocalDateTime endTime;

    }

}
