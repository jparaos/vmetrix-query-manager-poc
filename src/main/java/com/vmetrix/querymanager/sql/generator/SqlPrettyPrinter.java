package com.vmetrix.querymanager.sql.generator;

import org.springframework.stereotype.Component;

@Component
public class SqlPrettyPrinter {

    public String format(
            String sql
    ) {

        return sql
                .replace("SELECT", "\nSELECT")
                .replace("FROM", "\nFROM")
                .replace("LEFT JOIN", "\nLEFT JOIN")
                .replace("INNER JOIN", "\nINNER JOIN")
                .replace("WHERE", "\nWHERE")
                .replace("ORDER BY", "\nORDER BY")
                .trim();
    }
}