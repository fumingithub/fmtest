/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.service;


import net.jqsoft.base.system.domain.LoginLog;

import org.zcframework.core.service.IBaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * ILoginLogService
 * @author xiaohf@jqsoft.net
 * @version 1.0
 */
public interface ILoginLogService extends IBaseEntityService<LoginLog> {

    /**
     * 保存/修改方法
     * @param entity 登录日志实体类
     * @return int 成功标识 0：失败 1：插入成功 2：修改成功
     * @author jinliang
     * @create 2016-12-06 10:02:32
     **/
    public int saveOrUpdate(LoginLog entity);
}
