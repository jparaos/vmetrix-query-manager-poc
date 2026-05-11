package com.vmetrix.querymanager.api.controller;

import com.vmetrix.querymanager.api.request.QueryBuildRequest;
import com.vmetrix.querymanager.api.response.QueryBuildResponse;
import com.vmetrix.querymanager.api.response.ValidationResponse;
import com.vmetrix.querymanager.api.response.QueryExecutionResponse;
import com.vmetrix.querymanager.query.engine.QueryEngine;
import com.vmetrix.querymanager.shared.constants.ApiPaths;
import com.vmetrix.querymanager.execution.service.QueryExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;

@Tag(
        name = "Query API",
        description = "Dynamic query generation endpoints"
)
@Slf4j
@RestController
@RequiredArgsConstructor
public class QueryController {

    private final QueryEngine queryEngine;

    private final QueryExecutor queryExecutor;

    @Operation(
            summary = "Validate query request"
    )
    @PostMapping(ApiPaths.QUERY_VALIDATE)
    public ResponseEntity<ValidationResponse> validate(
            @Valid @RequestBody QueryBuildRequest request
    ) {

        return ResponseEntity.ok(
                queryEngine.validate(request)
        );
    }

    @Operation(
            summary = "Build dynamic SQL query"
    )
    @PostMapping(ApiPaths.QUERY_BUILD)
    public ResponseEntity<QueryBuildResponse> build(
            @Valid @RequestBody QueryBuildRequest request
    ) {

        return ResponseEntity.ok(
                queryEngine.build(request)
        );
    }

    @Operation(
            summary = "Execute dynamic SQL query"
    )
    @PostMapping(ApiPaths.QUERY_EXECUTE)
    public ResponseEntity<QueryExecutionResponse> execute(
            @Valid @RequestBody QueryBuildRequest request
    ) {

        return ResponseEntity.ok(
                queryExecutor.execute(request)
        );
    }
}