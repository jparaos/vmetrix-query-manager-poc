package com.vmetrix.querymanager.query.resolver;

import com.vmetrix.querymanager.metadata.model.EntityMetadata;
import com.vmetrix.querymanager.metadata.model.FieldMetadata;
import com.vmetrix.querymanager.metadata.model.ResolvedFieldMetadata;
import com.vmetrix.querymanager.metadata.service.EntityMetadataService;
import com.vmetrix.querymanager.metadata.service.FieldMetadataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultFieldResolver
        implements FieldResolver {

    private final EntityMetadataService entityMetadataService;

    private final FieldMetadataService fieldMetadataService;

    @Override
    public ResolvedFieldMetadata resolve(
            String entity,
            String field
    ) {

        EntityMetadata entityMetadata =
                entityMetadataService.getEntity(entity);

        FieldMetadata fieldMetadata =
                fieldMetadataService.getField(
                        entity,
                        field
                );

        return ResolvedFieldMetadata.builder()
                .entity(entity)
                .field(field)
                .physicalColumn(fieldMetadata.getPhysicalName())
                .tableAlias(entityMetadata.getAlias())
                .dataType(fieldMetadata.getType())
                .build();
    }
}