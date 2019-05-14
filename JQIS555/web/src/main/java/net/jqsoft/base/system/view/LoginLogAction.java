/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.view;

import net.jqsoft.base.system.domain.LoginLog;
import net.jqsoft.base.system.service.ILoginLogService;


import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.view.support.entity.BaseEntityAction;

/**
 * LoginLogAction
 * @author xiaohf@jqsoft.net
 * @version 1.0
 */
public class LoginLogAction extends BaseEntityAction<LoginLog, ILoginLogService> {
    @Autowired
    private ILoginLogService loginLogService;

    @Override
    protected ILoginLogService getEntityManager() {
        return loginLogService;
    }




}
