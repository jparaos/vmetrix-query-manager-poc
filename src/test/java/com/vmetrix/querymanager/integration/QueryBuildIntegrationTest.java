package com.vmetrix.querymanager.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmetrix.querymanager.api.request.FilterConditionRequest;
import com.vmetrix.querymanager.api.request.FilterGroupRequest;
import com.vmetrix.querymanager.api.request.QueryBuildRequest;
import com.vmetrix.querymanager.api.request.SelectFieldRequest;
import com.vmetrix.querymanager.api.request.SortRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QueryBuildIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldBuildDynamicSqlQuery()
            throws Exception {

        QueryBuildRequest request =
                QueryBuildRequest.builder()
                        .select(
                                List.of(
                                        SelectFieldRequest.builder()
                                                .entity("transaction")
                                                .field("amount")
                                                .build(),
                                        SelectFieldRequest.builder()
                                                .entity("party")
                                                .field("partyName")
                                                .build()
                                )
                        )
                        .filters(
                                FilterGroupRequest.builder()
                                        .operator("AND")
                                        .conditions(
                                                List.of(
                                                        FilterConditionRequest.builder()
                                                                .entity("transaction")
                                                                .field("amount")
                                                                .comparator("greaterThan")
                                                                .value(1000)
                                                                .build(),
                                                        FilterConditionRequest.builder()
                                                                .entity("party")
                                                                .field("country")
                                                                .comparator("equals")
                                                                .value("CL")
                                                                .build()
                                                )
                                        )
                                        .build()
                        )
                        .sorting(
                                List.of(
                                        SortRequest.builder()
                                                .entity("transaction")
                                                .field("txnDate")
                                                .direction("DESC")
                                                .build()
                                )
                        )
                        .maxResults(10)
                        .build();

        mockMvc.perform(
                        post("/api/query/build")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content(
                                        objectMapper.writeValueAsString(
                                                request
                                        )
                                )
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.sql")
                                .exists()
                )
                .andExpect(
                        jsonPath("$.parameters.p1")
                                .value(1000)
                )
                .andExpect(
                        jsonPath("$.parameters.p2")
                                .value("CL")
                )
                .andExpect(
                        jsonPath("$.resolvedTables[0]")
                                .value("transaction")
                )
                .andExpect(
                        jsonPath("$.resolvedJoins[0]")
                                .value("party")
                );
    }
}