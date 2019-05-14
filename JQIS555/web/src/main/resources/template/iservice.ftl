/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package ${packageInfo.packageName}.${packageInfo.moduleName}.service;


import ${packageInfo.packageName}.${packageInfo.moduleName}.domain.${packageInfo.className?cap_first};

import org.zcframework.core.service.IBaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * I${packageInfo.className?cap_first}Service
 * @author ${packageInfo.author}
 * @version ${packageInfo.version?default('1.0')}
 */
public interface I${packageInfo.className?cap_first}Service extends IBaseEntityService<${packageInfo.className?cap_first}> {


}
