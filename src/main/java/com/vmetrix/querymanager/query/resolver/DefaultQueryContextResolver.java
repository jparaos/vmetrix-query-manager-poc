package com.vmetrix.querymanager.query.resolver;

import com.vmetrix.querymanager.metadata.model.RelationshipMetadata;
import com.vmetrix.querymanager.query.model.ConditionNode;
import com.vmetrix.querymanager.query.model.FilterNode;
import com.vmetrix.querymanager.query.model.GroupNode;
import com.vmetrix.querymanager.query.model.QueryContext;
import com.vmetrix.querymanager.query.model.QueryDefinition;
import com.vmetrix.querymanager.query.model.SelectField;
import com.vmetrix.querymanager.query.model.SortDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultQueryContextResolver
        implements QueryContextResolver {

    private final RelationshipResolver relationshipResolver;

    @Override
    public QueryContext resolve(
            QueryDefinition queryDefinition
    ) {

        QueryContext context =
                QueryContext.builder()
                        .build();

        resolveSelects(queryDefinition, context);

        resolveSorting(queryDefinition, context);

        resolveFilters(
                queryDefinition.getFilter(),
                context
        );

        return context;
    }

    private void resolveSelects(
            QueryDefinition queryDefinition,
            QueryContext context
    ) {

        for (SelectField field :
                queryDefinition.getSelectFields()) {

            context.getResolvedTables()
                    .add(field.getEntity());

            resolveRelationship(field.getEntity(), context);
        }
    }

    private void resolveSorting(
            QueryDefinition queryDefinition,
            QueryContext context
    ) {

        if (queryDefinition.getSorting() == null) {
            return;
        }

        for (SortDefinition sort :
                queryDefinition.getSorting()) {

            context.getResolvedTables()
                    .add(sort.getEntity());

            resolveRelationship(sort.getEntity(), context);
        }
    }

    private void resolveFilters(
            FilterNode node,
            QueryContext context
    ) {

        if (node == null) {
            return;
        }

        if (node instanceof ConditionNode conditionNode) {

            context.getResolvedTables()
                    .add(conditionNode.getEntity());

            resolveRelationship(
                    conditionNode.getEntity(),
                    context
            );

            return;
        }

        if (node instanceof GroupNode groupNode) {

            groupNode.getConditions()
                    .forEach(condition ->
                            resolveFilters(condition, context)
                    );
        }
    }

    private void resolveRelationship(
            String entity,
            QueryContext context
    ) {

        if ("transaction".equals(entity)) {
            return;
        }

        try {

            RelationshipMetadata relationship =
                    relationshipResolver
                            .findRelationship(entity);

            context.getResolvedRelationships()
                    .add(relationship);

        } catch (Exception ignored) {
        }
    }
}