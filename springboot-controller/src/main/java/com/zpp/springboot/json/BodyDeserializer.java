package com.zpp.springboot.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.zpp.springboot.vo.UserVO;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class BodyDeserializer extends JsonDeserializer<Body> {

    private ObjectMapper mapper;

    private static final Map<String, TypeReference> mapping;
    static {
        Map<String, TypeReference> result = new HashMap<String, TypeReference>();
        result.put("user", new TypeReference<UserVO>() {});
        mapping = Collections.unmodifiableMap(result);
    }

    @Override
    public Body deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        mapper = getObjectMapper();

        String field;
        Body body = new Body();
        ObjectCodec oc = parser.getCodec();
        JsonNode node = oc.readTree(parser);
        Iterator<String> fields = node.fieldNames();

        while (fields.hasNext()) {
            field = fields.next();
            TypeReference cls = mapping.get(field);
            if (cls != null) {
                try {
                    Object value = mapper.readValue(node.get(field).toString(), cls);
                    body.put(field, value);
                } catch (JsonMappingException e) {
                    e.printStackTrace();
                }
            } else {
                throw new IOException("Body deserialize do not support:" + field);
            }
        }
        return body;
    }

    private ObjectMapper getObjectMapper() {
        if (mapper == null) {
            mapper = CustomObjectMapper.getObjectMapper();
        }

        return mapper;
    }
}
