package com.spikecorp.authserver.exception;

public class RoleNotExistsException extends RuntimeException {
    public RoleNotExistsException(String message) {
        super(message);
    }
}
