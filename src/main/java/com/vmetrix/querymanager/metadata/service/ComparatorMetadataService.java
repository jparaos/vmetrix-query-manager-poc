package com.vmetrix.querymanager.metadata.service;

import java.util.List;
import java.util.Map;

public interface ComparatorMetadataService {

    List<String> getComparatorsByType(String type);

    Map<String, List<String>> getAllComparators();

    boolean isValid(
            String dataType,
            String comparator
    );
}