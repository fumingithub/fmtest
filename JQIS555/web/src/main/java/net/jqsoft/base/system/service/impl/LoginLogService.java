/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.service.impl;

import net.jqsoft.base.system.domain.LoginLog;
import net.jqsoft.base.system.dao.LoginLogDao;
import net.jqsoft.base.system.service.ILoginLogService;

import net.jqsoft.common.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.zcframework.core.service.BaseEntityService;
import org.zcframework.core.dao.IEntityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * LoginLogService
 * @author xiaohf@jqsoft.net
 * @version 1.0
 */
@Service
public class LoginLogService extends BaseEntityService<LoginLog> implements ILoginLogService{

    @Autowired
    private LoginLogDao loginLogDao;

    @Override
    protected IEntityDao<LoginLog> getDao() {
        return loginLogDao;
    }

    @Override
    public int saveOrUpdate(LoginLog entity) {
        int flag = 0; // 成功标识 0：失败 1：插入成功 2：修改成功
        try {
            if (StringUtils.isNotEmpty(entity.getId())) {
                this.loginLogDao.update(entity);
                flag = 2;
            } else {
                entity.setId(CommonUtil.getUUID());
                this.loginLogDao.create(entity);
                flag = 1;
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        } finally {

        }
        return flag;
    }
}
