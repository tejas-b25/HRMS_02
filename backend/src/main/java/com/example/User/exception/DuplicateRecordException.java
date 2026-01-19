package com.example.User.exception;

public class DuplicateRecordException extends RuntimeException{

    public DuplicateRecordException(String message) {
        super(message);
    }
}
