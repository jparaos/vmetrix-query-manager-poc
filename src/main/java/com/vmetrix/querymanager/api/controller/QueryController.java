package com.vmetrix.querymanager.api.controller;

import com.vmetrix.querymanager.api.request.QueryBuildRequest;
import com.vmetrix.querymanager.api.response.QueryBuildResponse;
import com.vmetrix.querymanager.api.response.ValidationResponse;
import com.vmetrix.querymanager.query.engine.QueryEngine;
import com.vmetrix.querymanager.shared.constants.ApiPaths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class QueryController {

    private final QueryEngine queryEngine;

    @PostMapping(ApiPaths.QUERY_VALIDATE)
    public ResponseEntity<ValidationResponse> validate(
            @Valid @RequestBody QueryBuildRequest request
    ) {

        return ResponseEntity.ok(
                queryEngine.validate(request)
        );
    }

    @PostMapping(ApiPaths.QUERY_BUILD)
    public ResponseEntity<QueryBuildResponse> build(
            @Valid @RequestBody QueryBuildRequest request
    ) {
        log.info("Received query build request");
        return ResponseEntity.ok(
                queryEngine.build(request)
        );
    }
}