package com.vmetrix.querymanager.sql.builder;

import com.vmetrix.querymanager.metadata.model.RelationshipMetadata;
import com.vmetrix.querymanager.metadata.model.ResolvedRelationship;
import com.vmetrix.querymanager.query.model.QueryContext;
import com.vmetrix.querymanager.query.resolver.RelationshipResolver;
import com.vmetrix.querymanager.sql.model.SqlJoinClause;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultJoinClauseBuilder
        implements JoinClauseBuilder {

    private final RelationshipResolver relationshipResolver;

    @Override
    public List<SqlJoinClause> build(
            QueryContext context
    ) {

        List<SqlJoinClause> joins =
                new ArrayList<>();

        for (RelationshipMetadata relationship :
                context.getResolvedRelationships()) {

            String sqlTargetAlias = context.getRelationshipSqlAliases()
                    .getOrDefault(relationship.getAlias(), relationship.getAlias());

            ResolvedRelationship resolved =
                    relationshipResolver.resolve(
                            relationship.getAlias(),
                            sqlTargetAlias
                    );

            String condition =
                    resolved.getSourceAlias()
                            + "."
                            + resolved.getSourceColumn()
                            + " = "
                            + resolved.getTargetAlias()
                            + "."
                            + resolved.getTargetColumn();

            joins.add(
                    SqlJoinClause.builder()
                            .joinType(resolved.getJoinType())
                            .table(resolved.getTargetTable())
                            .alias(resolved.getTargetAlias())
                            .condition(condition)
                            .build()
            );
        }

        return joins;
    }
}