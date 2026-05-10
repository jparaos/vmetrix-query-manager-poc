package com.vmetrix.querymanager.metadata.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResolvedFieldMetadata {

    private String entity;

    private String field;

    private String physicalColumn;

    private String tableAlias;

    private String dataType;
}