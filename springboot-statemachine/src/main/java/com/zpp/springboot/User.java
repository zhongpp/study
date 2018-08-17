package com.zpp.springboot;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * @author pingpingZhong
 *         Date: 2017/6/2
 *         Time: 13:52
 */

@Data
@Entity
@Inheritance
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "IDGenerator")
    @GenericGenerator(name = "IDGenerator", strategy = "uuid")
    private String uuid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer age;

    private String status;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = DateTimeSerializer.class)
    @Column(name = "created_at")
    private DateTime createdAt;

    /**
     * 大字段
     */
    @Convert(converter = BlobConvert.class)
    private String description;

    private Map<String, String> attributes;

    private List<Tag> tags;

}

