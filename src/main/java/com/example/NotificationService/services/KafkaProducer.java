package com.example.NotificationServiceTry.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    public static final String Topic = "notification.send_sms";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publishId(int request_id){
        this.kafkaTemplate.send(Topic,Integer.toString(request_id));
    }

}
