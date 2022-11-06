package com.yangxcc.common.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;

public class ListValueConstraintValidator implements ConstraintValidator<ListValue, Integer> {
    private HashSet<Integer> hs = new HashSet<>();
    @Override
    public void initialize(ListValue constraintAnnotation) {
        for (int item : constraintAnnotation.value()) {
            hs.add(item);
        }
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return hs.contains(value);
    }
}
