package com.vmetrix.querymanager.query.resolver;

import com.vmetrix.querymanager.metadata.model.ResolvedFieldMetadata;

public interface FieldResolver {

    ResolvedFieldMetadata resolve(
            String entity,
            String field
    );
}