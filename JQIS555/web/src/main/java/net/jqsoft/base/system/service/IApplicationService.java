/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.service;


import net.jqsoft.base.system.domain.Application;
import net.jqsoft.base.system.domain.AreaManagement;

import org.zcframework.core.service.IBaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * IApplicationService
 * @author wangtao@jqsoft.net
 * @version 1.0
 */
public interface IApplicationService extends IBaseEntityService<Application> { 
	/**
     * 保存应用信息
     * @param entity 应用实体对象
     * @author wangtao
     * @createTime 2017-10-19
    */
    public int saveOrUpdateApplication(Application entity);
/**
 * 删除应用信息
 * @author wangtao
 * @date: 2017-10-26
 */
public void deleteAppliationInfo(String id);


}
