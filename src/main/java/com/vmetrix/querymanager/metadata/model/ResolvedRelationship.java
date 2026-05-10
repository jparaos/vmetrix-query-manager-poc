package com.vmetrix.querymanager.metadata.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResolvedRelationship {

    private String alias;

    private String sourceTable;

    private String sourceAlias;

    private String sourceColumn;

    private String targetTable;

    private String targetAlias;

    private String targetColumn;

    private String joinType;
}