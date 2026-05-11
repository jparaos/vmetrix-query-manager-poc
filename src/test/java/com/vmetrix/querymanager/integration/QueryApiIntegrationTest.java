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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class QueryApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldBuildDynamicSqlQuery()
            throws Exception {

        QueryBuildRequest request =
                buildCanonicalRequest();

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
                        jsonPath("$.sql")
                                .value(
                                        org.hamcrest.Matchers.containsString(
                                                "SELECT"
                                        )
                                )
                )
                .andExpect(
                        jsonPath("$.sql")
                                .value(
                                        org.hamcrest.Matchers.containsString(
                                                "LEFT JOIN PARTY"
                                        )
                                )
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
                )
                .andExpect(
                        jsonPath("$.metadata.columnCount")
                                .value(2)
                )
                .andExpect(
                        jsonPath("$.metadata.filterCount")
                                .value(2)
                );
    }

    @Test
    void shouldValidateQuerySuccessfully()
            throws Exception {

        QueryBuildRequest request =
                buildCanonicalRequest();

        mockMvc.perform(
                        post("/api/query/validate")
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
                        jsonPath("$.valid")
                                .value(true)
                )
                .andExpect(
                        jsonPath("$.errors")
                                .isEmpty()
                );
    }

    @Test
    void shouldReturnValidationErrorForInvalidField()
            throws Exception {

        QueryBuildRequest request =
                QueryBuildRequest.builder()
                        .select(
                                List.of(
                                        SelectFieldRequest.builder()
                                                .entity("party")
                                                .field("invalidField")
                                                .build()
                                )
                        )
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
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath("$.error")
                                .value("Validation Error")
                )
                .andExpect(
                        jsonPath("$.messages[0]")
                                .value(
                                        "Unknown field: invalidField in entity party"
                                )
                );
    }

    @Test
    void shouldExecuteDynamicQuery()
            throws Exception {

        QueryBuildRequest request =
                buildCanonicalRequest();

        mockMvc.perform(
                        post("/api/query/execute")
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
                        jsonPath("$.rowCount")
                                .exists()
                )
                .andExpect(
                        jsonPath("$.rows")
                                .isArray()
                );
    }

    private QueryBuildRequest buildCanonicalRequest() {

        return QueryBuildRequest.builder()
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
    }
}