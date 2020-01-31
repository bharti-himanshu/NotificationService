package com.example.NotificationServiceTry.data.dto.response.smsSend;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class SmsCreateRes {
    @JsonProperty("data")
    Data data;

    @lombok.Data
    @AllArgsConstructor @NoArgsConstructor
    @Builder
    public static class Data{
        @JsonProperty("request_id")
        String requestId;
        @JsonProperty("comments")
        String comments;
    }
}
