package com.vmetrix.querymanager.sql.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SqlSelectClause {

    @Builder.Default
    private List<String> columns =
            new ArrayList<>();
}