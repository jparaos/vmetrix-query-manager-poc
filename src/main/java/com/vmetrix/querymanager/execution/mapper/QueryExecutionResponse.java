package com.vmetrix.querymanager.execution.mapper;

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
public class QueryExecutionResponse {

    private String sql;

    private Map<String, Object> parameters;

    private Integer rowCount;

    private List<Map<String, Object>> rows;
}