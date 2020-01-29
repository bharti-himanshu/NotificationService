package com.example.NotificationService.Exceptions;

import org.elasticsearch.ElasticsearchException;

public class InvalidScrollIdException extends ElasticsearchException {

    public InvalidScrollIdException(Throwable cause) {
        super(cause);
    }
}
