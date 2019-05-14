/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.view;

import net.jqsoft.base.system.domain.SysProPermission;
import net.jqsoft.base.system.service.ISysProPermissionService;


import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.view.support.entity.BaseEntityAction;

/**
 * SysProPermissionAction
 * @author wangtao@jqsoft.net
 * @version 1.0
 */
public class SysProPermissionAction extends BaseEntityAction<SysProPermission, ISysProPermissionService> {
    @Autowired
    private ISysProPermissionService sysProPermissionService;

    @Override
    protected ISysProPermissionService getEntityManager() {
        return sysProPermissionService;
    }




}
