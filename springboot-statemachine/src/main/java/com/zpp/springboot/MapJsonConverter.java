package com.zpp.springboot;

/**
 * @author FuhaoHuang
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

@Converter(autoApply = true)
public class MapJsonConverter implements AttributeConverter<Map, String> {

    private static final Logger logger = LoggerFactory.getLogger(MapJsonConverter.class);

    private ObjectMapper mapper = new ObjectMapper();

    private final Pattern MESSY_PATTERN = Pattern.compile("^.*\"\\?+\".*$", Pattern.DOTALL);

    @Override
    public String convertToDatabaseColumn(Map entityValue) {
        if (entityValue == null) {
            return "{}";
        }

        try {
            return mapper.writeValueAsString(entityValue);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map convertToEntityAttribute(String dbValue) {
        if (dbValue == null) {
            return null;
        }

        try {
            String raw = new String(dbValue.getBytes("ISO-8859-1"), "UTF-8");
            if (MESSY_PATTERN.matcher(raw).matches()) {
                raw = dbValue;
            }
            return mapper.readValue(raw, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
