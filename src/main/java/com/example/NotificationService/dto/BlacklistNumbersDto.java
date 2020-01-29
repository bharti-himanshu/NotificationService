package com.example.NotificationService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class BlacklistNumbersDto {
    Set<String> phone_numbers;

    public BlacklistNumbersDto() {
    }

}
