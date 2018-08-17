package com.${company!''}.<#if module??>${module}.</#if>model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;


${fieldList}

<#assign x = fieldList>
<#--使用seq_contains判断序列中的元素是否存在-->
${x?seq_contains("date")?string("import org.joda.time.Date;", "")}
${x?seq_contains("time")?string("import org.joda.time.Date;", "")}
${x?seq_contains("datetime")?string("import org.joda.time.Date;", "")}

import javax.persistence.*;

<#if tableComment??>
/**
 * ${tableComment}
 */
</#if>
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "${tableName}")
public class ${model} {

	<#list fieldList as field>
		<#if field.description??>
	/** ${field.description} */
	  	</#if>
	  	<#if field.fieldName == "uuid">
    @Id
    @GeneratedValue(generator = "IDGenerator")
    @GenericGenerator(name = "IDGenerator", strategy = "uuid")
	  	</#if>
	private ${field.fieldType} ${field.fieldName};
	</#list>

}
