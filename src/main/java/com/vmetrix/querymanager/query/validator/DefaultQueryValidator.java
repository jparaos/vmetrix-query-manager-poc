package com.vmetrix.querymanager.query.validator;

import com.vmetrix.querymanager.query.model.QueryDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultQueryValidator implements QueryValidator {

    private final List<QueryValidationRule> validationRules;

    @Override
    public List<String> validate(QueryDefinition queryDefinition) {

        List<String> errors = new ArrayList<>();

        for (QueryValidationRule rule : validationRules) {

            errors.addAll(
                    rule.validate(queryDefinition)
            );
        }

        return errors;
    }
}