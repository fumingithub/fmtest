/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.service.impl;

import net.jqsoft.base.system.domain.Project;
import net.jqsoft.base.system.domain.SysProPermission;
import net.jqsoft.base.system.dao.SysProPermissionDao;
import net.jqsoft.base.system.service.ISysProPermissionService;

import net.jqsoft.common.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.zcframework.core.service.BaseEntityService;
import org.zcframework.core.dao.IEntityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zcframework.core.utils.date.UtilDateTime;
import org.zcframework.security.SpringSecurityUtils;
import org.zcframework.security.object.Loginer;

/**
 * SysProPermissionService
 * @author wangtao@jqsoft.net
 * @version 1.0
 */
@Service
public class SysProPermissionService extends BaseEntityService<SysProPermission> implements ISysProPermissionService{

	@Autowired
	private SysProPermissionDao sysProPermissionDao;

	@Override
    protected IEntityDao<SysProPermission> getDao() {
        return sysProPermissionDao;
    }
    @Override
    public String saveSysproPermission(SysProPermission sysProPermission) {
            this.sysProPermissionDao.create(sysProPermission);
        return null;
    }

	

	
}
