package com.vmetrix.querymanager.query.resolver;

import com.vmetrix.querymanager.query.model.QueryContext;
import com.vmetrix.querymanager.query.model.QueryDefinition;

public interface QueryContextResolver {

    QueryContext resolve(QueryDefinition queryDefinition);
}