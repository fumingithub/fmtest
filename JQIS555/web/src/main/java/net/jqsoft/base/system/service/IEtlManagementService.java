/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.service;


import net.jqsoft.base.system.domain.Application;
import net.jqsoft.base.system.domain.EtlManagement;

import org.zcframework.core.service.IBaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * IEtlManagementService
 * @author wangtao@jqsoft.net
 * @version 1.0
 */
public interface IEtlManagementService extends IBaseEntityService<EtlManagement> {
	/**
     * 保存etl信息
     * @param entity etl实体对象
     * @author wangtao
     * @createTime 2017-10-19
    */
    public int saveOrUpdateEtl(EtlManagement entity);


}
