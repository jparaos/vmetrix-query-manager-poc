package com.vmetrix.querymanager.query.comparator;

import org.springframework.stereotype.Component;

@Component
public class BetweenComparator
        extends AbstractSqlComparator {

    @Override
    public String getName() {
        return "between";
    }

    @Override
    public String build(
            String qualifiedColumn,
            String parameterName
    ) {

        return qualifiedColumn
                + " BETWEEN :"
                + parameterName
                + "_start AND :"
                + parameterName
                + "_end";
    }
}