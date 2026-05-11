package com.vmetrix.querymanager.metadata.cache;

import com.vmetrix.querymanager.metadata.model.EntityMetadata;
import com.vmetrix.querymanager.metadata.model.RelationshipMetadata;

import java.util.List;
import java.util.Map;

public interface MetadataCache {

    void cacheEntities(
            Map<String, EntityMetadata> entities
    );

    void cacheRelationships(
            List<RelationshipMetadata> relationships
    );

    void cacheComparators(
            Map<String, List<String>> comparators
    );

    Map<String, EntityMetadata> getEntities();

    List<RelationshipMetadata> getRelationships();

    Map<String, List<String>> getComparators();

    void clear();
}