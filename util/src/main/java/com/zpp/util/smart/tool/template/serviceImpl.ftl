package com.${company!''}.<#if module??>${module}.</#if>service.impl;
<#if containEnable>

import java.util.List;
</#if>

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.${company!''}.<#if module??>${module}.</#if>model.${model};
import com.${company!''}.<#if module??>${module}.</#if>dao.${model}Dao;
import com.${company!''}.<#if module??>${module}.</#if>service.${model}Service;
import com.smart.mvc.service.mybatis.impl.ServiceImpl;

@Component("${_model}Service")
public class ${model}ServiceImpl extends ServiceImpl<${model}Dao, ${model}, Integer> implements ${model}Service {

	@Autowired
	public void setDao(${model}Dao dao) {
		this.dao = dao;
	}
<#if containEnable>

	public void enable(Boolean isEnable, List<Integer> idList) {
		verifyRows(dao.enable(isEnable, idList), idList.size(), "管理员数据库更新失败");
	}
</#if>
}
