package com.vmetrix.querymanager.shared.exception;

public class MetadataNotFoundException
        extends QueryManagerException {

    public MetadataNotFoundException(
            String message
    ) {

        super(message);
    }

    public MetadataNotFoundException(
            String message,
            Throwable cause
    ) {

        super(message, cause);
    }
}