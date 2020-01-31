package com.example.NotificationServiceTry.exceptionHandler;

import com.example.NotificationServiceTry.data.dto.response.exceptionHandler.SendSmsFailureRes;
import com.example.NotificationServiceTry.data.dto.response.exceptionHandler.InvalidScrollIdRes;
import com.example.NotificationServiceTry.data.dto.response.exceptionHandler.NoSearchResultRes;
import com.example.NotificationServiceTry.exceptions.InvalidScrollIdException;
import com.example.NotificationServiceTry.exceptions.NoSearchResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(SQLException.class)
    public String handleSQLException(HttpServletRequest request, Exception ex){
        log.info("SQLException Occured:: URL="+request.getRequestURL());
        return "database_error";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<SendSmsFailureRes> handleNoElementException(NoSuchElementException ex){
        String methodName = ex.getStackTrace()[1].getMethodName();
        log.error("Error occure in method : " + ex.getStackTrace()[1].getMethodName());

        if(methodName.equals("getSmsById")){
            return new ResponseEntity<>(new SendSmsFailureRes("request_id not found"),HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new SendSmsFailureRes("Error occured while fetching from Datbase"),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<SendSmsFailureRes> handlePhoneNumberMissingException(MethodArgumentNotValidException ex){
        String parameter = ex.getParameter().getParameterName();
        log.error("error occured in creating class - " + parameter);
        if(parameter.equals("smsCreateDto")){
            return new ResponseEntity<>(new SendSmsFailureRes("phone_number is mandatory"),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new SendSmsFailureRes("Argument Not valid"),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidScrollIdException.class)
    public ResponseEntity<InvalidScrollIdRes> handleInvalidScrollIdException(InvalidScrollIdException e){
        InvalidScrollIdRes invalidScrollIdRes = InvalidScrollIdRes.builder()
                .error("Page token provided is invalid")
                .build();
        return new ResponseEntity<>(invalidScrollIdRes,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSearchResultException.class)
    public ResponseEntity<NoSearchResultRes> handleNoSearchResultException(NoSearchResultException e){
        NoSearchResultRes noSearchResultRes = NoSearchResultRes.builder()
                .error("No result found/left")
                .build();
        return new ResponseEntity<>(noSearchResultRes,HttpStatus.BAD_REQUEST);
    }

}