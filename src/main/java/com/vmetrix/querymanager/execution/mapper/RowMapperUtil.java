package com.vmetrix.querymanager.execution.mapper;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class RowMapperUtil {

    public Map<String, Object> normalizeRow(
            Map<String, Object> row
    ) {

        Map<String, Object> normalized =
                new LinkedHashMap<>();

        row.forEach((key, value) -> {

            normalized.put(
                    key.toLowerCase(),
                    normalizeValue(value)
            );
        });

        return normalized;
    }

    private Object normalizeValue(
            Object value
    ) {

        if (value instanceof Timestamp timestamp) {

            return Instant.ofEpochMilli(
                    timestamp.getTime()
            );
        }

        return value;
    }
}