package com.vmetrix.querymanager.validation.service;

import com.vmetrix.querymanager.api.response.ValidationErrorResponse;
import com.vmetrix.querymanager.api.response.ValidationResponse;
import com.vmetrix.querymanager.validation.model.ValidationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ValidationResponseMapper {

    private final ValidationErrorMapper validationErrorMapper;

    public ValidationResponse map(
            ValidationResult validationResult
    ) {

        List<ValidationErrorResponse> errors =
                validationResult.getErrors()
                        .stream()
                        .map(validationErrorMapper::map)
                        .toList();

        return ValidationResponse.builder()
                .valid(validationResult.isValid())
                .errors(errors)
                .build();
    }
}