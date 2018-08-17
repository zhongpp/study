package com.zpp.util.smart.tool.generate;



import com.zpp.util.smart.tool.system.DummyField;
import com.zpp.util.smart.tool.system.FreemarkerUtils;
import com.zpp.util.smart.tool.system.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model
 *
 * @author Joe
 */
public class Model {

    private Map<String, Object> dataMap;

    public Model(String company, String module, String model, List<DummyField> fieldList,
                 String tableName, boolean containEnable, boolean containDate,
                 String tableComment) {
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
        /** 是否包含Date **/
        dataMap.put("containDate", containDate);
        /** 字段list **/
        dataMap.put("fieldList", fieldList);
        /** 表名 **/
        dataMap.put("tableName", tableName);
        /** 表描述 **/
        dataMap.put("tableComment", tableComment);
    }

    public String getHtml() {
        return FreemarkerUtils.getText("model.ftl", dataMap);
    }
}
