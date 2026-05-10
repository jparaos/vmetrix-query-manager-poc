package com.vmetrix.querymanager.query.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConditionNode implements FilterNode {

    private String entity;

    private String field;

    private String comparator;

    private Object value;
}