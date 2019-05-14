/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.service;


import net.jqsoft.base.system.domain.Project;
import net.jqsoft.base.system.domain.SysProPermission;

import org.zcframework.core.service.IBaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ISysProPermissionService
 * @author wangtao@jqsoft.net
 * @version 1.0
 */
public interface ISysProPermissionService extends IBaseEntityService<SysProPermission> {

    /**
     * 项目授权
     * @param SysProPermission 项目授权实体对象
     * @author wangtao
     * @createTime 2018-01-24
     */
    public String saveSysproPermission(SysProPermission sysProPermission);

}
