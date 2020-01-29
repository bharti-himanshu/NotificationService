package com.example.NotificationService.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name="sms_requests")
public class SmsRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String phone_number;

    private String message;

    private String status;

    private String failure_code;

    private String failure_comments;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    public SmsRequest(){}

}
