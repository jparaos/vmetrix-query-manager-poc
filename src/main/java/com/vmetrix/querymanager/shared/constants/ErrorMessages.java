package com.vmetrix.querymanager.shared.constants;

public final class ErrorMessages {

    private ErrorMessages() {
    }

    public static final String ENTITY_NOT_FOUND =
            "Entity '%s' does not exist in metadata";

    public static final String FIELD_NOT_FOUND =
            "Field '%s' does not exist in entity '%s'";

    public static final String INVALID_COMPARATOR =
            "Comparator '%s' is not valid for field type '%s'";

    public static final String INVALID_FILTER_VALUE =
            "Invalid value for field '%s'";

    public static final String RELATIONSHIP_NOT_FOUND =
            "Relationship '%s' could not be resolved";

    public static final String INVALID_SORT_DIRECTION =
            "Invalid sort direction '%s'";

    public static final String METADATA_LOAD_ERROR =
            "Error loading metadata configuration";
}