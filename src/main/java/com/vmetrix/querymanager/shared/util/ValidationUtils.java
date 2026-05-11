package com.vmetrix.querymanager.shared.util;

import com.vmetrix.querymanager.shared.exception.ValidationException;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class ValidationUtils {

    public static void throwIfErrors(
            List<String> errors
    ) {

        if (errors == null
                || errors.isEmpty()) {

            return;
        }

        throw new ValidationException(errors);
    }
}