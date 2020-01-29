package com.example.NotificationServiceTry.services;

import com.example.NotificationServiceTry.Exceptions.BlackListedNumberException;
import com.example.NotificationServiceTry.entities.ApiResponseEntity.ApiResponse;
import com.example.NotificationServiceTry.entities.SmsRequest;
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
        SmsRequest sms_request = smsCrudService.getSmsById(Integer.parseInt(request_id));
        String number = sms_request.getPhone_number();

        Boolean isBlackListed = jedis.sismember("blackList",number);

        if(isBlackListed==false) {
            ApiResponse apiResponse = smsApiService.sendSms(sms_request);
            if (apiResponse.getCode().equals("1001")) {
                sms_request.setStatus("Successfully Sent");
            } else {
                sms_request.setStatus("Failed");
                sms_request.setFailure_code(apiResponse.getCode());
                sms_request.setFailure_comments(apiResponse.getDescription());
            }
        }
        else{
            sms_request.setStatus("Failed");
            sms_request.setFailure_comments("Number is blacklisted");
            elasticSearchService.saveSmsElastic(sms_request);
        }
        smsCrudService.updateSms(sms_request);

    }

}
