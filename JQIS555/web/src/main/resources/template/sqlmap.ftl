<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE sqlMap
        PUBLIC "-//ibatis.apache.org//DTD SQL Map 2.0//EN"
        "http://ibatis.apache.org/dtd/sql-map-2.dtd">
<sqlMap namespace="${packageInfo.className?cap_first}">
    <typeAlias alias="${packageInfo.className?cap_first}" type="${packageInfo.packageName}.${packageInfo.moduleName}.domain.${packageInfo.className?cap_first}" />

    <resultMap id="${packageInfo.className?cap_first}Map" class="${packageInfo.className?cap_first}">

          <result property="id" column="id"/>
         <#list tableInfo.columns as column>
                <result property="${column.name}" column="${column.columnName}"/>
          </#list>

    </resultMap>

    <select id="getAll" resultMap="${packageInfo.className?cap_first}Map">
        SELECT * FROM ${tableInfo.table.tableName}
    </select>
    <select id="get" parameterClass="${packageInfo.idClass}" resultMap="${packageInfo.className?cap_first}Map">
        <![CDATA[
           	   SELECT * FROM ${tableInfo.table.tableName} WHERE  id=#id:${packageInfo.idClass}#
         ]]>
    </select>
    <insert id="insert" parameterClass="${packageInfo.className?cap_first}">
        <![CDATA[
        INSERT INTO ${tableInfo.table.tableName}(
        <#list tableInfo.columns as column>
        <#if !column_has_next>${column.columnName}<#else>${column.columnName},</#if>
        </#list>
        ) values(
            <#list tableInfo.columns as column>
                <#if !column_has_next>#${column.name}#<#else>#${column.name}#,</#if>
            </#list>
             )
		]]>
    </insert>
    <update id="update" parameterClass="${packageInfo.className?cap_first}">
        <![CDATA[
			UPDATE ${tableInfo.table.tableName} ]]>
			<dynamic prepend="SET">
			  <#list tableInfo.columns as column>

            <isNotEmpty  property="${column.name}" prepend=",">
                <![CDATA[
					${column.columnName} = #${column.name}:${column.type}#
				]]>
            </isNotEmpty >
            </#list>
        </dynamic>
        <dynamic prepend="WHERE">
            <![CDATA[
					id = #id:int#
				]]>
        </dynamic>
    </update>
    <delete id="deleteById" parameterClass="String">
        <![CDATA[
        DELETE FROM demo where id = #id:${packageInfo.idClass}#
     ]]>
    </delete>
    <select id="count" resultClass="int">
        SELECT COUNT(*) FROM ${tableInfo.table.tableName}
    </select>
    <select id="select" resultMap="${packageInfo.className?cap_first}Map"  parameterClass="java.util.HashMap">
        SELECT * FROM ${tableInfo.table.tableName}
    </select>

</sqlMap>