package com.vmetrix.querymanager.query.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmetrix.querymanager.api.request.FilterConditionRequest;
import com.vmetrix.querymanager.api.request.FilterGroupRequest;
import com.vmetrix.querymanager.query.model.ConditionNode;
import com.vmetrix.querymanager.query.model.FilterNode;
import com.vmetrix.querymanager.query.model.GroupNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DefaultFilterParser
        implements FilterParser {

    private final ObjectMapper objectMapper;

    @Override
    public FilterNode parse(
            Object request
    ) {

        if (request == null) {
            return null;
        }

        return (FilterNode) parseCondition(request);
    }

    private GroupNode parseGroup(
            FilterGroupRequest request
    ) {

        List<FilterNode> nodes = new ArrayList<>();

        for (Object condition : request.getConditions()) {

            nodes.add(
                    parseCondition(condition)
            );
        }

        return GroupNode.builder()
                .operator(request.getOperator())
                .conditions(nodes)
                .build();
    }

    private FilterNode parseCondition(
            Object rawCondition
    ) {

        Map<String, Object> condition =
                objectMapper.convertValue(
                        rawCondition,
                        Map.class
                );

        if (condition.containsKey("conditions")) {

            FilterGroupRequest group =
                    objectMapper.convertValue(
                            rawCondition,
                            FilterGroupRequest.class
                    );

            return parseGroup(group);
        }

        FilterConditionRequest filterCondition =
                objectMapper.convertValue(
                        rawCondition,
                        FilterConditionRequest.class
                );

        return ConditionNode.builder()
                .entity(filterCondition.getEntity())
                .field(filterCondition.getField())
                .comparator(filterCondition.getComparator())
                .value(filterCondition.getValue())
                .build();
    }
}