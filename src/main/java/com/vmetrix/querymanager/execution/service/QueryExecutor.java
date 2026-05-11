package com.vmetrix.querymanager.execution.service;

import com.vmetrix.querymanager.api.request.QueryBuildRequest;
import com.vmetrix.querymanager.api.response.QueryExecutionResponse;

public interface QueryExecutor {

    QueryExecutionResponse execute(
            QueryBuildRequest request
    );
}