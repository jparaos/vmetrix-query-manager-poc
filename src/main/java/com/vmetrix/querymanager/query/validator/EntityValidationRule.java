package com.vmetrix.querymanager.query.validator;

import com.vmetrix.querymanager.metadata.service.EntityMetadataService;
import com.vmetrix.querymanager.metadata.service.RelationshipMetadataService;
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
public class EntityValidationRule
        implements QueryValidationRule {

    private final EntityMetadataService entityMetadataService;

    private final RelationshipMetadataService relationshipMetadataService;

    private boolean isValidEntityReference(String name) {
        return entityMetadataService.exists(name)
                || relationshipMetadataService.isAlias(name);
    }

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

            if (!isValidEntityReference(
                    selectField.getEntity()
            )) {

                errors.add(ValidationError.builder()
                        .entity(selectField.getEntity())
                        .message("Entity '"
                                + selectField.getEntity()
                                + "' does not exist in the model")
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

            if (!isValidEntityReference(
                    sort.getEntity()
            )) {

                errors.add(ValidationError.builder()
                        .entity(sort.getEntity())
                        .message("Entity '"
                                + sort.getEntity()
                                + "' does not exist in the model")
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

            if (!isValidEntityReference(
                    conditionNode.getEntity()
            )) {

                errors.add(ValidationError.builder()
                        .entity(conditionNode.getEntity())
                        .message("Entity '"
                                + conditionNode.getEntity()
                                + "' does not exist in the model")
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