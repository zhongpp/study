package com.zpp.springboot;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author pingpingZhong
 *         Date: 2017/7/11
 *         Time: 14:45
 */
@AllArgsConstructor
@Data
public class Tag implements Serializable{
    private String name;

}