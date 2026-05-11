package com.vmetrix.querymanager.query.resolver;

import com.vmetrix.querymanager.metadata.model.EntityMetadata;
import com.vmetrix.querymanager.metadata.model.FieldMetadata;
import com.vmetrix.querymanager.metadata.model.ResolvedFieldMetadata;
import com.vmetrix.querymanager.metadata.service.EntityMetadataService;
import com.vmetrix.querymanager.metadata.service.FieldMetadataService;
import com.vmetrix.querymanager.metadata.service.RelationshipMetadataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultFieldResolver
        implements FieldResolver {

    private final EntityMetadataService entityMetadataService;

    private final FieldMetadataService fieldMetadataService;

    private final RelationshipMetadataService relationshipMetadataService;

    @Override
    public ResolvedFieldMetadata resolve(
            String entity,
            String field
    ) {

        boolean isAlias = !entityMetadataService.exists(entity)
                && relationshipMetadataService.isAlias(entity);

        String resolvedEntity = isAlias
                ? relationshipMetadataService.resolveTargetEntity(entity)
                : entity;

        EntityMetadata entityMetadata =
                entityMetadataService.getEntity(resolvedEntity);

        FieldMetadata fieldMetadata =
                fieldMetadataService.getField(
                        entity,
                        field
                );

        // When accessed via relationship alias, use alias as SQL table alias
        // so counterparty and issuer (both → PARTY) get distinct aliases in SQL
        String tableAlias = isAlias ? entity : entityMetadata.getAlias();

        return ResolvedFieldMetadata.builder()
                .entity(entity)
                .field(field)
                .physicalColumn(fieldMetadata.getPhysicalName())
                .tableAlias(tableAlias)
                .dataType(fieldMetadata.getType())
                .build();
    }
}