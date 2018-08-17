package com.${company!''}.<#if module??>${module}.</#if>dao;
<#if containEnable>

import java.util.List;

import org.apache.ibatis.annotations.Param;
</#if>

import com.${company!''}.<#if module??>${module}.</#if>model.${model};
import com.smart.mvc.dao.mybatis.Dao;

public interface ${model}Dao extends Dao<${model}, Integer> {
<#if containEnable>

	public int enable(@Param("isEnable") Boolean isEnable, @Param("idList") List<Integer> idList);
</#if>
}
