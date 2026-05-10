package com.vmetrix.querymanager.metadata.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntityMetadata {

    private String entity;

    private String physicalTable;

    private String alias;

    private String description;

    private List<FieldMetadata> fields;
}