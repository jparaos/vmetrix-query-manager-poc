package com.vmetrix.querymanager.query.validator;

import com.vmetrix.querymanager.query.model.QueryDefinition;
import com.vmetrix.querymanager.query.model.SortDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SortValidationRule
        implements QueryValidationRule {

    @Override
    public List<String> validate(QueryDefinition queryDefinition) {

        List<String> errors = new ArrayList<>();

        if (queryDefinition.getSorting() == null) {
            return errors;
        }

        for (SortDefinition sort :
                queryDefinition.getSorting()) {

            String direction =
                    sort.getDirection();

            if (!"asc".equalsIgnoreCase(direction)
                    && !"desc".equalsIgnoreCase(direction)) {

                errors.add(
                        "Invalid sort direction: "
                                + direction
                );
            }
        }

        return errors;
    }
}