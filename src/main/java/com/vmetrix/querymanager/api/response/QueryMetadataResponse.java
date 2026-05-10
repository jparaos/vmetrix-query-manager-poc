package com.vmetrix.querymanager.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryMetadataResponse {

    private Integer columnCount;

    private Integer filterCount;

    private Instant generatedAt;
}