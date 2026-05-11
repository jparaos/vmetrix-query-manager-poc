package com.vmetrix.querymanager.shared.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtils {

    public static boolean isBlank(
            String value
    ) {

        return value == null
                || value.isBlank();
    }

    public static boolean isNotBlank(
            String value
    ) {

        return !isBlank(value);
    }

    public static String safe(
            String value
    ) {

        return value == null
                ? ""
                : value.trim();
    }

    public static String upper(
            String value
    ) {

        return safe(value).toUpperCase();
    }

    public static String lower(
            String value
    ) {

        return safe(value).toLowerCase();
    }
}