package com.vmetrix.querymanager.execution.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class JdbcQueryExecutionService
        implements QueryExecutionService {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> execute(
            String sql,
            Map<String, Object> parameters
    ) {

        log.debug("Executing SQL: {}", sql);

        log.debug("SQL parameters: {}", parameters);

        return jdbcTemplate.queryForList(
                sql,
                parameters
        );
    }
}