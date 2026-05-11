package com.vmetrix.querymanager.sql.builder;

import com.vmetrix.querymanager.query.model.QueryContext;
import com.vmetrix.querymanager.sql.model.SqlJoinClause;

import java.util.List;

public interface JoinClauseBuilder {

    List<SqlJoinClause> build(QueryContext context);
}