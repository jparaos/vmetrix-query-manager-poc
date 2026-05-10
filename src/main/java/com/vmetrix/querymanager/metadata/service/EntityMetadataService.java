package com.vmetrix.querymanager.metadata.service;

import com.vmetrix.querymanager.metadata.model.EntityMetadata;

import java.util.List;

public interface EntityMetadataService {

    EntityMetadata getEntity(String entityName);

    List<EntityMetadata> getAllEntities();

    boolean exists(String entityName);
}