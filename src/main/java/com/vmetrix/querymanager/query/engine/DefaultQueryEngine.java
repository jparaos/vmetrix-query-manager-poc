package com.vmetrix.querymanager.query.engine;

import com.vmetrix.querymanager.api.request.QueryBuildRequest;
import com.vmetrix.querymanager.api.response.QueryBuildResponse;
import com.vmetrix.querymanager.api.response.ValidationResponse;
import com.vmetrix.querymanager.query.builder.QueryBuildFacade;
import com.vmetrix.querymanager.query.builder.ResolvedQueryMapper;
import com.vmetrix.querymanager.query.model.QueryDefinition;
import com.vmetrix.querymanager.query.model.ResolvedQuery;
import com.vmetrix.querymanager.query.parser.QueryDefinitionParser;
import com.vmetrix.querymanager.query.parser.QueryRequestNormalizer;
import com.vmetrix.querymanager.shared.exception.ValidationException;
import com.vmetrix.querymanager.validation.model.ValidationResult;
import com.vmetrix.querymanager.validation.service.ValidationResponseMapper;
import com.vmetrix.querymanager.validation.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultQueryEngine
        implements QueryEngine {

    private final QueryDefinitionParser
            queryDefinitionParser;

    private final QueryRequestNormalizer
            queryRequestNormalizer;

    private final ValidationService
            validationService;

    private final ValidationResponseMapper
            validationResponseMapper;

    private final QueryBuildFacade
            queryBuildFacade;

    private final ResolvedQueryMapper
            resolvedQueryMapper;

    @Override
    public QueryBuildResponse build(
            QueryBuildRequest request
    ) {

        QueryDefinition queryDefinition =
                parse(request);

        ValidationResult validationResult =
                validationService.validate(
                        queryDefinition
                );

        if (!validationResult.isValid()) {

            throw new ValidationException(
                    validationResult.getErrors()
                            .stream()
                            .map(error ->
                                    error.getMessage()
                            )
                            .toList()
            );
        }

        ResolvedQuery resolvedQuery =
                queryBuildFacade.build(
                        queryDefinition
                );

        return resolvedQueryMapper.map(
                resolvedQuery
        );
    }

    @Override
    public ValidationResponse validate(
            QueryBuildRequest request
    ) {

        QueryDefinition queryDefinition =
                parse(request);

        ValidationResult validationResult =
                validationService.validate(
                        queryDefinition
                );

        return validationResponseMapper.map(
                validationResult
        );
    }

    private QueryDefinition parse(
            QueryBuildRequest request
    ) {

        QueryDefinition queryDefinition =
                queryDefinitionParser.parse(
                        request
                );

        return queryRequestNormalizer.normalize(
                queryDefinition
        );
    }
}