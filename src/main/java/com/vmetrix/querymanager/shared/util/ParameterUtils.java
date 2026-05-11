package com.vmetrix.querymanager.shared.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public final class ParameterUtils {

    private ParameterUtils() {
    }

    public static String nextParameter(
            AtomicInteger counter
    ) {

        return "p" + counter.getAndIncrement();
    }

    public static Map<String, Object> singleton(
            String key,
            Object value
    ) {

        Map<String, Object> map =
                new LinkedHashMap<>();

        map.put(key, value);

        return map;
    }
}