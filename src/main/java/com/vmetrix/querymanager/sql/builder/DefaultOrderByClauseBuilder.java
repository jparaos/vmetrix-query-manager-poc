package com.vmetrix.querymanager.sql.builder;

import com.vmetrix.querymanager.metadata.model.ResolvedFieldMetadata;
import com.vmetrix.querymanager.query.model.QueryDefinition;
import com.vmetrix.querymanager.query.model.SortDefinition;
import com.vmetrix.querymanager.query.resolver.FieldResolver;
import com.vmetrix.querymanager.sql.model.SqlOrderByClause;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultOrderByClauseBuilder
        implements OrderByClauseBuilder {

    private final FieldResolver fieldResolver;

    @Override
    public SqlOrderByClause build(
            QueryDefinition queryDefinition
    ) {

        SqlOrderByClause clause =
                SqlOrderByClause.builder()
                        .build();

        if (queryDefinition.getSorting() == null) {
            return clause;
        }

        for (SortDefinition sort :
                queryDefinition.getSorting()) {

            ResolvedFieldMetadata resolved =
                    fieldResolver.resolve(
                            sort.getEntity(),
                            sort.getField()
                    );

            String field =
                    resolved.getTableAlias()
                            + "."
                            + resolved.getPhysicalColumn()
                            + " "
                            + sort.getDirection();

            clause.getFields().add(field);
        }

        return clause;
    }
}