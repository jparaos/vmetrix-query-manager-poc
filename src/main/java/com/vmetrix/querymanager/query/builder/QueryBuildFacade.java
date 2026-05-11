package com.vmetrix.querymanager.query.builder;

import com.vmetrix.querymanager.query.model.QueryDefinition;
import com.vmetrix.querymanager.query.model.ResolvedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueryBuildFacade {

    private final QueryBuilder queryBuilder;

    public ResolvedQuery build(
            QueryDefinition queryDefinition
    ) {

        return queryBuilder.build(
                queryDefinition
        );
    }
}