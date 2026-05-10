package com.vmetrix.querymanager.query.validator;

import com.vmetrix.querymanager.query.model.QueryDefinition;

import java.util.List;

public interface QueryValidationRule {

    List<String> validate(QueryDefinition queryDefinition);
}