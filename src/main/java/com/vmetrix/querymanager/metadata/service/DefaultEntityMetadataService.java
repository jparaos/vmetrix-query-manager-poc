package com.vmetrix.querymanager.metadata.service;

import com.vmetrix.querymanager.metadata.model.EntityMetadata;
import com.vmetrix.querymanager.shared.constants.ErrorMessages;
import com.vmetrix.querymanager.shared.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultEntityMetadataService
        implements EntityMetadataService {

    private final MetadataRegistryService registryService;

    @Override
    public EntityMetadata getEntity(String entityName) {

        EntityMetadata metadata = registryService.getRegistry()
                .getEntities()
                .get(entityName);

        if (metadata == null) {

            throw new EntityNotFoundException(
                    String.format(
                            ErrorMessages.ENTITY_NOT_FOUND,
                            entityName
                    )
            );
        }

        return metadata;
    }

    @Override
    public List<EntityMetadata> getAllEntities() {

        return new ArrayList<>(
                registryService.getRegistry()
                        .getEntities()
                        .values()
        );
    }

    @Override
    public boolean exists(String entityName) {

        return registryService.getRegistry()
                .getEntities()
                .containsKey(entityName);
    }
}