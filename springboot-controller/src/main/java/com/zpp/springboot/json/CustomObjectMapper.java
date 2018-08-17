package com.zpp.springboot.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;

import java.util.ArrayList;
import java.util.List;


public class CustomObjectMapper {

    public static ObjectMapper getObjectMapper() {
        Jackson2ObjectMapperFactoryBean bean = new Jackson2ObjectMapperFactoryBean();
        bean.setIndentOutput(true);
        bean.setTimeZone(DateTimeZone.forID("Asia/Hong_Kong").toTimeZone());
        bean.setSimpleDateFormat("");

        // Body serializer module
        BodySerializer bodySerializer = new BodySerializer();
        SimpleModule bodySerializerModule = new SimpleModule("BodySerializerModule", new Version(1, 0, 0, null, null, null));
        bodySerializerModule.addSerializer(Body.class, bodySerializer);

        // Body deserializer module
        BodyDeserializer bodyDeserializer = new BodyDeserializer();
        SimpleModule bodyDeserializerModule = new SimpleModule("BodyDeserializerModule", new Version(1, 0, 0, null, null, null));
        bodyDeserializerModule.addDeserializer(Body.class, bodyDeserializer);

        // DateTime deserializer module
        DateTimeSerializer dateTimeSerializer = new DateTimeSerializer();
        SimpleModule dateTimeSerializerModule = new SimpleModule("DateTimeSerializerModule", new Version(1, 0, 0, null, null, null));
        dateTimeSerializerModule.addSerializer(DateTime.class, dateTimeSerializer);

        // DateTime deserializer module
        DateTimeDeserializer dateTimeDeserializer = new DateTimeDeserializer();
        SimpleModule dateTimeDeserializerModule = new SimpleModule("DateTimeDeserializerModule", new Version(1, 0, 0, null, null, null));
        dateTimeDeserializerModule.addDeserializer(DateTime.class, dateTimeDeserializer);

        List<Module> modules = new ArrayList<Module>();
        modules.add(bodySerializerModule);
        modules.add(bodyDeserializerModule);
        modules.add(dateTimeSerializerModule);
        modules.add(dateTimeDeserializerModule);
        bean.setModules(modules);
        bean.afterPropertiesSet();

        return bean.getObject();
    }

}
