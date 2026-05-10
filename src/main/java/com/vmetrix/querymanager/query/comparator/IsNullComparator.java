package com.vmetrix.querymanager.query.comparator;

import org.springframework.stereotype.Component;

@Component
public class IsNullComparator
        extends AbstractSqlComparator {

    @Override
    public String getName() {
        return "isNull";
    }

    @Override
    public String build(
            String qualifiedColumn,
            String parameterName
    ) {

        return qualifiedColumn + " IS NULL";
    }
}