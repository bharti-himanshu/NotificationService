package com.example.NotificationServiceTry.controllers;

import com.example.NotificationServiceTry.data.dto.request.blackList.BlacklistNumbersReq;
import com.example.NotificationServiceTry.data.dto.response.blackList.BlacklistSaveRes;
import com.example.NotificationServiceTry.services.BlacklistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/blacklist")
public class BlacklistController {
    private static final Logger log = LoggerFactory.getLogger(SmsSendController.class);
    @Autowired
    BlacklistService blacklistService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BlacklistSaveRes> saveBlacklist(@RequestBody BlacklistNumbersReq blacklistNumbersReq){
        BlacklistSaveRes blacklistSaveRes = blacklistService.saveBlackList(blacklistNumbersReq);
        return new ResponseEntity<>(blacklistSaveRes,HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<BlacklistNumbersReq> getBlackList(){
        BlacklistNumbersReq blacklistNumbersReq = blacklistService.getBlackList();
        return new ResponseEntity<BlacklistNumbersReq>(blacklistNumbersReq,HttpStatus.OK);
    }

    @DeleteMapping("")
    public void deleteNumber(@RequestBody BlacklistNumbersReq blacklistNumbersReq){
        blacklistService.deleteNumber(blacklistNumbersReq);
    }
}
