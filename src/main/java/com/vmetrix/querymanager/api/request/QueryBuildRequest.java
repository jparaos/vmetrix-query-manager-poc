package com.vmetrix.querymanager.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryBuildRequest {

    @Valid
    @NotEmpty
    private List<SelectFieldRequest> select;

    @Valid
    private FilterGroupRequest filter;

    @Valid
    private List<SortRequest> sorting;

    private Integer maxResults;
}