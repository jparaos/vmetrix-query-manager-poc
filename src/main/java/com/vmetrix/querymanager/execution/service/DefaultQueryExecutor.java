package com.vmetrix.querymanager.execution.service;

import com.vmetrix.querymanager.api.request.QueryBuildRequest;
import com.vmetrix.querymanager.api.response.QueryExecutionResponse;
import com.vmetrix.querymanager.query.engine.QueryEngine;
import com.vmetrix.querymanager.query.model.ResolvedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DefaultQueryExecutor
        implements QueryExecutor {

    private final QueryEngine queryEngine;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public QueryExecutionResponse execute(
            QueryBuildRequest request
    ) {

        ResolvedQuery resolvedQuery =
                queryEngine.buildInternal(
                        request
                );

        List<Map<String, Object>> rows =
                jdbcTemplate.queryForList(
                        resolvedQuery.getSql(),
                        resolvedQuery.getParameters()
                );

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