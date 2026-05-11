package com.vmetrix.querymanager.metadata.cache;

import com.vmetrix.querymanager.metadata.model.EntityMetadata;
import com.vmetrix.querymanager.metadata.model.RelationshipMetadata;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryMetadataCache
        implements MetadataCache {

    private final Map<String, EntityMetadata> entities =
            new ConcurrentHashMap<>();

    private volatile List<RelationshipMetadata> relationships =
            Collections.emptyList();

    private volatile Map<String, List<String>> comparators =
            Collections.emptyMap();

    @Override
    public void cacheEntities(
            Map<String, EntityMetadata> entities
    ) {

        this.entities.clear();

        this.entities.putAll(entities);
    }

    @Override
    public void cacheRelationships(
            List<RelationshipMetadata> relationships
    ) {

        this.relationships = relationships;
    }

    @Override
    public void cacheComparators(
            Map<String, List<String>> comparators
    ) {

        this.comparators = comparators;
    }

    @Override
    public Map<String, EntityMetadata> getEntities() {

        return Collections.unmodifiableMap(
                entities
        );
    }

    @Override
    public List<RelationshipMetadata> getRelationships() {

        return Collections.unmodifiableList(
                relationships
        );
    }

    @Override
    public Map<String, List<String>> getComparators() {

        return Collections.unmodifiableMap(
                comparators
        );
    }

    @Override
    public void clear() {

        entities.clear();

        relationships = Collections.emptyList();

        comparators = Collections.emptyMap();
    }
}