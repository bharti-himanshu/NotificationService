package com.example.NotificationServiceTry.controllers;

import com.example.NotificationServiceTry.data.dto.request.searchSms.SearchSmsReq;
import com.example.NotificationServiceTry.exceptions.NoSearchResultException;
import com.example.NotificationServiceTry.data.dto.response.searchSms.SearchSmsRes;
import com.example.NotificationServiceTry.services.SmsCrudService;
import com.example.NotificationServiceTry.services.ElasticSearch.ElasticSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/v1/sms/searchSms")
public class ElasticSearchController {
    private static final Logger log = LoggerFactory.getLogger(ElasticSearchController.class);

    @Autowired
    ElasticSearchService elasticSearchService;

    @Autowired
    SmsCrudService smsCrudService;

    @GetMapping("/smsBetweenInterval")
    public ResponseEntity<SearchSmsRes> smsBetweenInterval(@RequestBody SearchSmsReq searchSmsReq) throws IOException, NoSearchResultException {

        SearchSmsRes res = elasticSearchService.getSmsInterval(searchSmsReq);
        log.info(String.valueOf(res.getData().size()));
        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @GetMapping("/getAllSms")
    public ResponseEntity<SearchSmsRes> test(@RequestBody SearchSmsReq searchSmsReq) throws IOException, NoSearchResultException {

        SearchSmsRes res = elasticSearchService.getAllSms(searchSmsReq);
        log.info(String.valueOf(res.getData().size()));
        return new ResponseEntity<>(res,HttpStatus.OK);

    }

    @GetMapping("/smsContainingText")
    public ResponseEntity<SearchSmsRes> smsContainingText(@RequestBody SearchSmsReq searchSmsReq) throws IOException, NoSearchResultException {

        SearchSmsRes res = elasticSearchService.getSmsContainingText(searchSmsReq);
        log.info(String.valueOf(res.getData().size()));
        return new ResponseEntity<>(res, HttpStatus.OK);

    }

}
