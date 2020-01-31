package com.example.NotificationServiceTry.data.dto.response.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvalidScrollIdRes {
    @JsonProperty("Error")
    String error;
}
