package com.vmetrix.querymanager.query.validator;

import com.vmetrix.querymanager.metadata.service.FieldMetadataService;
import com.vmetrix.querymanager.query.model.ConditionNode;
import com.vmetrix.querymanager.query.model.FilterNode;
import com.vmetrix.querymanager.query.model.GroupNode;
import com.vmetrix.querymanager.query.model.QueryDefinition;
import com.vmetrix.querymanager.query.model.SelectField;
import com.vmetrix.querymanager.query.model.SortDefinition;
import com.vmetrix.querymanager.validation.model.ValidationError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FieldValidationRule
        implements QueryValidationRule {

    private final FieldMetadataService fieldMetadataService;

    @Override
    public List<ValidationError> validate(QueryDefinition queryDefinition) {

        List<ValidationError> errors = new ArrayList<>();

        validateSelects(queryDefinition, errors);

        validateSorting(queryDefinition, errors);

        validateFilters(queryDefinition.getFilter(), errors);

        return errors;
    }

    private void validateSelects(
            QueryDefinition queryDefinition,
            List<ValidationError> errors
    ) {

        for (SelectField selectField :
                queryDefinition.getSelectFields()) {

            if (!fieldMetadataService.exists(
                    selectField.getEntity(),
                    selectField.getField()
            )) {

                errors.add(ValidationError.builder()
                        .entity(selectField.getEntity())
                        .field(selectField.getField())
                        .message("Field '"
                                + selectField.getField()
                                + "' does not exist in entity '"
                                + selectField.getEntity() + "'")
                        .build());
            }
        }
    }

    private void validateSorting(
            QueryDefinition queryDefinition,
            List<ValidationError> errors
    ) {

        if (queryDefinition.getSorting() == null) {
            return;
        }

        for (SortDefinition sort :
                queryDefinition.getSorting()) {

            if (!fieldMetadataService.exists(
                    sort.getEntity(),
                    sort.getField()
            )) {

                errors.add(ValidationError.builder()
                        .entity(sort.getEntity())
                        .field(sort.getField())
                        .message("Field '"
                                + sort.getField()
                                + "' does not exist in entity '"
                                + sort.getEntity() + "'")
                        .build());
            }
        }
    }

    private void validateFilters(
            FilterNode node,
            List<ValidationError> errors
    ) {

        if (node == null) {
            return;
        }

        if (node instanceof ConditionNode conditionNode) {

            if (!fieldMetadataService.exists(
                    conditionNode.getEntity(),
                    conditionNode.getField()
            )) {

                errors.add(ValidationError.builder()
                        .entity(conditionNode.getEntity())
                        .field(conditionNode.getField())
                        .message("Field '"
                                + conditionNode.getField()
                                + "' does not exist in entity '"
                                + conditionNode.getEntity() + "'")
                        .build());
            }

            return;
        }

        if (node instanceof GroupNode groupNode) {

            for (FilterNode child :
                    groupNode.getConditions()) {

                validateFilters(child, errors);
            }
        }
    }
}