package com.zpp.springboot.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class Body {

    private HashMap<String, Object> mapping;

    public HashMap<String, Object> getMapping() {
        return mapping;
    }

    public Body() {
        this.mapping = new HashMap<String, Object>();
    }

    public Object put(String key, Object value) {
        return this.mapping.put(key, value);
    }

    public Object get(String key) {
        return this.mapping.get(key);
    }

    private String getKeyByClass(Class cls) {
        String[] parts = cls.getName().split(Pattern.quote("."));
        String key = parts[parts.length - 1];
        if (key.substring(key.length() - 2, key.length()).equals("VO")) {
            key = key.substring(0, key.length() - 2);
        }
        return key.toLowerCase();
    }

    public <T> T getByClass(Class cls) {
        return (T) this.get(this.getKeyByClass(cls));
    }

    public <T> T putByClass(Class cls, T value) {
        return (T) this.put(this.getKeyByClass(cls), value);
    }

    public <T> T getByKey(String key) {
        return (T) this.get(key);
    }

    public <T> List<T> getList(String key) {
        Object ret = this.mapping.get(key);
        if (ret == null) {
            return new ArrayList<T>();
        }
        return (List<T>) ret;
    }

    public Body putError(String type, String reason, String message) {
        this.putByClass(Error.class, new Error(type, reason, message));
        return this;
    }

    @Override
    public String toString() {
        return mapping.toString();
    }

    public int getInt(String key) {
        Object value = this.get(key);
        return Integer.parseInt((String) value);
    }

    public boolean getBoolean(String key) {
        return (Boolean) this.get(key);
    }
}
