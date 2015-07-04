package com.unblocked.upsss.model;

import java.io.Serializable;

/**
 * ${entity.title} - Model
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (${now?string('yyyy-MM-dd HH:mm:ss')})
 */
public class Upsss${entity.type} implements Serializable {
    private static final long serialVersionUID = 1L;

	<#list entity.fields as field>
    private ${def["builder.Demo"].mysqlTypeToJavaType("${field.type}")} ${field.column?lower_case};
    </#list>
	<#list entity.fields as field>

    public ${def["builder.Demo"].mysqlTypeToJavaType("${field.type}")} get${field.column?lower_case?cap_first}() {
        return this.${field.column?lower_case};
    }

    public void set${field.column?lower_case?cap_first}(${def["builder.Demo"].mysqlTypeToJavaType("${field.type}")} ${field.column?lower_case}) {
        this.${field.column?lower_case} = ${field.column?lower_case};
    }
    </#list>
}