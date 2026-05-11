package com.vmetrix.querymanager.sql.builder;

import com.vmetrix.querymanager.metadata.model.ResolvedFieldMetadata;
import com.vmetrix.querymanager.query.model.QueryDefinition;
import com.vmetrix.querymanager.query.model.SelectField;
import com.vmetrix.querymanager.query.resolver.FieldResolver;
import com.vmetrix.querymanager.sql.model.SqlSelectClause;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultSelectClauseBuilder
        implements SelectClauseBuilder {

    private final FieldResolver fieldResolver;

    @Override
    public SqlSelectClause build(
            QueryDefinition queryDefinition
    ) {

        SqlSelectClause clause =
                SqlSelectClause.builder()
                        .build();

        for (SelectField field :
                queryDefinition.getSelectFields()) {

            ResolvedFieldMetadata resolved =
                    fieldResolver.resolve(
                            field.getEntity(),
                            field.getField()
                    );

            String column =
                    resolved.getTableAlias()
                            + "."
                            + resolved.getPhysicalColumn();

            if (field.getAlias() != null
                    && !field.getAlias().isBlank()) {

                column += " AS " + field.getAlias();
            }

            clause.getColumns().add(column);
        }

        return clause;
    }
}