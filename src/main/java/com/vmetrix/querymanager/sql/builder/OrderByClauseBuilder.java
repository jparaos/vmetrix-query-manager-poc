package com.vmetrix.querymanager.sql.builder;

import com.vmetrix.querymanager.query.model.QueryDefinition;
import com.vmetrix.querymanager.sql.model.SqlOrderByClause;

public interface OrderByClauseBuilder {

    SqlOrderByClause build(QueryDefinition queryDefinition);
}