package com.example.NotificationService.services;

import com.example.NotificationService.entities.ApiResponseEntity.ApiResponseFailure;
import com.example.NotificationService.entities.ApiResponseEntity.ApiResponseRoot;
import com.example.NotificationService.entities.SmsApi.SmsApi;
import com.example.NotificationService.entities.SmsRequest;
import com.example.NotificationService.entities.ApiResponseEntity.ApiResponse;
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

@Service
public class SmsApiService {
    private static final Logger log = LoggerFactory.getLogger(NotificationConsumer.class);

    public ApiResponse sendSms(SmsRequest sms_request) throws URISyntaxException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "https://api.imiconnect.in/resources/v1/messaging";
        URI uri = new URI(baseUrl);
        SmsApi smsApi = new SmsApi(sms_request.getMessage(),sms_request.getPhone_number(),Integer.toString(sms_request.getId()));
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("key","7b73f76d-369e-11ea-9e4e-025282c394f2e");
        HttpEntity<SmsApi> request = new HttpEntity<>(smsApi, headers);

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
