package com.vmetrix.querymanager.shared.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SqlUtils {

    public static String qualify(
            String alias,
            String column
    ) {

        return alias + "." + column;
    }

    public static String parameter(
            String name
    ) {

        return ":" + name;
    }

    public static String wrap(
            String value
    ) {

        return "(" + value + ")";
    }

    public static String comma() {

        return ", ";
    }

    public static boolean isEmpty(
            String value
    ) {

        return value == null
                || value.isBlank();
    }
}