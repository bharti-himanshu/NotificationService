package com.example.NotificationServiceTry.entities.ApiResponseEntity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@JsonAutoDetect
@Builder
@AllArgsConstructor
public class ApiResponse {

    private String code;
    private String transid;
    private String description;

}
