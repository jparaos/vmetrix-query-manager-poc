package com.vmetrix.querymanager.query.comparator;

public interface ComparatorFactory {

    SqlComparator get(String comparatorName);
}