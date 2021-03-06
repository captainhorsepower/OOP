package com.netcracker.edu.varabey.inventory.web.validation.fragments;

import com.netcracker.edu.varabey.inventory.springutils.beanannotation.Validator;
import com.netcracker.edu.varabey.inventory.web.validation.exceptions.TagException;
import lombok.Getter;

@Getter
@Validator
public class TagNameValidator implements NameValidator {
    private final String forbiddenSymbols;
    private final Integer minNameLength;

    public TagNameValidator(String forbiddenSymbols, Integer minNameLength) {
        this.forbiddenSymbols = forbiddenSymbols;
        this.minNameLength = minNameLength;
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
