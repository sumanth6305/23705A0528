package com.affordmed.urlshortener.exception;

public class ShortcodeCollisionException extends RuntimeException {
    public ShortcodeCollisionException(String message) {
        super(message);
    }
}