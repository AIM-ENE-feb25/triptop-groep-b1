package org.prototype.demo.common.exception;

public class FacadeException extends RuntimeException {
    public FacadeException(String message) {
        super(message);
    }

    public FacadeException(String message, Throwable cause) {
        super(message, cause);
    }
} 