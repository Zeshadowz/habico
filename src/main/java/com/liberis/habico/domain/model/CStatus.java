package com.liberis.habico.domain.model;

import com.liberis.habico.app.enums.BaseEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Status of the project.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CStatus implements BaseEnum<Character> {
    STARTED('S'),
    PENDING('P'),
    PROCESSING('C'),
    FINISHED('F');

    private final Character value;
}
