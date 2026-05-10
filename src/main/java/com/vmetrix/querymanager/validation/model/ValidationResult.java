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
public class ValidationResult {

    @Builder.Default
    private boolean valid = true;

    @Builder.Default
    private List<ValidationError> errors = new ArrayList<>();

    public void addError(ValidationError error) {

        this.valid = false;

        this.errors.add(error);
    }

    public void addErrors(List<ValidationError> errors) {

        if (errors == null || errors.isEmpty()) {
            return;
        }

        this.valid = false;

        this.errors.addAll(errors);
    }
}