package com.vmetrix.querymanager.query.comparator;

import org.springframework.stereotype.Component;

@Component
public class InComparator
        extends AbstractSqlComparator {

    @Override
    public String getName() {
        return "in";
    }

    @Override
    public String build(
            String qualifiedColumn,
            String parameterName
    ) {

        return qualifiedColumn
                + " IN (:"
                + parameterName
                + ")";
    }
}