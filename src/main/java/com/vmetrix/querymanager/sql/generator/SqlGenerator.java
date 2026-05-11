package com.vmetrix.querymanager.sql.generator;

import com.vmetrix.querymanager.sql.model.SqlQuery;
import com.vmetrix.querymanager.sql.model.SqlStatement;

public interface SqlGenerator {

    SqlQuery generate(SqlStatement statement);
}