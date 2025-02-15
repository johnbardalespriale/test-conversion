package com.exchange.experience.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final String code;

    public CustomException(String message, String code) {
        super(message);
        this.code = code;
    }
}