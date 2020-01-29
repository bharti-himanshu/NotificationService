package com.example.NotificationService.dto;

import lombok.Data;

@Data
public class InvalidScrollIdDto {
    String error;

    public InvalidScrollIdDto() {
        error = "provided invalid ScrollId";
    }
}
