package com.company.errors.exceptions;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(String message) {
        super(message);
    }
}
