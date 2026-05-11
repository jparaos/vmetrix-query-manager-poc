package com.vmetrix.querymanager.execution.service;

import java.util.List;
import java.util.Map;

public interface QueryExecutionService {

    List<Map<String, Object>> execute(
            String sql,
            Map<String, Object> parameters
    );
}