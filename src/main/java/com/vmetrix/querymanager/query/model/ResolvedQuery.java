package com.vmetrix.querymanager.query.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResolvedQuery {

    private String sql;

    private Map<String, Object> parameters;

    private List<String> resolvedTables;

    private List<String> resolvedJoins;

    private Integer columnCount;

    private Integer filterCount;
}