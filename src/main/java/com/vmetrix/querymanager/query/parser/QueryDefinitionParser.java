package com.vmetrix.querymanager.query.parser;

import com.vmetrix.querymanager.api.request.QueryBuildRequest;
import com.vmetrix.querymanager.query.model.QueryDefinition;

public interface QueryDefinitionParser {

    QueryDefinition parse(QueryBuildRequest request);
}