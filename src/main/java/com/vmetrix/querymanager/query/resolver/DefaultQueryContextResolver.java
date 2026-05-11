package com.vmetrix.querymanager.query.resolver;

import com.vmetrix.querymanager.metadata.model.RelationshipMetadata;
import com.vmetrix.querymanager.metadata.service.EntityMetadataService;
import com.vmetrix.querymanager.metadata.service.RelationshipMetadataService;
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

    private final RelationshipMetadataService relationshipMetadataService;

    private final EntityMetadataService entityMetadataService;

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

        // Already resolved this alias/entity
        boolean alreadyResolved = context.getResolvedRelationships()
                .stream()
                .anyMatch(r -> r.getAlias().equals(targetEntity));

        if (alreadyResolved) {
            return;
        }

        try {

            if (relationshipMetadataService.isAlias(targetEntity)) {
                // Request uses relationship alias (e.g. "counterparty") → SQL alias = alias name
                RelationshipMetadata relationship =
                        relationshipResolver.findRelationship(targetEntity);
                context.getResolvedRelationships().add(relationship);
                context.getRelationshipSqlAliases().put(relationship.getAlias(), targetEntity);
            } else {
                // Request uses direct entity name (e.g. "party") → SQL alias = entity's natural alias
                RelationshipMetadata relationship =
                        relationshipResolver.findByEntities(rootEntity, targetEntity);
                String sqlAlias = entityMetadataService.getEntity(targetEntity).getAlias();
                context.getResolvedRelationships().add(relationship);
                context.getRelationshipSqlAliases().put(relationship.getAlias(), sqlAlias);
            }

        } catch (Exception ignored) {
        }
    }
}