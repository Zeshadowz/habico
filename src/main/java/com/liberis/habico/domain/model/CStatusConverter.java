package com.liberis.habico.domain.model;

import com.liberis.habico.app.enums.EnumBaseConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CStatusConverter extends EnumBaseConverter<CStatus, Character> {
    public CStatusConverter() {
        super(CStatus.class);
    }
}
