package com.vmetrix.querymanager.metadata.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmetrix.querymanager.config.MetadataProperties;
import com.vmetrix.querymanager.metadata.model.ComparatorMetadata;
import com.vmetrix.querymanager.metadata.model.EntityMetadata;
import com.vmetrix.querymanager.metadata.model.MetadataRegistry;
import com.vmetrix.querymanager.metadata.model.RelationshipMetadata;
import com.vmetrix.querymanager.shared.constants.ErrorMessages;
import com.vmetrix.querymanager.shared.exception.MetadataException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JsonMetadataLoader implements MetadataLoader {

    private final ObjectMapper objectMapper;

    private final MetadataProperties metadataProperties;

    @Override
    public MetadataRegistry load() {

        try {

            Map<String, EntityMetadata> entities = loadEntities();

            List<RelationshipMetadata> relationships = loadRelationships();

            Map<String, List<String>> comparators = loadComparators();

            return MetadataRegistry.builder()
                    .entities(entities)
                    .relationships(relationships)
                    .comparators(comparators)
                    .build();

        } catch (Exception exception) {

            throw new MetadataException(
                    ErrorMessages.METADATA_LOAD_ERROR,
                    exception
            );
        }
    }

    private Map<String, EntityMetadata> loadEntities() throws Exception {

        Map<String, EntityMetadata> entities = new HashMap<>();

        PathMatchingResourcePatternResolver resolver =
                new PathMatchingResourcePatternResolver();

        Resource[] resources = resolver.getResources(
                "classpath:" + metadataProperties.getEntitiesPath() + "/*.json"
        );

        for (Resource resource : resources) {

            try (InputStream inputStream = resource.getInputStream()) {

                EntityMetadata metadata =
                        objectMapper.readValue(inputStream, EntityMetadata.class);

                entities.put(metadata.getEntity(), metadata);
            }
        }

        return entities;
    }

    private List<RelationshipMetadata> loadRelationships() throws Exception {

        Resource resource = new PathMatchingResourcePatternResolver()
                .getResource(
                        "classpath:" + metadataProperties.getRelationshipsPath()
                );

        try (InputStream inputStream = resource.getInputStream()) {

            return objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<RelationshipMetadata>>() {
                    }
            );
        }
    }

    private Map<String, List<String>> loadComparators() throws Exception {

        Resource resource = new PathMatchingResourcePatternResolver()
                .getResource(
                        "classpath:" + metadataProperties.getComparatorsPath()
                );

        try (InputStream inputStream = resource.getInputStream()) {

            return objectMapper.readValue(
                    inputStream,
                    new TypeReference<Map<String, List<String>>>() {
                    }
            );
        }
    }
}