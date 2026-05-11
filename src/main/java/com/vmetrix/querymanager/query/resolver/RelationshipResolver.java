package com.vmetrix.querymanager.query.resolver;

import com.vmetrix.querymanager.metadata.model.RelationshipMetadata;
import com.vmetrix.querymanager.metadata.model.ResolvedRelationship;

public interface RelationshipResolver {

    ResolvedRelationship resolve(
            String relationshipAlias
    );

    ResolvedRelationship resolve(
            String relationshipAlias,
            String sqlTargetAlias
    );

    RelationshipMetadata findRelationship(
            String relationshipAlias
    );

    RelationshipMetadata findByEntities(
            String sourceEntity,
            String targetEntity
    );
}