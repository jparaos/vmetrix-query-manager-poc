package com.vmetrix.querymanager.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmetrix.querymanager.api.request.QueryBuildRequest;
import com.vmetrix.querymanager.api.request.SelectFieldRequest;
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
class QueryValidationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnValidationErrorForUnknownField()
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
                                        "Field 'invalidField' does not exist in entity 'party'"
                                )
                );
    }
}