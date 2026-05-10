package com.vmetrix.querymanager.shared.constants;

public final class ApiPaths {

    private ApiPaths() {
    }

    public static final String API_BASE = "/api";

    public static final String QUERY = API_BASE + "/query";
    public static final String QUERY_BUILD = QUERY + "/build";
    public static final String QUERY_VALIDATE = QUERY + "/validate";

    public static final String METADATA = API_BASE + "/metadata";
    public static final String METADATA_ENTITIES = METADATA + "/entities";
    public static final String METADATA_COMPARATORS = METADATA + "/comparators";
}