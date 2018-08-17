package com.zpp.springboot;

/**
 * @author FuhaoHuang
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter(autoApply = true)
public class ListJsonConverter implements AttributeConverter<List, String> {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List entityValue) {
        if (entityValue == null) {
            return "[]";
        }

        try {
            return mapper.writeValueAsString(entityValue);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List convertToEntityAttribute(String dbValue) {
        if (dbValue == null) {
            return null;
        }

        try {
            return mapper.readValue(dbValue.getBytes("ISO-8859-1"), List.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
