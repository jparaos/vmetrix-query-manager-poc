package com.vmetrix.querymanager.query.engine;

import com.vmetrix.querymanager.api.request.QueryBuildRequest;
import com.vmetrix.querymanager.api.response.QueryBuildResponse;
import com.vmetrix.querymanager.api.response.ValidationResponse;
import com.vmetrix.querymanager.query.model.ResolvedQuery;

public interface QueryEngine {

    QueryBuildResponse build(
            QueryBuildRequest request
    );

    ValidationResponse validate(
            QueryBuildRequest request
    );

    ResolvedQuery buildInternal(
            QueryBuildRequest request
    );
}