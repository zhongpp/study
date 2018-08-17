package com.zpp.util.smart.tool.generate;

import com.zpp.util.smart.tool.system.FreemarkerUtils;
import com.zpp.util.smart.tool.system.StringUtils;

import java.util.HashMap;
import java.util.Map;



/**
 * Dao
 * 
 * @author Joe
 */
public class Dao {

	private Map<String, Object> dataMap;

	public Dao(String company, String module, String model, boolean containEnable) {
		dataMap = new HashMap<String, Object>();
		/** 公司 **/
		dataMap.put("company", company);
		/** 模块 **/
		if (StringUtils.isNotBlank(module))
			dataMap.put("module", module);
		/** 模型 **/
		dataMap.put("model", model);
		/** 是否包含启用 **/
		dataMap.put("containEnable", containEnable);
	}
	
	public String getHtml(){
		return FreemarkerUtils.getText("dao.ftl", dataMap);
	}
}
