/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.common.cache;

import net.jqsoft.base.system.domain.SysOrg;
import net.jqsoft.base.system.service.ISysOrgService;
import net.jqsoft.common.cache.service.impl.AbstractEhCacheManager;

import java.util.List;

/**
 * EhCache缓存类
 *
 * @author jinliang
 * @create 2016-12-21 下午 4:27
 **/
public class EhCacheManager extends AbstractEhCacheManager {

    /** 组织机构服务接口 */
    private ISysOrgService sysOrgService;

    @Override
    public void init() {
        synchronized (this) {
            List<SysOrg> sysOrgList = this.sysOrgService.getAll();
            put("AllSysOrg", sysOrgList);
        }
    }

    public void setSysOrgService(ISysOrgService sysOrgService) {
        this.sysOrgService = sysOrgService;
    }

}
