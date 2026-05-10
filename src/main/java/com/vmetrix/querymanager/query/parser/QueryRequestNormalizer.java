package com.vmetrix.querymanager.query.parser;

import com.vmetrix.querymanager.query.model.ConditionNode;
import com.vmetrix.querymanager.query.model.FilterNode;
import com.vmetrix.querymanager.query.model.GroupNode;
import com.vmetrix.querymanager.query.model.QueryDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueryRequestNormalizer {

    public QueryDefinition normalize(
            QueryDefinition queryDefinition
    ) {

        normalizeFilter(queryDefinition.getFilter());

        return queryDefinition;
    }

    private void normalizeFilter(
            FilterNode node
    ) {

        if (node == null) {
            return;
        }

        if (node instanceof ConditionNode conditionNode) {

            normalizeCondition(conditionNode);

            return;
        }

        if (node instanceof GroupNode groupNode) {

            groupNode.setOperator(
                    groupNode.getOperator()
                            .toUpperCase()
            );

            groupNode.getConditions()
                    .forEach(this::normalizeFilter);
        }
    }

    private void normalizeCondition(
            ConditionNode condition
    ) {

        if (condition.getComparator() != null) {

            condition.setComparator(
                    condition.getComparator().trim()
            );
        }
    }
}