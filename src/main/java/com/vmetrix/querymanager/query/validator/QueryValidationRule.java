package com.vmetrix.querymanager.query.validator;

import com.vmetrix.querymanager.query.model.QueryDefinition;
import com.vmetrix.querymanager.validation.model.ValidationError;

import java.util.List;

public interface QueryValidationRule {

    List<ValidationError> validate(QueryDefinition queryDefinition);
}