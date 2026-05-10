package com.vmetrix.querymanager.query.comparator;

import org.springframework.stereotype.Component;

@Component
public class IsNotNullComparator
        extends AbstractSqlComparator {

    @Override
    public String getName() {
        return "isNotNull";
    }

    @Override
    public String build(
            String qualifiedColumn,
            String parameterName
    ) {

        return qualifiedColumn + " IS NOT NULL";
    }
}