package com.vmetrix.querymanager.query.comparator;

public abstract class AbstractSqlComparator
        implements SqlComparator {

    protected String wrap(
            String qualifiedColumn,
            String operator,
            String parameterName
    ) {

        return qualifiedColumn
                + " "
                + operator
                + " :"
                + parameterName;
    }
}