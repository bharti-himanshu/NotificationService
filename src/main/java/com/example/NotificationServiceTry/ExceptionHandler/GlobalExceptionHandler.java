package com.example.NotificationServiceTry.ExceptionHandler;

import com.example.NotificationServiceTry.dto.FailureResponseDto;
import com.example.NotificationServiceTry.dto.InvalidScrollIdDto;
import com.example.NotificationServiceTry.Exceptions.InvalidScrollIdException;
import com.example.NotificationServiceTry.Exceptions.NoSearchResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
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
    public ResponseEntity<FailureResponseDto> handleNoElementException(NoSuchElementException ex){
        String methodName = ex.getStackTrace()[1].getMethodName();
        log.error("Error occure in method : " + ex.getStackTrace()[1].getMethodName());

        if(methodName.equals("getSmsById")){
            return new ResponseEntity<>(new FailureResponseDto("request_id not found"),HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new FailureResponseDto("Error occured while fetching from Datbase"),HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FailureResponseDto> handlePhoneNumberMissingException(MethodArgumentNotValidException ex){
        String parameter = ex.getParameter().getParameterName();
        log.error("error occured in creating class - " + parameter);
        if(parameter.equals("smsCreateDto")){
            return new ResponseEntity<>(new FailureResponseDto("phone_number is mandatory"),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new FailureResponseDto("Argument Not valid"),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidScrollIdException.class)
    public ResponseEntity<InvalidScrollIdDto> handleInvalidScrollIdException(InvalidScrollIdException e){
        return new ResponseEntity<>(new InvalidScrollIdDto(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSearchResultException.class)
    public ResponseEntity<Map<String,String>> handleNoSearchResultException(NoSearchResultException e){
        Map<String,String> res = new HashMap<>();
        res.put("error","No result found/left");
        return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
    }


}