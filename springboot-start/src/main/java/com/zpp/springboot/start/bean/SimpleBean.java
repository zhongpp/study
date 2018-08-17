package com.zpp.springboot.start.bean;

import lombok.*;
import org.springframework.stereotype.Component;

/**
 * Created by Format
 * on 2017/5/1.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class SimpleBean {

    private String id;
    private String name;

}
