package com.zpp.springboot.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

/**
 * @author pingpingZhong
 *         Date: 2017/6/5
 *         Time: 16:48
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserVO {
    private long id;

    private String name;

    private String status;

    private int age;

    @JsonProperty(value = "create_at")
    private DateTime createAt;
}
