package com.example.NotificationService.entities.ApiResponseEntity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonAutoDetect
public class ApiResponseFailure {
    private ApiResponse apiResponse;

}
