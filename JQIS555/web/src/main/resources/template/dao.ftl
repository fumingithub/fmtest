/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package ${packageInfo.packageName}.${packageInfo.moduleName}.dao;


import ${packageInfo.packageName}.${packageInfo.moduleName}.domain.${packageInfo.className};
import org.springframework.stereotype.Repository;
import org.zcframework.core.dao.entity.IBatisEntityDao;

/**
 * ${packageInfo.className}DAO
 * @author ${packageInfo.author}
 * @version ${packageInfo.version?default('1.0')}
 */
@Repository
public class ${packageInfo.className}Dao extends IBatisEntityDao<${packageInfo.className}> {
	
}
