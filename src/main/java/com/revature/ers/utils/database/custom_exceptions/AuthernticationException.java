package com.revature.ers.utils.database.custom_exceptions;

public class AuthernticationException extends RuntimeException{
    public AuthernticationException() {
    }

    public AuthernticationException(String message) {
        super(message);
    }

    public AuthernticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthernticationException(Throwable cause) {
        super(cause);
    }

    public AuthernticationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
