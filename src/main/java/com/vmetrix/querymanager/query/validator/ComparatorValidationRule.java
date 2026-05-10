package com.vmetrix.querymanager.query.validator;

import com.vmetrix.querymanager.metadata.model.FieldMetadata;
import com.vmetrix.querymanager.metadata.service.ComparatorMetadataService;
import com.vmetrix.querymanager.metadata.service.FieldMetadataService;
import com.vmetrix.querymanager.query.model.ConditionNode;
import com.vmetrix.querymanager.query.model.FilterNode;
import com.vmetrix.querymanager.query.model.GroupNode;
import com.vmetrix.querymanager.query.model.QueryDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ComparatorValidationRule
        implements QueryValidationRule {

    private final FieldMetadataService fieldMetadataService;

    private final ComparatorMetadataService comparatorMetadataService;

    @Override
    public List<String> validate(QueryDefinition queryDefinition) {

        List<String> errors = new ArrayList<>();

        validateFilters(queryDefinition.getFilter(), errors);

        return errors;
    }

    private void validateFilters(
            FilterNode node,
            List<String> errors
    ) {

        if (node == null) {
            return;
        }

        if (node instanceof ConditionNode conditionNode) {

            validateCondition(conditionNode, errors);

            return;
        }

        if (node instanceof GroupNode groupNode) {

            for (FilterNode child :
                    groupNode.getConditions()) {

                validateFilters(child, errors);
            }
        }
    }

    private void validateCondition(
            ConditionNode condition,
            List<String> errors
    ) {

        if (!fieldMetadataService.exists(
                condition.getEntity(),
                condition.getField()
        )) {

            return;
        }

        FieldMetadata fieldMetadata =
                fieldMetadataService.getField(
                        condition.getEntity(),
                        condition.getField()
                );

        boolean valid =
                comparatorMetadataService.isValid(
                        fieldMetadata.getType(),
                        condition.getComparator()
                );

        if (!valid) {

            errors.add(
                    "Comparator "
                            + condition.getComparator()
                            + " is not valid for type "
                            + fieldMetadata.getType()
            );
        }
    }
}