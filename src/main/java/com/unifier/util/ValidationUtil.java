package com.unifier.util;

import io.vavr.collection.Seq;
import io.vavr.control.Validation;

public class ValidationUtil {

    public static final String OK = "ok";

    public static String getResultAsString(Validation<Seq<String>, Validation<Seq<String>, Boolean>> validation){
        return validation.fold(ValidationUtil::concatErrors,
                innerValidation -> innerValidation.fold(ValidationUtil::concatErrors, boolresult -> OK));
    }

    public static String getResultAsString2(Validation<Seq<String>, Boolean> validation){
        return validation.fold(ValidationUtil::concatErrors, boolresult -> OK);
    }

    private static String concatErrors(Seq<String> errors) {
        return errors.intersperse(", ").fold("", String::concat);
    }
}
