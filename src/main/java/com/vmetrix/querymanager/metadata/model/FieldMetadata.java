package com.vmetrix.querymanager.metadata.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldMetadata {

    private String name;

    private String physicalName;

    private String type;

    private boolean primaryKey;

    private boolean filterable;

    private boolean selectable;
}