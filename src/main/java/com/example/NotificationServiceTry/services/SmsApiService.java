package com.example.NotificationServiceTry.services;

import com.example.NotificationServiceTry.data.entities.ApiResponseEntity.ApiResponseFailure;
import com.example.NotificationServiceTry.data.entities.ApiResponseEntity.ApiResponseRoot;
import com.example.NotificationServiceTry.data.entities.SmsApi.IMIConnectSMSReq;
import com.example.NotificationServiceTry.data.entities.SmsRequest;
import com.example.NotificationServiceTry.data.entities.ApiResponseEntity.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

@Service
public class SmsApiService {
    private static final Logger log = LoggerFactory.getLogger(NotificationConsumer.class);

    public ApiResponse sendSms(SmsRequest sms_request) throws URISyntaxException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "https://api.imiconnect.in/resources/v1/messaging";
        URI uri = new URI(baseUrl);
        IMIConnectSMSReq iMIConnectSMSReq = IMIConnectSMSReq.builder()
                .deliverychannel("sms")
                .channels(
                        IMIConnectSMSReq.Channels.builder()
                        .sms(
                                IMIConnectSMSReq.Channels.Sms.builder()
                                .text(sms_request.getMessage())
                                .build()
                        )
                        .build()
                )
                .destination(
                        Collections.singletonList(
                                IMIConnectSMSReq.Destination.builder()
                                        .msisdn(
                                                Collections.singletonList(
                                                        sms_request.getPhoneNumber()
                                                )
                                        )
                                        .correlationid(String.valueOf(sms_request.getId()))
                                        .build()
                        )
                )
                .build();


        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("key","7b73f76d-369e-11ea-9e4e-025282c394f2e");
        HttpEntity<IMIConnectSMSReq> request = new HttpEntity<>(iMIConnectSMSReq, headers);

        try {
            ResponseEntity<ApiResponseRoot> response = restTemplate.postForEntity(uri, request, ApiResponseRoot.class);
            log.info(response.getBody().toString());
            return response.getBody().getApiResponse().get(0);
        } catch (RestClientException e) {
            ResponseEntity<ApiResponseFailure> response = restTemplate.postForEntity(uri, request, ApiResponseFailure.class);
            log.info(response.getBody().toString());
            return response.getBody().getApiResponse();
        }

    }
}
