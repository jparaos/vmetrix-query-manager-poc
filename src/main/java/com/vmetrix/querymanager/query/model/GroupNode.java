package com.vmetrix.querymanager.query.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupNode implements FilterNode {

    private String operator;

    private List<FilterNode> conditions;
}