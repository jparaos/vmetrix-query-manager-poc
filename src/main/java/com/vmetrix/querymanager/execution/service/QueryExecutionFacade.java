package com.vmetrix.querymanager.execution.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class QueryExecutionFacade {

    private final QueryExecutionService
            queryExecutionService;

    public List<Map<String, Object>> execute(
            String sql,
            Map<String, Object> parameters
    ) {

        return queryExecutionService.execute(
                sql,
                parameters
        );
    }
}