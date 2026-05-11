package com.vmetrix.querymanager.metadata.service;

import com.vmetrix.querymanager.metadata.model.RelationshipMetadata;
import com.vmetrix.querymanager.shared.constants.ErrorMessages;
import com.vmetrix.querymanager.shared.exception.JoinResolutionException;
import com.vmetrix.querymanager.shared.exception.MetadataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultRelationshipMetadataService
        implements RelationshipMetadataService {

    private final MetadataRegistryService registryService;

    @Override
    public List<RelationshipMetadata> getRelationships() {

        return registryService.getRegistry()
                .getRelationships();
    }

    @Override
    public RelationshipMetadata getRelationshipByAlias(String alias) {

        return registryService.getRegistry()
                .getRelationships()
                .stream()
                .filter(relationship ->
                        relationship.getAlias().equals(alias)
                )
                .findFirst()
                .orElseThrow(() ->
                        new JoinResolutionException(
                                String.format(
                                        ErrorMessages.RELATIONSHIP_NOT_FOUND,
                                        alias
                                )
                        )
                );
    }

    @Override
    public RelationshipMetadata findByEntities(
            String sourceEntity,
            String targetEntity
    ) {

        return registryService.getRegistry()
                .getRelationships()
                .stream()
                .filter(relationship ->
                        relationship.getSourceEntity()
                                .equals(sourceEntity)
                                &&
                                relationship.getTargetEntity()
                                        .equals(targetEntity)
                )
                .findFirst()
                .orElseThrow(() ->
                        new MetadataNotFoundException(
                                "Relationship not found between "
                                        + sourceEntity
                                        + " and "
                                        + targetEntity
                        )
                );
    }
}