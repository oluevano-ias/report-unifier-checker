package com.unifier.migrated.models.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unifier.migrated.models.ReportProperties;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

/**
 * Converts ReportProperties to/from a JSON String to/from the database
 *
 * Created by bplies on 9/28/17.
 */
@Converter
public class ReportPropertiesConverter implements AttributeConverter<List<ReportProperties>, String> {
    @Override
    public String convertToDatabaseColumn(List<ReportProperties> attribute) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to convert to JSON", e);
        }
    }

    @Override
    public List<ReportProperties> convertToEntityAttribute(String dbData) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(dbData, new TypeReference<List<ReportProperties>>(){});
        } catch (IOException e) {
            throw new RuntimeException("Unable to convert from JSON", e);
        }
    }
}
