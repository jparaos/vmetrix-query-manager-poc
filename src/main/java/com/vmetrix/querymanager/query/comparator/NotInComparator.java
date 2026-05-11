package com.vmetrix.querymanager.query.comparator;

import org.springframework.stereotype.Component;

@Component
public class NotInComparator
        extends AbstractSqlComparator {

    @Override
    public String getName() {
        return "notIn";
    }

    @Override
    public String build(
            String qualifiedColumn,
            String parameterName
    ) {

        return qualifiedColumn
                + " NOT IN (:"
                + parameterName
                + ")";
    }
}
