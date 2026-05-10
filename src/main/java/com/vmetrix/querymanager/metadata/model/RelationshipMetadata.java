package com.vmetrix.querymanager.metadata.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelationshipMetadata {

    private String alias;

    private String sourceEntity;

    private String sourceField;

    private String targetEntity;

    private String targetField;

    private String joinType;
}