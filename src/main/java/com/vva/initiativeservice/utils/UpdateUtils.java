package com.vva.initiativeservice.utils;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class UpdateUtils {

    // Utility method to update fields in an object if they are non-null and different
    public static <T> void updateFieldIfChanged(Supplier<T> getter, Consumer<T> setter, T newValue) {
        if (newValue != null && !Objects.equals(getter.get(), newValue)) {
            setter.accept(newValue);
        }
    }

    // Is the organization ID a UUID
    public static boolean isValidOrganizationId(String organizationId) {
        try {
            UUID.fromString(organizationId);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
