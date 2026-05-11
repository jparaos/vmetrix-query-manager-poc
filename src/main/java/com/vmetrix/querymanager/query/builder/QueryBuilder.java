package com.vmetrix.querymanager.query.builder;

import com.vmetrix.querymanager.query.model.QueryDefinition;
import com.vmetrix.querymanager.query.model.ResolvedQuery;

public interface QueryBuilder {

    ResolvedQuery build(QueryDefinition queryDefinition);
}