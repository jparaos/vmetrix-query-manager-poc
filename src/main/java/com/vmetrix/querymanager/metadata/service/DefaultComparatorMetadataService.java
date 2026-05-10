package com.vmetrix.querymanager.metadata.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DefaultComparatorMetadataService
        implements ComparatorMetadataService {

    private final MetadataRegistryService registryService;

    @Override
    public List<String> getComparatorsByType(String type) {

        return registryService.getRegistry()
                .getComparators()
                .getOrDefault(type, Collections.emptyList());
    }

    @Override
    public Map<String, List<String>> getAllComparators() {

        return registryService.getRegistry()
                .getComparators();
    }

    @Override
    public boolean isValid(
            String dataType,
            String comparator
    ) {

        return getComparatorsByType(dataType)
                .contains(comparator);
    }
}