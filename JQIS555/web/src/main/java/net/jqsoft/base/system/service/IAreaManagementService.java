/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.service;


import net.jqsoft.base.system.domain.AreaManagement;
import net.jqsoft.base.system.domain.DatabaseInfoManagement;

import org.zcframework.core.service.IBaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * IAreaManagementService
 * @author wangtao@jqsoft.net
 * @version 1.0
 */
public interface IAreaManagementService extends IBaseEntityService<AreaManagement> {
	/**
     * 保存域信息
     * @param entity 域实体对象
     * @author wangtao
     * @createTime 2017-10-19
    */
    public int saveOrUpdateArea(AreaManagement entity);
    /**
	 * 删除域信息
	 * @author wangtao
	 * @date: 2017-10-25
	 */
	public void deleteDomainInfo(String id);


}
