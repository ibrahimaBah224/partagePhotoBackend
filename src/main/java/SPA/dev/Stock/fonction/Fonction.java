package SPA.dev.Stock.fonction;

import java.lang.reflect.Field;

public class Fonction {
    public static <T> T updateEntityWithNonNullFields(T entity, Object dto, String idName) {
        try {
            Field[] dtoFields = dto.getClass().getDeclaredFields();
            for (Field dtoField : dtoFields) {
                dtoField.setAccessible(true);
                Object value = dtoField.get(dto);

                // Ignore the id field
                if (dtoField.getName().equals(idName)) {
                    continue;
                }

                if (value != null) {
                    Field entityField;
                    try {
                        entityField = entity.getClass().getDeclaredField(dtoField.getName());
                    } catch (NoSuchFieldException e) {
                        // If the field doesn't exist in the entity, skip it
                        continue;
                    }
                    entityField.setAccessible(true);
                    entityField.set(entity, value);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to update entity with non-null fields", e);
        }
        return entity;
    }
}
