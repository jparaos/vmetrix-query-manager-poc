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

        String rootEntity =
                resolveRootEntity(queryDefinition);

        resolveSelects(
                queryDefinition,
                context,
                rootEntity
        );

        resolveSorting(
                queryDefinition,
                context,
                rootEntity
        );

        resolveFilters(
                queryDefinition.getFilter(),
                context,
                rootEntity
        );

        return context;
    }

    private String resolveRootEntity(
            QueryDefinition queryDefinition
    ) {

        return queryDefinition.getSelectFields()
                .get(0)
                .getEntity();
    }

    private void resolveSelects(
            QueryDefinition queryDefinition,
            QueryContext context,
            String rootEntity
    ) {

        for (SelectField field :
                queryDefinition.getSelectFields()) {

            context.getResolvedTables()
                    .add(field.getEntity());

            resolveRelationship(
                    rootEntity,
                    field.getEntity(),
                    context
            );
        }
    }

    private void resolveSorting(
            QueryDefinition queryDefinition,
            QueryContext context,
            String rootEntity
    ) {

        if (queryDefinition.getSorting() == null) {
            return;
        }

        for (SortDefinition sort :
                queryDefinition.getSorting()) {

            context.getResolvedTables()
                    .add(sort.getEntity());

            resolveRelationship(
                    rootEntity,
                    sort.getEntity(),
                    context
            );
        }
    }

    private void resolveFilters(
            FilterNode node,
            QueryContext context,
            String rootEntity
    ) {

        if (node == null) {
            return;
        }

        if (node instanceof ConditionNode conditionNode) {

            context.getResolvedTables()
                    .add(conditionNode.getEntity());

            resolveRelationship(
                    rootEntity,
                    conditionNode.getEntity(),
                    context
            );

            return;
        }

        if (node instanceof GroupNode groupNode) {

            groupNode.getConditions()
                    .forEach(condition ->
                            resolveFilters(
                                    condition,
                                    context,
                                    rootEntity
                            )
                    );
        }
    }

    private void resolveRelationship(
            String rootEntity,
            String targetEntity,
            QueryContext context
    ) {

        if (rootEntity.equals(targetEntity)) {
            return;
        }

        try {

            RelationshipMetadata relationship =
                    relationshipResolver
                            .findByEntities(
                                    rootEntity,
                                    targetEntity
                            );

            context.getResolvedRelationships()
                    .add(relationship);

        } catch (Exception ignored) {
        }
    }
}