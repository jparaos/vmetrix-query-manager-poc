package com.vmetrix.querymanager.shared.exception;

public class MetadataException extends QueryManagerException {

    public MetadataException(String message) {
        super(message);
    }

    public MetadataException(String message, Throwable cause) {
        super(message, cause);
    }
}