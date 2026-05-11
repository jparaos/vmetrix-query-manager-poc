package com.vmetrix.querymanager.metadata.service;

import com.vmetrix.querymanager.metadata.cache.MetadataCache;
import com.vmetrix.querymanager.metadata.cache.MetadataCacheManager;
import com.vmetrix.querymanager.metadata.model.MetadataRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InMemoryMetadataRegistryService
        implements MetadataRegistryService {

    private final MetadataCache metadataCache;

    private final MetadataCacheManager metadataCacheManager;

    private MetadataRegistry registry;

    @Override
    public void initialize(
            MetadataRegistry registry
    ) {

        this.registry = registry;

        metadataCacheManager.initialize(
                registry
        );

        log.info(
                "Metadata registry loaded with {} entities and {} relationships",
                registry.getEntities().size(),
                registry.getRelationships().size()
        );
    }

    @Override
    public MetadataRegistry getRegistry() {

        return MetadataRegistry.builder()
                .entities(
                        metadataCache.getEntities()
                )
                .relationships(
                        metadataCache.getRelationships()
                )
                .comparators(
                        metadataCache.getComparators()
                )
                .build();
    }
}