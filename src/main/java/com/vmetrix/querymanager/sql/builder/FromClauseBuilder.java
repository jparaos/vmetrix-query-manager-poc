package com.vmetrix.querymanager.sql.builder;

import com.vmetrix.querymanager.query.model.QueryDefinition;
import com.vmetrix.querymanager.sql.model.SqlFromClause;

public interface FromClauseBuilder {

    SqlFromClause build(QueryDefinition queryDefinition);
}