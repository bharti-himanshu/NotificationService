package com.example.NotificationServiceTry.data.dto.request.blackList;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlacklistNumbersReq {
    @JsonProperty("phone_numbers")
    Set<String> phoneNumbers;
}
