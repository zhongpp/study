package com.zpp.springboot.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.format.datetime.joda.DateTimeFormatterFactory;

import java.io.IOException;


public class DateTimeDeserializer extends JsonDeserializer<DateTime> {
    @Override
    public DateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        DateTimeFormatterFactory formatterFactory = new DateTimeFormatterFactory();
        formatterFactory.setPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        DateTimeFormatter formatter = formatterFactory.createDateTimeFormatter()
            .withZone(DateTimeZone.forID("Asia/Hong_Kong"));
        DateTime dateTime = DateTime.parse(parser.getValueAsString(), formatter);
        return dateTime;
    }
}
