package com.vmetrix.querymanager.query.comparator;

import org.springframework.stereotype.Component;

@Component
public class LikeComparator
        extends AbstractSqlComparator {

    @Override
    public String getName() {
        return "like";
    }

    @Override
    public String build(
            String qualifiedColumn,
            String parameterName
    ) {

        return wrap(
                qualifiedColumn,
                "LIKE",
                parameterName
        );
    }
}