package com.unifier.exception;

public class AuthTokenException extends RuntimeException {
    public AuthTokenException() {
    }

    public AuthTokenException(String message) {
        super(message);
    }

    public AuthTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthTokenException(Throwable cause) {
        super(cause);
    }

    public AuthTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
