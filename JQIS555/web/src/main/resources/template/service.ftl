/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package ${packageInfo.packageName}.${packageInfo.moduleName}.service.impl;

import ${packageInfo.packageName}.${packageInfo.moduleName}.domain.${packageInfo.className};
import ${packageInfo.packageName}.${packageInfo.moduleName}.dao.${packageInfo.className}Dao;
import ${packageInfo.packageName}.${packageInfo.moduleName}.service.I${packageInfo.className}Service;

import org.zcframework.core.service.BaseEntityService;
import org.zcframework.core.dao.IEntityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ${packageInfo.className}Service
 * @author ${packageInfo.author}
 * @version ${packageInfo.version?default('1.0')}
 */
@Service
public class ${packageInfo.className}Service extends BaseEntityService<${packageInfo.className}> implements I${packageInfo.className}Service{

	@Autowired
	private ${packageInfo.className}Dao ${packageInfo.className?uncap_first}Dao;

	@Override
    protected IEntityDao<${packageInfo.className}> getDao() {
        return ${packageInfo.className?uncap_first}Dao;
    }
	

	

	
}
