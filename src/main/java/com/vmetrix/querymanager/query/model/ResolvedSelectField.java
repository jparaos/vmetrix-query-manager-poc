package com.vmetrix.querymanager.query.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResolvedSelectField {

    private String entity;

    private String field;

    private String physicalColumn;

    private String tableAlias;

    private String outputAlias;
}