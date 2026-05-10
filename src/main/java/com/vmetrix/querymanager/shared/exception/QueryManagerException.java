package com.vmetrix.querymanager.shared.exception;

public class QueryManagerException extends RuntimeException {

    public QueryManagerException(String message) {
        super(message);
    }

    public QueryManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}