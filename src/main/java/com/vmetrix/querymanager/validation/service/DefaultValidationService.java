package com.vmetrix.querymanager.validation.service;

import com.vmetrix.querymanager.query.model.QueryDefinition;
import com.vmetrix.querymanager.query.validator.QueryValidator;
import com.vmetrix.querymanager.validation.model.ValidationError;
import com.vmetrix.querymanager.validation.model.ValidationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultValidationService
        implements ValidationService {

    private final QueryValidator queryValidator;

    @Override
    public ValidationResult validate(
            QueryDefinition queryDefinition
    ) {

        List<String> validationMessages =
                queryValidator.validate(queryDefinition);

        ValidationResult result =
                ValidationResult.builder()
                        .valid(validationMessages.isEmpty())
                        .build();

        validationMessages.forEach(message ->
                result.addError(
                        ValidationError.builder()
                                .message(message)
                                .build()
                )
        );

        return result;
    }
}