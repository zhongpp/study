package com.${company!''}.<#if module??>${module}.</#if>service;
<#if containEnable>

import java.util.List;
</#if>

import com.${company!''}.<#if module??>${module}.</#if>model.${model};
import com.smart.mvc.service.mybatis.Service;

public interface ${model}Service extends Service<${model}, Integer> {
<#if containEnable>

	/**
	 * 启用禁用操作
	 * @param isEnable 是否启用
	 * @param idList 管理员ID集合
	 * @return
	 */
	public void enable(Boolean isEnable, List<Integer> idList);
</#if>
}