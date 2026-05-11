package com.vmetrix.querymanager.metadata.service;

import com.vmetrix.querymanager.metadata.model.RelationshipMetadata;

import java.util.List;

public interface RelationshipMetadataService {

    List<RelationshipMetadata> getRelationships();

    RelationshipMetadata getRelationshipByAlias(String alias);

    RelationshipMetadata findByEntities(
            String sourceEntity,
            String targetEntity
    );
}