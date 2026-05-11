package com.vmetrix.querymanager.query.validator;

import com.vmetrix.querymanager.query.model.QueryDefinition;
import com.vmetrix.querymanager.validation.model.ValidationError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultQueryValidator implements QueryValidator {

    private final List<QueryValidationRule> validationRules;

    @Override
    public List<ValidationError> validate(QueryDefinition queryDefinition) {

        List<ValidationError> errors = new ArrayList<>();

        for (QueryValidationRule rule : validationRules) {

            errors.addAll(
                    rule.validate(queryDefinition)
            );
        }

        return errors;
    }
}