package com.vmetrix.querymanager.sql.generator;

import com.vmetrix.querymanager.query.model.QueryContext;
import com.vmetrix.querymanager.query.model.QueryDefinition;
import com.vmetrix.querymanager.sql.builder.SqlStatementBuilder;
import com.vmetrix.querymanager.sql.model.SqlQuery;
import com.vmetrix.querymanager.sql.model.SqlStatement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SqlGenerationFacade {

    private final SqlStatementBuilder sqlStatementBuilder;

    private final SqlGenerator sqlGenerator;

    private final SqlPrettyPrinter sqlPrettyPrinter;

    public SqlQuery generate(
            QueryDefinition queryDefinition,
            QueryContext context
    ) {

        SqlStatement statement =
                sqlStatementBuilder.build(
                        queryDefinition,
                        context
                );

        SqlQuery sqlQuery =
                sqlGenerator.generate(statement);

        sqlQuery.setSql(
                sqlPrettyPrinter.format(
                        sqlQuery.getSql()
                )
        );

        return sqlQuery;
    }
}