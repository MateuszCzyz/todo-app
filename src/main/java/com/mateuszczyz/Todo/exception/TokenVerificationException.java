package com.mateuszczyz.Todo.exception;

public class TokenVerificationException extends RuntimeException {

    public TokenVerificationException(String message) {
        super(message);
    }
}
