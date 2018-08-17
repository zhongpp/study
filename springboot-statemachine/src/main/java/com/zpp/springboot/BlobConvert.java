package com.zpp.springboot;

import lombok.extern.java.Log;

import javax.persistence.AttributeConverter;
import java.io.UnsupportedEncodingException;


/**
 * @author pingpingZhong
 *         Date: 2017/7/11
 *         Time: 14:29
 */
@Log
public class BlobConvert implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return attribute;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null){
            return null;
        }
        String retValue = null;
        try {
            retValue = new String(dbData.getBytes("ISO-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.info("error");
        }
        return retValue;
    }
}
