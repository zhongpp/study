package com.baomidou.mybatisplus.samples.deluxe.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * 用户表
 * 设置逻辑删除字段,并且逻辑删除字段不 select 出来
 *
 * @author miemie
 * @since 2018-08-12
 */
@Data
@Accessors(chain = true)
@TableName(resultMap = "userResultMap")
public class User {
    private Long id;
    private String name;
    private Integer age;
    @TableField(el = "email, typeHandler=com.baomidou.mybatisplus.samples.deluxe.config.TestTypeHandler")
    private String email;

    @TableField(el = "tags, typeHandler=com.baomidou.mybatisplus.samples.deluxe.handler.ListJsonHandler")
    private List tags;

    @TableField(el = "attributes, typeHandler=com.baomidou.mybatisplus.samples.deluxe.handler.MapJsonHandler")
    private Map attributes;

    private String likes;

    @Version
    private Integer version;

    @TableLogic(value = "0", delval = "1")
    @TableField(select = false)
    private Integer deleted;
}
