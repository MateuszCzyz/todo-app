package com.mateuszczyz.Todo.exception;

public class InvalidTaskFormatException extends RuntimeException {

    public InvalidTaskFormatException(String message) {
        super(message);
    }
}
