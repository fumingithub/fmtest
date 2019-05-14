/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package ${packageInfo.packageName}.${packageInfo.moduleName}.view;

import ${packageInfo.packageName}.${packageInfo.moduleName}.domain.${packageInfo.className};
import ${packageInfo.packageName}.${packageInfo.moduleName}.service.I${packageInfo.className}Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.view.support.entity.BaseEntityAction;

/**
 * ${packageInfo.className}Action
 * @author ${packageInfo.author}
 * @version ${packageInfo.version?default('1.0')}
 */
public class ${packageInfo.className}Action extends BaseEntityAction<${packageInfo.className}, I${packageInfo.className}Service> {
    @Autowired
    private I${packageInfo.className}Service ${packageInfo.className?uncap_first}Service;

    @Override
    protected I${packageInfo.className}Service getEntityManager() {
        return ${packageInfo.className?uncap_first}Service;
    }




}
