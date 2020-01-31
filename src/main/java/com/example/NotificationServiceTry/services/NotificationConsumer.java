package com.example.NotificationServiceTry.services;

import com.example.NotificationServiceTry.data.entities.ApiResponseEntity.ApiResponse;
import com.example.NotificationServiceTry.data.entities.SmsRequest;
import com.example.NotificationServiceTry.services.ElasticSearch.ElasticSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class NotificationConsumer {
    private static final Logger log = LoggerFactory.getLogger(NotificationConsumer.class);

    @Autowired
    SmsCrudService smsCrudService;

    @Autowired
    ElasticSearchService elasticSearchService;

    @Autowired
    SmsApiService smsApiService;

    @Autowired
    Jedis jedis;

    @KafkaListener(topics = "notification.send_sms",groupId = "notification")
    public void consume(String request_id) throws URISyntaxException, IOException {
        SmsRequest smsRequest = smsCrudService.getSmsById(Integer.parseInt(request_id));
        String number = smsRequest.getPhoneNumber();

        Boolean isBlackListed = jedis.sismember("blackList",number);

        if(isBlackListed.equals(false)) {
            ApiResponse apiResponse = smsApiService.sendSms(smsRequest);
            if (apiResponse.getCode().equals("1001")) {
                smsRequest.setStatus("Successfully Sent");
            } else {
                smsRequest.setStatus("Failed");
                smsRequest.setFailureCode(apiResponse.getCode());
                smsRequest.setFailureComments(apiResponse.getDescription());
            }
        }
        else{
            smsRequest.setStatus("Failed");
            smsRequest.setFailureComments("Number is blacklisted");
            elasticSearchService.saveSmsElastic(smsRequest);
        }
        smsCrudService.updateSms(smsRequest);

    }

}
