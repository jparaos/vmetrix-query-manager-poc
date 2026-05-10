package com.vmetrix.querymanager.validation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationRuleResult {

    @Builder.Default
    private List<ValidationError> errors = new ArrayList<>();

    public boolean hasErrors() {

        return !errors.isEmpty();
    }
}