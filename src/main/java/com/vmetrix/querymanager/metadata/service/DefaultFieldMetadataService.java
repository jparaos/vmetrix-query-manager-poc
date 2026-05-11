package com.vmetrix.querymanager.metadata.service;

import com.vmetrix.querymanager.metadata.model.EntityMetadata;
import com.vmetrix.querymanager.metadata.model.FieldMetadata;
import com.vmetrix.querymanager.shared.constants.ErrorMessages;
import com.vmetrix.querymanager.shared.exception.FieldNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultFieldMetadataService
        implements FieldMetadataService {

    private final EntityMetadataService entityMetadataService;

    @Override
    public FieldMetadata getField(
            String entityName,
            String fieldName
    ) {

        EntityMetadata entity =
                entityMetadataService.getEntity(entityName);

        return entity.getFields()
                .stream()
                .filter(field ->
                        field.getName().equals(fieldName)
                )
                .findFirst()
                .orElseThrow(() ->
                        new FieldNotFoundException(
                                String.format(
                                        ErrorMessages.FIELD_NOT_FOUND,
                                        fieldName,
                                        entityName
                                )
                        )
                );
    }

    @Override
    public boolean exists(
            String entityName,
            String fieldName
    ) {

        if (!entityMetadataService.exists(entityName)) {
            return false;
        }

        EntityMetadata entity =
                entityMetadataService.getEntity(entityName);

        return entity.getFields()
                .stream()
                .anyMatch(field ->
                        field.getName().equals(fieldName)
                );
    }
}