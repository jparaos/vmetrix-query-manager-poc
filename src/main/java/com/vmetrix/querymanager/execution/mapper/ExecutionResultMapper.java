package com.vmetrix.querymanager.execution.mapper;

import com.vmetrix.querymanager.query.model.ResolvedQuery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ExecutionResultMapper {

    public QueryExecutionResponse map(
            ResolvedQuery resolvedQuery,
            List<Map<String, Object>> rows
    ) {

        return QueryExecutionResponse.builder()
                .sql(resolvedQuery.getSql())
                .parameters(
                        resolvedQuery.getParameters()
                )
                .rowCount(rows.size())
                .rows(rows)
                .build();
    }
}