package com.vmetrix.querymanager.sql.builder;

import com.vmetrix.querymanager.query.model.QueryDefinition;
import com.vmetrix.querymanager.sql.model.SqlSelectClause;

public interface SelectClauseBuilder {

    SqlSelectClause build(QueryDefinition queryDefinition);
}