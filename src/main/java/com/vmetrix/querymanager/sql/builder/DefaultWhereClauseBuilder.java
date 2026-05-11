package com.vmetrix.querymanager.sql.builder;

import com.vmetrix.querymanager.metadata.model.ResolvedFieldMetadata;
import com.vmetrix.querymanager.query.comparator.ComparatorFactory;
import com.vmetrix.querymanager.query.comparator.SqlComparator;
import com.vmetrix.querymanager.query.model.ConditionNode;
import com.vmetrix.querymanager.query.model.FilterNode;
import com.vmetrix.querymanager.query.model.GroupNode;
import com.vmetrix.querymanager.query.model.QueryContext;
import com.vmetrix.querymanager.query.model.QueryDefinition;
import com.vmetrix.querymanager.query.resolver.FieldResolver;
import com.vmetrix.querymanager.shared.util.SqlUtils;
import com.vmetrix.querymanager.sql.model.SqlWhereClause;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultWhereClauseBuilder
        implements WhereClauseBuilder {

    private final FieldResolver fieldResolver;

    private final ComparatorFactory comparatorFactory;

    @Override
    public SqlWhereClause build(
            QueryDefinition queryDefinition,
            QueryContext context
    ) {

        if (queryDefinition.getFilter() == null) {

            return null;
        }

        String expression =
                buildNode(
                        queryDefinition.getFilter(),
                        context
                );

        return SqlWhereClause.builder()
                .expression(expression)
                .build();
    }

    private String buildNode(
            FilterNode node,
            QueryContext context
    ) {

        if (node instanceof ConditionNode conditionNode) {

            return buildCondition(
                    conditionNode,
                    context
            );
        }

        if (node instanceof GroupNode groupNode) {

            return buildGroup(
                    groupNode,
                    context
            );
        }

        return "";
    }

    private String buildGroup(
            GroupNode group,
            QueryContext context
    ) {

        String operator =
                " " + group.getOperator() + " ";

        String expression =
                group.getConditions()
                        .stream()
                        .map(condition ->
                                buildNode(
                                        condition,
                                        context
                                )
                        )
                        .filter(value ->
                                !value.isBlank()
                        )
                        .reduce((left, right) ->
                                left + operator + right
                        )
                        .orElse("");

        return SqlUtils.wrap(expression);
    }

    private String buildCondition(
            ConditionNode condition,
            QueryContext context
    ) {

        ResolvedFieldMetadata resolved =
                fieldResolver.resolve(
                        condition.getEntity(),
                        condition.getField()
                );

        String qualifiedColumn =
                resolved.getTableAlias()
                        + "."
                        + resolved.getPhysicalColumn();

        String parameterName =
                nextParameter(context);

        SqlComparator comparator =
                comparatorFactory.get(
                        condition.getComparator()
                );

        registerParameters(
                comparator.getName(),
                parameterName,
                condition.getValue(),
                context
        );

        return comparator.build(
                qualifiedColumn,
                parameterName
        );
    }

    private String nextParameter(
            QueryContext context
    ) {

        String parameter =
                "p" + context.getParameterIndex();

        context.setParameterIndex(
                context.getParameterIndex() + 1
        );

        return parameter;
    }

    private void registerParameters(
            String comparator,
            String parameterName,
            Object value,
            QueryContext context
    ) {

        if ("isNull".equals(comparator)
                || "isNotNull".equals(comparator)) {

            return;
        }

        if ("between".equals(comparator)
                && value instanceof java.util.List<?> values
                && values.size() == 2) {

            context.getParameters().put(
                    parameterName + "_start",
                    values.get(0)
            );

            context.getParameters().put(
                    parameterName + "_end",
                    values.get(1)
            );

            return;
        }

        context.getParameters().put(
                parameterName,
                value
        );
    }
}