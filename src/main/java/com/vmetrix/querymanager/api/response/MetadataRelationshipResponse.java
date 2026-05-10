package com.vmetrix.querymanager.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetadataRelationshipResponse {

    private String alias;

    private String targetEntity;

    private String joinType;

    private String sourceField;

    private String targetField;
}