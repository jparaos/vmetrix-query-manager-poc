package com.vmetrix.querymanager.query.model;

import com.vmetrix.querymanager.metadata.model.RelationshipMetadata;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryContext {

    @Builder.Default
    private Set<String> resolvedTables = new LinkedHashSet<>();

    @Builder.Default
    private Set<RelationshipMetadata> resolvedRelationships =
            new LinkedHashSet<>();

    @Builder.Default
    private Map<String, Object> parameters =
            new LinkedHashMap<>();

    @Builder.Default
    private Integer parameterIndex = 1;
}