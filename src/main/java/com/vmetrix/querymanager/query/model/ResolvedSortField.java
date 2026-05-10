package com.vmetrix.querymanager.query.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResolvedSortField {

    private String physicalColumn;

    private String tableAlias;

    private String direction;
}