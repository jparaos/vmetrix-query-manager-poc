package com.vmetrix.querymanager.sql.builder;

import com.vmetrix.querymanager.query.model.QueryContext;
import com.vmetrix.querymanager.query.model.QueryDefinition;
import com.vmetrix.querymanager.sql.model.SqlWhereClause;

public interface WhereClauseBuilder {

    SqlWhereClause build(
            QueryDefinition queryDefinition,
            QueryContext context
    );
}