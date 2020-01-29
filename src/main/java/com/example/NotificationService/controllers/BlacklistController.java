package com.example.NotificationService.controllers;

import com.example.NotificationService.dto.BlacklistNumbersDto;
import com.example.NotificationService.services.BlacklistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("v1/blacklist")
public class BlacklistController {
    private static final Logger log = LoggerFactory.getLogger(SmsSendController.class);
    @Autowired
    BlacklistService blacklistService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Map<String,String>> saveBlacklist(@RequestBody BlacklistNumbersDto blacklistNumbersDto){
        blacklistService.saveBlackList(blacklistNumbersDto);
        Map<String,String> response = new HashMap<>();
        response.put("data","Successfully blacklisted");
        return new ResponseEntity<Map<String, String>>(response,HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<BlacklistNumbersDto> getBlackList(){
        BlacklistNumbersDto blacklistNumbersDto = blacklistService.getBlackList();
        return new ResponseEntity<BlacklistNumbersDto>(blacklistNumbersDto,HttpStatus.OK);
    }

    @DeleteMapping("")
    public void deleteNumber(@RequestBody BlacklistNumbersDto blacklistNumbersDto){
        blacklistService.deleteNumber(blacklistNumbersDto);
    }
}
