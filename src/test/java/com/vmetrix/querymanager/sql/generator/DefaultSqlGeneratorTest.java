package com.vmetrix.querymanager.sql.generator;

import com.vmetrix.querymanager.sql.model.SqlFromClause;
import com.vmetrix.querymanager.sql.model.SqlOrderByClause;
import com.vmetrix.querymanager.sql.model.SqlSelectClause;
import com.vmetrix.querymanager.sql.model.SqlStatement;
import com.vmetrix.querymanager.sql.model.SqlWhereClause;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultSqlGeneratorTest {

    private DefaultSqlGenerator generator;

    @BeforeEach
    void setUp() {

        generator = new DefaultSqlGenerator();
    }

    @Test
    void shouldGenerateSql() {

        SqlStatement statement =
                SqlStatement.builder()
                        .selectClause(
                                SqlSelectClause.builder()
                                        .columns(
                                                List.of(
                                                        "t.AMOUNT"
                                                )
                                        )
                                        .build()
                        )
                        .fromClause(
                                SqlFromClause.builder()
                                        .table("TX_TRANSACTION")
                                        .alias("t")
                                        .build()
                        )
                        .whereClause(
                                SqlWhereClause.builder()
                                        .expression(
                                                "t.AMOUNT > :p1"
                                        )
                                        .build()
                        )
                        .orderByClause(
                                SqlOrderByClause.builder()
                                        .fields(
                                                List.of(
                                                        "t.AMOUNT DESC"
                                                )
                                        )
                                        .build()
                        )
                        .limit(10)
                        .parameters(
                                Map.of(
                                        "p1",
                                        1000
                                )
                        )
                        .build();

        String sql =
                generator.generate(statement)
                        .getSql();

        assertThat(sql)
                .contains("SELECT");

        assertThat(sql)
                .contains("FROM TX_TRANSACTION t");

        assertThat(sql)
                .contains("WHERE t.AMOUNT > :p1");

        assertThat(sql)
                .contains("ORDER BY");

        assertThat(sql)
                .contains("FETCH FIRST 10 ROWS ONLY");
    }
}