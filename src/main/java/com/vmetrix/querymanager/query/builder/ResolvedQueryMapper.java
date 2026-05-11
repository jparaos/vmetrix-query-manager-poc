package com.vmetrix.querymanager.query.builder;

import com.vmetrix.querymanager.api.response.QueryBuildResponse;
import com.vmetrix.querymanager.api.response.QueryMetadataResponse;
import com.vmetrix.querymanager.query.model.ResolvedQuery;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ResolvedQueryMapper {

    public QueryBuildResponse map(
            ResolvedQuery resolvedQuery
    ) {

        return QueryBuildResponse.builder()
                .sql(resolvedQuery.getSql())
                .parameters(resolvedQuery.getParameters())
                .resolvedTables(
                        resolvedQuery.getResolvedTables()
                )
                .resolvedJoins(
                        resolvedQuery.getResolvedJoins()
                )
                .metadata(
                        QueryMetadataResponse.builder()
                                .generatedAt(Instant.now())
                                .build()
                )
                .build();
    }
}