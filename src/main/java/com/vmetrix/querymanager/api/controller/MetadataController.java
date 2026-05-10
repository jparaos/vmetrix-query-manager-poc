package com.vmetrix.querymanager.api.controller;

import com.vmetrix.querymanager.api.response.ComparatorsResponse;
import com.vmetrix.querymanager.api.response.MetadataEntityResponse;
import com.vmetrix.querymanager.api.response.MetadataFieldResponse;
import com.vmetrix.querymanager.api.response.MetadataRelationshipResponse;
import com.vmetrix.querymanager.metadata.model.EntityMetadata;
import com.vmetrix.querymanager.metadata.model.FieldMetadata;
import com.vmetrix.querymanager.metadata.model.RelationshipMetadata;
import com.vmetrix.querymanager.metadata.service.ComparatorMetadataService;
import com.vmetrix.querymanager.metadata.service.EntityMetadataService;
import com.vmetrix.querymanager.metadata.service.RelationshipMetadataService;
import com.vmetrix.querymanager.shared.constants.ApiPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MetadataController {

    private final EntityMetadataService entityMetadataService;

    private final RelationshipMetadataService relationshipMetadataService;

    private final ComparatorMetadataService comparatorMetadataService;

    @GetMapping(ApiPaths.METADATA_ENTITIES)
    public List<MetadataEntityResponse> getEntities() {

        List<RelationshipMetadata> relationships =
                relationshipMetadataService.getRelationships();

        return entityMetadataService.getAllEntities()
                .stream()
                .map(entity -> mapEntity(entity, relationships))
                .collect(Collectors.toList());
    }

    @GetMapping(ApiPaths.METADATA_COMPARATORS)
    public ComparatorsResponse getComparators() {

        return ComparatorsResponse.builder()
                .comparators(
                        comparatorMetadataService.getAllComparators()
                )
                .build();
    }

    private MetadataEntityResponse mapEntity(
            EntityMetadata entity,
            List<RelationshipMetadata> relationships
    ) {

        List<MetadataFieldResponse> fields =
                entity.getFields()
                        .stream()
                        .map(this::mapField)
                        .collect(Collectors.toList());

        List<MetadataRelationshipResponse> relations =
                relationships.stream()
                        .filter(relationship ->
                                relationship.getSourceEntity()
                                        .equals(entity.getEntity())
                        )
                        .map(this::mapRelationship)
                        .collect(Collectors.toList());

        return MetadataEntityResponse.builder()
                .entity(entity.getEntity())
                .physicalTable(entity.getPhysicalTable())
                .alias(entity.getAlias())
                .fields(fields)
                .relations(relations)
                .build();
    }

    private MetadataFieldResponse mapField(
            FieldMetadata field
    ) {

        return MetadataFieldResponse.builder()
                .name(field.getName())
                .physicalName(field.getPhysicalName())
                .type(field.getType())
                .primaryKey(field.isPrimaryKey())
                .filterable(field.isFilterable())
                .selectable(field.isSelectable())
                .build();
    }

    private MetadataRelationshipResponse mapRelationship(
            RelationshipMetadata relationship
    ) {

        return MetadataRelationshipResponse.builder()
                .alias(relationship.getAlias())
                .targetEntity(relationship.getTargetEntity())
                .joinType(relationship.getJoinType())
                .sourceField(relationship.getSourceField())
                .targetField(relationship.getTargetField())
                .build();
    }
}