package com.example.NotificationServiceTry.controllers;

import com.example.NotificationServiceTry.Exceptions.NoSearchResultException;
import com.example.NotificationServiceTry.dto.ElasticSearchIntervalDto;
import com.example.NotificationServiceTry.dto.ElasticSearchResDto;
import com.example.NotificationServiceTry.dto.ScrollIdDto;
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
@RequestMapping("/v1/sms/elasticSearch")
public class ElasticSearchController {
    private static final Logger log = LoggerFactory.getLogger(ElasticSearchController.class);

    @Autowired
    ElasticSearchService elasticSearchService;

    @Autowired
    SmsCrudService smsCrudService;

    @GetMapping("/smsBetweenInterval")
    public ResponseEntity<ElasticSearchResDto> smsFromRequestId(@RequestBody ElasticSearchIntervalDto elasticSearchIntervalDto) throws IOException, NoSearchResultException {

        ScrollIdDto dto = elasticSearchService.getSmsInterval(elasticSearchIntervalDto.getData().getPhoneNumber(), elasticSearchIntervalDto.getData().getStartTime(), elasticSearchIntervalDto.getData().getEndTime(),elasticSearchIntervalDto.getScrollId());
        return new ResponseEntity<>(new ElasticSearchResDto(dto), HttpStatus.OK);

    }

    @GetMapping("/getAllSms")
    public ResponseEntity<ElasticSearchResDto> test(@RequestParam(value = "__paging_token",required = false) String pagingToken,@RequestParam(value = "limit") int limit) throws IOException, NoSearchResultException {

        ScrollIdDto dto = elasticSearchService.getAllSms(pagingToken,limit);
        log.info(String.valueOf(dto.getData().size()));
        return new ResponseEntity<>(new ElasticSearchResDto(dto),HttpStatus.OK);

    }

    @GetMapping("/smsContainingText")
    public ResponseEntity<ElasticSearchResDto> smsContainingText(@RequestParam("text") String text, @RequestParam(value = "__paging_token",required = false) String pagingToken) throws IOException, NoSearchResultException {

        ScrollIdDto Dto = elasticSearchService.getSmsContainingText(text,pagingToken);
        return new ResponseEntity<>(new ElasticSearchResDto(Dto), HttpStatus.OK);

    }

}
