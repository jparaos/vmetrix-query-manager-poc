package com.vmetrix.querymanager.sql.builder;

import com.vmetrix.querymanager.metadata.model.EntityMetadata;
import com.vmetrix.querymanager.metadata.service.EntityMetadataService;
import com.vmetrix.querymanager.metadata.service.RelationshipMetadataService;
import com.vmetrix.querymanager.query.model.QueryDefinition;
import com.vmetrix.querymanager.query.model.SelectField;
import com.vmetrix.querymanager.sql.model.SqlFromClause;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultFromClauseBuilder
        implements FromClauseBuilder {

    private final EntityMetadataService entityMetadataService;

    private final RelationshipMetadataService relationshipMetadataService;

    @Override
    public SqlFromClause build(
            QueryDefinition queryDefinition
    ) {

        SelectField rootField =
                queryDefinition.getSelectFields()
                        .get(0);

        String entityName = rootField.getEntity();

        // If root field references a relationship alias, use the source entity for FROM
        if (!entityMetadataService.exists(entityName)
                && relationshipMetadataService.isAlias(entityName)) {
            entityName = relationshipMetadataService
                    .getRelationshipByAlias(entityName)
                    .getSourceEntity();
        }

        EntityMetadata entity =
                entityMetadataService.getEntity(entityName);

        return SqlFromClause.builder()
                .table(entity.getPhysicalTable())
                .alias(entity.getAlias())
                .build();
    }
}