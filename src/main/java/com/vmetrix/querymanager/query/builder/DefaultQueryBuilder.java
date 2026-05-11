package com.vmetrix.querymanager.query.builder;

import com.vmetrix.querymanager.metadata.model.RelationshipMetadata;
import com.vmetrix.querymanager.query.model.QueryContext;
import com.vmetrix.querymanager.query.model.QueryDefinition;
import com.vmetrix.querymanager.query.model.ResolvedQuery;
import com.vmetrix.querymanager.query.resolver.QueryContextResolver;
import com.vmetrix.querymanager.sql.generator.SqlGenerationFacade;
import com.vmetrix.querymanager.sql.model.SqlQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class DefaultQueryBuilder
        implements QueryBuilder {

    private final QueryContextResolver queryContextResolver;

    private final SqlGenerationFacade sqlGenerationFacade;

    @Override
    public ResolvedQuery build(
            QueryDefinition queryDefinition
    ) {

        QueryContext context =
                queryContextResolver.resolve(
                        queryDefinition
                );

        SqlQuery sqlQuery =
                sqlGenerationFacade.generate(
                        queryDefinition,
                        context
                );

        return ResolvedQuery.builder()
                .sql(sqlQuery.getSql())
                .parameters(sqlQuery.getParameters())
                .resolvedTables(
                        new ArrayList<>(
                                context.getResolvedTables()
                        )
                )
                .resolvedJoins(
                        context.getResolvedRelationships()
                                .stream()
                                .map(RelationshipMetadata::getTargetEntity)
                                .toList()
                )
                .columnCount(
                        queryDefinition.getSelectFields().size()
                )
                .filterCount(
                        context.getParameters().size()
                )
                .build();
    }
}