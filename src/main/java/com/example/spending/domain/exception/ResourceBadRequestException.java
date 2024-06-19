package com.example.spending.domain.exception;

public class ResourceBadRequestException extends RuntimeException {

    public ResourceBadRequestException(String message) {
        super(message);
    }
}
