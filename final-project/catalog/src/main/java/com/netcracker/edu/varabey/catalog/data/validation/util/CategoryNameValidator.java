package com.netcracker.edu.varabey.catalog.data.validation.util;

import com.netcracker.edu.varabey.catalog.springutils.beanannotation.Validator;
import com.netcracker.edu.varabey.catalog.data.validation.exceptions.CategoryException;
import com.netcracker.edu.varabey.catalog.data.validation.exceptions.InvalidNameException;
import lombok.Getter;

@Getter
@Validator
public class CategoryNameValidator implements NameValidator {
    private final String forbiddenSymbols;
    private final Integer minNameLength;

    public CategoryNameValidator(String forbiddenSymbolsCategory, Integer minNameLengthCategory) {
        this.forbiddenSymbols = forbiddenSymbolsCategory;
        this.minNameLength = minNameLengthCategory;
    }

    @Override
    public void check(String name) {
        try {
            NameValidator.super.check(name);
        } catch (InvalidNameException e) {
            throw new CategoryException(e);
        }
    }
}
