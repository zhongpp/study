package com.zpp.springboot.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.format.datetime.joda.DateTimeFormatterFactory;

import java.io.IOException;


public class DateTimeSerializer extends JsonSerializer<DateTime> {
    @Override
    public void serialize(DateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        DateTimeFormatterFactory formatterFactory = new DateTimeFormatterFactory();
        formatterFactory.setPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        DateTimeFormatter formatter = formatterFactory.createDateTimeFormatter()
            .withZone(DateTimeZone.forID("Asia/Hong_Kong"));
        jsonGenerator.writeString(formatter.print(dateTime));
    }
}
