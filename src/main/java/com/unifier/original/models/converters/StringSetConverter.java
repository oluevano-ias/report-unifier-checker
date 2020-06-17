package com.unifier.original.models.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by bplies on 2/9/18.
 */
@Converter
public class StringSetConverter implements AttributeConverter<Set<String>,String> {
    @Override
    public String convertToDatabaseColumn(Set<String> attribute) {
        if (attribute == null) return null;
        String asString = String.join(",", attribute);
        return asString.replaceAll("\\s", ""); // Trim any whitespace from anywhere
    }

    @Override
    public Set<String> convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        HashSet<String> recipients = new HashSet<>(Arrays.asList(dbData.split(",")));
        return recipients;
    }
}
