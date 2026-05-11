package com.vmetrix.querymanager.query.validator;

import com.vmetrix.querymanager.query.model.QueryDefinition;
import com.vmetrix.querymanager.query.model.SortDefinition;
import com.vmetrix.querymanager.validation.model.ValidationError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SortValidationRule
        implements QueryValidationRule {

    @Override
    public List<ValidationError> validate(QueryDefinition queryDefinition) {

        List<ValidationError> errors = new ArrayList<>();

        if (queryDefinition.getSorting() == null) {
            return errors;
        }

        for (SortDefinition sort :
                queryDefinition.getSorting()) {

            String direction =
                    sort.getDirection();

            if (!"asc".equalsIgnoreCase(direction)
                    && !"desc".equalsIgnoreCase(direction)) {

                errors.add(ValidationError.builder()
                        .entity(sort.getEntity())
                        .field(sort.getField())
                        .message("Invalid sort direction '"
                                + direction
                                + "'. Must be 'asc' or 'desc'")
                        .build());
            }
        }

        return errors;
    }
}