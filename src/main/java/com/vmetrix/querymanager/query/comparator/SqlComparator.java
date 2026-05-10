package com.vmetrix.querymanager.query.comparator;

public interface SqlComparator {

    String getName();

    String build(
            String qualifiedColumn,
            String parameterName
    );
}