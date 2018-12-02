package com.example.snack.exception;

import org.springframework.http.HttpStatus;

public class MessageException extends RuntimeException {

    private static final long serialVersionUID = 4126949445748298368L;

    private String message;

    private HttpStatus statusCode;

    public MessageException(String message, HttpStatus statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public MessageException(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
