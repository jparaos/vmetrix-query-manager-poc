package com.vmetrix.querymanager.metadata.service;

import com.vmetrix.querymanager.metadata.model.FieldMetadata;

public interface FieldMetadataService {

    FieldMetadata getField(
            String entityName,
            String fieldName
    );

    boolean exists(
            String entityName,
            String fieldName
    );
}