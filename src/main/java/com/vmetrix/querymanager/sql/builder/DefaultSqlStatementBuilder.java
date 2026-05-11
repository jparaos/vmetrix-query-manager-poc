package com.vmetrix.querymanager.sql.builder;

import com.vmetrix.querymanager.query.model.QueryContext;
import com.vmetrix.querymanager.query.model.QueryDefinition;
import com.vmetrix.querymanager.sql.model.SqlStatement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultSqlStatementBuilder
        implements SqlStatementBuilder {

    private final SelectClauseBuilder selectClauseBuilder;

    private final FromClauseBuilder fromClauseBuilder;

    private final JoinClauseBuilder joinClauseBuilder;

    private final WhereClauseBuilder whereClauseBuilder;

    private final OrderByClauseBuilder orderByClauseBuilder;

    @Override
    public SqlStatement build(
            QueryDefinition queryDefinition,
            QueryContext context
    ) {

        return SqlStatement.builder()
                .selectClause(
                        selectClauseBuilder.build(
                                queryDefinition
                        )
                )
                .fromClause(
                        fromClauseBuilder.build(
                                queryDefinition
                        )
                )
                .joins(
                        joinClauseBuilder.build(context)
                )
                .whereClause(
                        whereClauseBuilder.build(
                                queryDefinition,
                                context
                        )
                )
                .parameters(
                        context.getParameters()
                )
                .orderByClause(
                        orderByClauseBuilder.build(
                                queryDefinition
                        )
                )
                .limit(
                        queryDefinition.getMaxResults()
                )
                .build();
    }
}