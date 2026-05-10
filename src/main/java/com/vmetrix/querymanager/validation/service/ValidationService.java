package com.vmetrix.querymanager.validation.service;

import com.vmetrix.querymanager.query.model.QueryDefinition;
import com.vmetrix.querymanager.validation.model.ValidationResult;

public interface ValidationService {

    ValidationResult validate(QueryDefinition queryDefinition);
}