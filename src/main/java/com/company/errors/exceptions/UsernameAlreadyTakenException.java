package com.company.errors.exceptions;

public class UsernameAlreadyTakenException extends RuntimeException {

    public UsernameAlreadyTakenException(String message){
        super(message);
    }
}
