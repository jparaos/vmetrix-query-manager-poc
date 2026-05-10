package com.vmetrix.querymanager.query.comparator;

import org.springframework.stereotype.Component;

@Component
public class NotEqualsComparator
        extends AbstractSqlComparator {

    @Override
    public String getName() {
        return "notEquals";
    }

    @Override
    public String build(
            String qualifiedColumn,
            String parameterName
    ) {

        return wrap(
                qualifiedColumn,
                "<>",
                parameterName
        );
    }
}