<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE struts PUBLIC
        "-//Apache Software Foundation//DTD Struts Configuration 2.0//EN"
        "http://struts.apache.org/dtds/struts-2.0.dtd">
<struts>
    <package name="${packageInfo.moduleName?lower_case}" namespace="/${packageInfo.moduleName?lower_case}" extends="ncms-default">
        <action name="${packageInfo.className?lower_case}" class="${packageInfo.packageName}.${packageInfo.moduleName?lower_case}.view.${packageInfo.className?cap_first}Action">
            <result name="success" type="freemarker">/WEB-INF/pages/${packageInfo.moduleName?lower_case}/${packageInfo.className?lower_case}/${packageInfo.className?uncap_first}_list.html</result>
            <result name="input" type="freemarker">/WEB-INF/pages/${packageInfo.moduleName?lower_case}/${packageInfo.className?lower_case}/${packageInfo.className?uncap_first}_view.html</result>
        </action>
    </package>
</struts>
