package com.vmetrix.querymanager.query.comparator;

import org.springframework.stereotype.Component;

@Component
public class LessOrEqualComparator
        extends AbstractSqlComparator {

    @Override
    public String getName() {
        return "lessOrEqual";
    }

    @Override
    public String build(
            String qualifiedColumn,
            String parameterName
    ) {

        return wrap(
                qualifiedColumn,
                "<=",
                parameterName
        );
    }
}
