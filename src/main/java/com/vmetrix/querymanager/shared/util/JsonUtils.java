package com.vmetrix.querymanager.shared.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmetrix.querymanager.shared.exception.QueryManagerException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER =
            new ObjectMapper();

    public static <T> T fromJson(
            String json,
            Class<T> clazz
    ) {

        try {

            return OBJECT_MAPPER.readValue(
                    json,
                    clazz
            );

        } catch (JsonProcessingException exception) {

            throw new QueryManagerException(
                    "Failed to deserialize json",
                    exception
            );
        }
    }

    public static String toJson(
            Object object
    ) {

        try {

            return OBJECT_MAPPER.writeValueAsString(
                    object
            );

        } catch (JsonProcessingException exception) {

            throw new QueryManagerException(
                    "Failed to serialize object",
                    exception
            );
        }
    }
}