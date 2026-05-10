package com.vmetrix.querymanager.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelectFieldRequest {

    @NotBlank
    private String entity;

    @NotBlank
    private String field;

    private String alias;
}