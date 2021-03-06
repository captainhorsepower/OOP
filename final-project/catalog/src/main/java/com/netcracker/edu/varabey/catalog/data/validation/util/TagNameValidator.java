package com.netcracker.edu.varabey.catalog.data.validation.util;

import com.netcracker.edu.varabey.catalog.data.validation.exceptions.TagException;
import com.netcracker.edu.varabey.catalog.springutils.beanannotation.Validator;
import lombok.Getter;

@Getter
@Validator
public class TagNameValidator implements NameValidator {
    private final String forbiddenSymbols;
    private final Integer minNameLength;

    public TagNameValidator(String forbiddenSymbolsTag, Integer minNameLengthTag) {
        this.forbiddenSymbols = forbiddenSymbolsTag;
        this.minNameLength = minNameLengthTag;
    }

    @Override
    public void check(String name) {
        try {
            NameValidator.super.check(name);
        } catch (Exception e) {
            throw new TagException(e);
        }
    }
}
