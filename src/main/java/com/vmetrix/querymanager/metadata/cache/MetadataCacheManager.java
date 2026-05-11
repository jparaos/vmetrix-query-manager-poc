package com.vmetrix.querymanager.metadata.cache;

import com.vmetrix.querymanager.metadata.model.MetadataRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MetadataCacheManager {

    private final MetadataCache metadataCache;

    public void initialize(
            MetadataRegistry registry
    ) {

        metadataCache.cacheEntities(
                registry.getEntities()
        );

        metadataCache.cacheRelationships(
                registry.getRelationships()
        );

        metadataCache.cacheComparators(
                registry.getComparators()
        );
    }

    public void evictAll() {

        metadataCache.clear();
    }
}