package com.vmetrix.querymanager.metadata.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class MetadataRegistry {

    private Map<String, EntityMetadata> entities;

    private List<RelationshipMetadata> relationships;

    private Map<String, List<String>> comparators;
}