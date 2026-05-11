package com.vmetrix.querymanager.sql.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SqlStatement {

    private SqlSelectClause selectClause;

    private SqlFromClause fromClause;

    @Builder.Default
    private List<SqlJoinClause> joins =
            new ArrayList<>();

    private SqlWhereClause whereClause;

    private SqlOrderByClause orderByClause;

    private Integer limit;

    @Builder.Default
    private Map<String, Object> parameters =
            new LinkedHashMap<>();
}