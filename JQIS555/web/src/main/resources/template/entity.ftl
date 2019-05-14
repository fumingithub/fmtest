/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package ${packageInfo.packageName}.${packageInfo.moduleName}.domain;

import org.zcframework.core.domain.support.Entity;
import org.apache.commons.lang3.StringUtils;

<#include "header.ftl"/>

public class ${packageInfo.className?cap_first} extends Entity<${packageInfo.idClass}> {
	
	<#list tableInfo.columns as column>
	
	/** ${column.description!} */
    private ${column.type}  ${column.name};
    </#list>
    //------------------GETTER & SETTER--------------------
    <#list tableInfo.columns as column>
    public ${column.type}  get${column.name?cap_first}(){
        return ${column.name};
    }
    public void set${column.name?cap_first}(${column.type} ${column.name}){
        this.${column.name}=${column.name};
    }
    </#list>
    
    /**
	 * 记录日志-新增
	 * @return
	 */
	public String newString() {
		StringBuffer sb = new StringBuffer("");
		<#list tableInfo.columns as column>
			sb.append("${column.description!}：").append(this.get${column.name?cap_first}());
		</#list>
		return sb.toString();
	}
	
	/**
	 * 记录日志-修改
	 * @param ${packageInfo.className} 原始未更改前
	 * @return
	 */
	public String compareDiff(${packageInfo.className?cap_first} ${packageInfo.className}){
		StringBuffer sb = new StringBuffer("");
		<#list tableInfo.columns as column>
			if(!StringUtils.equals(this.get${column.name?cap_first}(),${packageInfo.className}.get${column.name?cap_first}())) {
				sb.append("，").append("${column.description!}：").append(${packageInfo.className}.get${column.name?cap_first}()).append(" 修改为：").append(this.get${column.name?cap_first}());
			}
		</#list>
		return sb.toString();
	}
	
}


