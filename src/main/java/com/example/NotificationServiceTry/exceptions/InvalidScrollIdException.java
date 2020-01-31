package com.example.NotificationServiceTry.exceptions;

import org.elasticsearch.ElasticsearchException;

public class InvalidScrollIdException extends ElasticsearchException {

    public InvalidScrollIdException(Throwable cause) {
        super(cause);
    }
}
