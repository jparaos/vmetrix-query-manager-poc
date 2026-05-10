package com.vmetrix.querymanager.metadata.service;

import com.vmetrix.querymanager.metadata.model.MetadataRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InMemoryMetadataRegistryService
        implements MetadataRegistryService {

    private MetadataRegistry registry;

    @Override
    public void initialize(MetadataRegistry registry) {

        this.registry = registry;

        log.info(
                "Metadata registry loaded with {} entities and {} relationships",
                registry.getEntities().size(),
                registry.getRelationships().size()
        );
    }

    @Override
    public MetadataRegistry getRegistry() {
        return registry;
    }
}