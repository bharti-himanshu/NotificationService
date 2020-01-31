package com.example.NotificationServiceTry.data.dto.response.blackList;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlacklistSaveRes {
    @JsonProperty("data")
    String data;
}
