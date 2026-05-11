package com.vmetrix.querymanager.sql.generator;

import com.vmetrix.querymanager.sql.model.SqlJoinClause;
import com.vmetrix.querymanager.sql.model.SqlOrderByClause;
import com.vmetrix.querymanager.sql.model.SqlQuery;
import com.vmetrix.querymanager.sql.model.SqlStatement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultSqlGenerator
        implements SqlGenerator {

    @Override
    public SqlQuery generate(
            SqlStatement statement
    ) {

        StringBuilder sql =
                new StringBuilder();

        appendSelect(statement, sql);

        appendFrom(statement, sql);

        appendJoins(statement, sql);

        appendWhere(statement, sql);

        appendOrderBy(statement, sql);

        appendLimit(statement, sql);

        return SqlQuery.builder()
                .sql(sql.toString())
                .parameters(statement.getParameters())
                .build();
    }

    private void appendSelect(
            SqlStatement statement,
            StringBuilder sql
    ) {

        sql.append("SELECT ");

        sql.append(
                String.join(
                        ", ",
                        statement.getSelectClause()
                                .getColumns()
                )
        );
    }

    private void appendFrom(
            SqlStatement statement,
            StringBuilder sql
    ) {

        sql.append(" FROM ");

        sql.append(
                statement.getFromClause()
                        .getTable()
        );

        sql.append(" ");

        sql.append(
                statement.getFromClause()
                        .getAlias()
        );
    }

    private void appendJoins(
            SqlStatement statement,
            StringBuilder sql
    ) {

        for (SqlJoinClause join :
                statement.getJoins()) {

            sql.append(" ");

            sql.append(join.getJoinType());

            sql.append(" JOIN ");

            sql.append(join.getTable());

            sql.append(" ");

            sql.append(join.getAlias());

            sql.append(" ON ");

            sql.append(join.getCondition());
        }
    }

    private void appendWhere(
            SqlStatement statement,
            StringBuilder sql
    ) {

        if (statement.getWhereClause() == null
                || statement.getWhereClause()
                .getExpression() == null
                || statement.getWhereClause()
                .getExpression()
                .isBlank()) {

            return;
        }

        sql.append(" WHERE ");

        sql.append(
                statement.getWhereClause()
                        .getExpression()
        );
    }

    private void appendOrderBy(
            SqlStatement statement,
            StringBuilder sql
    ) {

        SqlOrderByClause clause =
                statement.getOrderByClause();

        if (clause == null
                || clause.getFields().isEmpty()) {

            return;
        }

        sql.append(" ORDER BY ");

        sql.append(
                String.join(
                        ", ",
                        clause.getFields()
                )
        );
    }

    private void appendLimit(
            SqlStatement statement,
            StringBuilder sql
    ) {

        if (statement.getLimit() == null) {
            return;
        }

        sql.append(" FETCH FIRST ")
                .append(statement.getLimit())
                .append(" ROWS ONLY");
    }
}