package com.vmetrix.querymanager.sql.builder;

import com.vmetrix.querymanager.metadata.model.ResolvedFieldMetadata;
import com.vmetrix.querymanager.query.comparator.DefaultComparatorFactory;
import com.vmetrix.querymanager.query.model.ConditionNode;
import com.vmetrix.querymanager.query.model.QueryContext;
import com.vmetrix.querymanager.query.model.QueryDefinition;
import com.vmetrix.querymanager.query.resolver.FieldResolver;
import com.vmetrix.querymanager.sql.model.SqlWhereClause;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DefaultWhereClauseBuilderTest {

    private DefaultWhereClauseBuilder builder;

    private FieldResolver fieldResolver;

    @BeforeEach
    void setUp() {

        fieldResolver = mock(FieldResolver.class);

        when(
                fieldResolver.resolve(
                        "transaction",
                        "amount"
                )
        ).thenReturn(
                ResolvedFieldMetadata.builder()
                        .tableAlias("t")
                        .physicalColumn("AMOUNT")
                        .build()
        );

        builder = new DefaultWhereClauseBuilder(
                fieldResolver,
                new DefaultComparatorFactory(
                        List.of(
                                new com.vmetrix.querymanager.query.comparator.GreaterThanComparator()
                        )
                )
        );
    }

    @Test
    void shouldBuildWhereClause() {

        ConditionNode condition =
                ConditionNode.builder()
                        .entity("transaction")
                        .field("amount")
                        .comparator("greaterThan")
                        .value(1000)
                        .build();

        QueryDefinition definition =
                QueryDefinition.builder()
                        .filter(condition)
                        .build();

        QueryContext context =
                QueryContext.builder()
                        .build();

        SqlWhereClause clause =
                builder.build(
                        definition,
                        context
                );

        assertThat(clause).isNotNull();

        assertThat(
                clause.getExpression()
        ).contains("t.AMOUNT > :p1");

        assertThat(
                context.getParameters()
        ).containsEntry("p1", 1000);
    }
}