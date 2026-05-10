package com.vmetrix.querymanager.query.validator;

import com.vmetrix.querymanager.query.model.QueryDefinition;

import java.util.List;

public interface QueryValidator {

    List<String> validate(QueryDefinition queryDefinition);
}