/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.service;


import net.jqsoft.base.system.domain.DatabaseInfoManagement;
import net.jqsoft.base.system.domain.EquipmentInfor;

import org.zcframework.core.service.IBaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * IDatabaseInfoManagementService
 * @author wangtao@jqsoft.net
 * @version 1.0
 */
public interface IDatabaseInfoManagementService extends IBaseEntityService<DatabaseInfoManagement> {
	/**
     * 保存数据库信息
     * @param entity 服务器实体对象
     * @author wangtao
     * @createTime 2017-10-18
    */
    public int saveOrUpdateDatabase(DatabaseInfoManagement entity);
    /**
	 * 删除数据库信息
	 * @author wangtao
	 * @date: 2017-10-25
	 */
	public void deleteDatabaseInfo(String id);
	
	/**
	 * 根据数据库用户名获得实体
	 * @author wangtao
	 * @date: 2017-10-25
	 */
	DatabaseInfoManagement getByUserName(String UserName,String EquipmentId);


}
