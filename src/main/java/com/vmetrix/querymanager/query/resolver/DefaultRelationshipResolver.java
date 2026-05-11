package com.vmetrix.querymanager.query.resolver;

import com.vmetrix.querymanager.metadata.model.EntityMetadata;
import com.vmetrix.querymanager.metadata.model.FieldMetadata;
import com.vmetrix.querymanager.metadata.model.RelationshipMetadata;
import com.vmetrix.querymanager.metadata.model.ResolvedRelationship;
import com.vmetrix.querymanager.metadata.service.EntityMetadataService;
import com.vmetrix.querymanager.metadata.service.FieldMetadataService;
import com.vmetrix.querymanager.metadata.service.RelationshipMetadataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultRelationshipResolver
        implements RelationshipResolver {

    private final RelationshipMetadataService
            relationshipMetadataService;

    private final EntityMetadataService
            entityMetadataService;

    private final FieldMetadataService
            fieldMetadataService;

    @Override
    public ResolvedRelationship resolve(
            String relationshipAlias
    ) {

        RelationshipMetadata relationship =
                relationshipMetadataService
                        .getRelationshipByAlias(
                                relationshipAlias
                        );

        EntityMetadata sourceEntity =
                entityMetadataService.getEntity(
                        relationship.getSourceEntity()
                );

        EntityMetadata targetEntity =
                entityMetadataService.getEntity(
                        relationship.getTargetEntity()
                );

        FieldMetadata sourceField =
                fieldMetadataService.getField(
                        relationship.getSourceEntity(),
                        relationship.getSourceField()
                );

        FieldMetadata targetField =
                fieldMetadataService.getField(
                        relationship.getTargetEntity(),
                        relationship.getTargetField()
                );

        return ResolvedRelationship.builder()
                .alias(relationshipAlias)
                .sourceTable(sourceEntity.getPhysicalTable())
                .sourceAlias(sourceEntity.getAlias())
                .sourceColumn(sourceField.getPhysicalName())
                .targetTable(targetEntity.getPhysicalTable())
                .targetAlias(targetEntity.getAlias())
                .targetColumn(targetField.getPhysicalName())
                .joinType(relationship.getJoinType())
                .build();
    }

    @Override
    public RelationshipMetadata findRelationship(
            String relationshipAlias
    ) {

        return relationshipMetadataService
                .getRelationshipByAlias(
                        relationshipAlias
                );
    }

    @Override
    public RelationshipMetadata findByEntities(
            String sourceEntity,
            String targetEntity
    ) {

        return relationshipMetadataService
                .findByEntities(
                        sourceEntity,
                        targetEntity
                );
    }
}