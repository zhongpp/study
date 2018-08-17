package com.zpp.mybatis;

import lombok.Data;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;

/**
 * @author pingpingZhong
 *         Date: 2017/6/2
 *         Time: 13:52
 */

@Data
public class User {


    private String uuid;


    private String name;

    private UserSexEnum sex;

    private Integer age;

    private String status;

    private DateTime createdAt;

    /**
     * 大字段
     */
    private String description;

    private List Tags;

    private Map<String, String> attributes;


}


