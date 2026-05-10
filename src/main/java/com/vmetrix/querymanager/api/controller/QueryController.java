package com.vmetrix.querymanager.api.controller;

import com.vmetrix.querymanager.api.request.QueryBuildRequest;
import com.vmetrix.querymanager.api.response.QueryBuildResponse;
import com.vmetrix.querymanager.api.response.ValidationResponse;
import com.vmetrix.querymanager.shared.constants.ApiPaths;
import com.vmetrix.querymanager.validation.service.ValidationService;
import com.vmetrix.querymanager.validation.service.ValidationResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPaths.QUERY)
public class QueryController {

    private final ValidationService validationService;

    private final ValidationResponseMapper validationResponseMapper;

    @PostMapping("/validate")
    public ResponseEntity<ValidationResponse> validate(
            @Valid @RequestBody QueryBuildRequest request
    ) {

        ValidationResponse response =
                validationResponseMapper.map(
                        validationService.validate(null)
                );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/build")
    public ResponseEntity<QueryBuildResponse> build(
            @Valid @RequestBody QueryBuildRequest request
    ) {

        return ResponseEntity.ok(
                QueryBuildResponse.builder()
                        .sql("-- SQL generation pending")
                        .build()
        );
    }
}