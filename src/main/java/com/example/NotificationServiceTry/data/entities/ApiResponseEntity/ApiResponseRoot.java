package com.example.NotificationServiceTry.data.entities.ApiResponseEntity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@JsonAutoDetect
public class ApiResponseRoot {
    private List<ApiResponse> apiResponse;
}
