package com.vmetrix.querymanager.validation.service;

import com.vmetrix.querymanager.api.response.ValidationErrorResponse;
import com.vmetrix.querymanager.validation.model.ValidationError;
import org.springframework.stereotype.Component;

@Component
public class ValidationErrorMapper {

    public ValidationErrorResponse map(
            ValidationError validationError
    ) {

        return ValidationErrorResponse.builder()
                .entity(validationError.getEntity())
                .field(validationError.getField())
                .comparator(validationError.getComparator())
                .message(validationError.getMessage())
                .build();
    }
}