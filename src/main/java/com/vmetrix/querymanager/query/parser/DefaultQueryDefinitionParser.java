package com.vmetrix.querymanager.query.parser;

import com.vmetrix.querymanager.api.request.QueryBuildRequest;
import com.vmetrix.querymanager.api.request.SelectFieldRequest;
import com.vmetrix.querymanager.api.request.SortRequest;
import com.vmetrix.querymanager.query.model.QueryDefinition;
import com.vmetrix.querymanager.query.model.SelectField;
import com.vmetrix.querymanager.query.model.SortDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultQueryDefinitionParser
        implements QueryDefinitionParser {

    private final FilterParser filterParser;

    @Override
    public QueryDefinition parse(
            QueryBuildRequest request
    ) {

        return QueryDefinition.builder()
                .selectFields(
                        mapSelectFields(request)
                )
                .filter(
                        filterParser.parse(
                                request.getFilter()
                        )
                )
                .sorting(
                        mapSorting(request)
                )
                .maxResults(
                        request.getMaxResults()
                )
                .build();
    }

    private List<SelectField> mapSelectFields(
            QueryBuildRequest request
    ) {

        return request.getSelect()
                .stream()
                .map(this::mapSelectField)
                .toList();
    }

    private SelectField mapSelectField(
            SelectFieldRequest request
    ) {

        return SelectField.builder()
                .entity(request.getEntity())
                .field(request.getField())
                .alias(request.getAlias())
                .build();
    }

    private List<SortDefinition> mapSorting(
            QueryBuildRequest request
    ) {

        if (request.getSorting() == null) {
            return Collections.emptyList();
        }

        return request.getSorting()
                .stream()
                .map(this::mapSort)
                .toList();
    }

    private SortDefinition mapSort(
            SortRequest request
    ) {

        return SortDefinition.builder()
                .entity(request.getEntity())
                .field(request.getField())
                .direction(request.getDirection())
                .build();
    }
}