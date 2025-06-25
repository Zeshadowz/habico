package com.liberis.habico.app.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class EnumBaseConverter<T extends Enum<T> & BaseEnum<U>, U> implements AttributeConverter<T, U> {

    private final Class<T> enumClass;

    public EnumBaseConverter(final Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public T convertToEntityAttribute(U value) {
        return value == null ? null :
                Stream.of(enumClass.getEnumConstants())
                        .filter(e -> e.getValue().equals(value))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Invalid " + enumClass.getSimpleName() + ": " + value));
    }

    @Override
    public U convertToDatabaseColumn(T attribute) {
        return attribute == null ? null : attribute.getValue();
    }


}
