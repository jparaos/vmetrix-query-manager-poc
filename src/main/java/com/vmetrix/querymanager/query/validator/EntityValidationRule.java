package com.vmetrix.querymanager.query.validator;

import com.vmetrix.querymanager.metadata.service.EntityMetadataService;
import com.vmetrix.querymanager.query.model.ConditionNode;
import com.vmetrix.querymanager.query.model.FilterNode;
import com.vmetrix.querymanager.query.model.GroupNode;
import com.vmetrix.querymanager.query.model.QueryDefinition;
import com.vmetrix.querymanager.query.model.SelectField;
import com.vmetrix.querymanager.query.model.SortDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EntityValidationRule
        implements QueryValidationRule {

    private final EntityMetadataService entityMetadataService;

    @Override
    public List<String> validate(QueryDefinition queryDefinition) {

        List<String> errors = new ArrayList<>();

        validateSelects(queryDefinition, errors);

        validateSorting(queryDefinition, errors);

        validateFilters(queryDefinition.getFilter(), errors);

        return errors;
    }

    private void validateSelects(
            QueryDefinition queryDefinition,
            List<String> errors
    ) {

        for (SelectField selectField :
                queryDefinition.getSelectFields()) {

            if (!entityMetadataService.exists(
                    selectField.getEntity()
            )) {

                errors.add(
                        "Unknown entity: "
                                + selectField.getEntity()
                );
            }
        }
    }

    private void validateSorting(
            QueryDefinition queryDefinition,
            List<String> errors
    ) {

        if (queryDefinition.getSorting() == null) {
            return;
        }

        for (SortDefinition sort :
                queryDefinition.getSorting()) {

            if (!entityMetadataService.exists(
                    sort.getEntity()
            )) {

                errors.add(
                        "Unknown entity: "
                                + sort.getEntity()
                );
            }
        }
    }

    private void validateFilters(
            FilterNode node,
            List<String> errors
    ) {

        if (node == null) {
            return;
        }

        if (node instanceof ConditionNode conditionNode) {

            if (!entityMetadataService.exists(
                    conditionNode.getEntity()
            )) {

                errors.add(
                        "Unknown entity: "
                                + conditionNode.getEntity()
                );
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