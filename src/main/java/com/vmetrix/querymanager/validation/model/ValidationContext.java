package com.vmetrix.querymanager.validation.model;

import com.vmetrix.querymanager.query.model.QueryDefinition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationContext {

    private QueryDefinition queryDefinition;

    private ValidationResult validationResult;
}