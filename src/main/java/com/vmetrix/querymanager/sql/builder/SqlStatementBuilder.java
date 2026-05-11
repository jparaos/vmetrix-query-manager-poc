package com.vmetrix.querymanager.sql.builder;

import com.vmetrix.querymanager.query.model.QueryContext;
import com.vmetrix.querymanager.query.model.QueryDefinition;
import com.vmetrix.querymanager.sql.model.SqlStatement;

public interface SqlStatementBuilder {

    SqlStatement build(
            QueryDefinition queryDefinition,
            QueryContext context
    );
}