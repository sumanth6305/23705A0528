package com.affordmed.urlshortener.exception;

public class ExpiredLinkException extends RuntimeException {
    public ExpiredLinkException(String message) {
        super(message);
    }
}