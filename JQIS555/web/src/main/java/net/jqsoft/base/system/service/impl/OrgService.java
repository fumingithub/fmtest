/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.base.system.service.impl;

import net.jqsoft.base.system.dao.OrgDao;
import net.jqsoft.base.system.domain.Org;
import net.jqsoft.base.system.service.IOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zcframework.core.dao.IEntityDao;
import org.zcframework.core.service.BaseEntityService;
import org.zcframework.core.utils.date.UtilDateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门管理Service接口实现类
 * @author xuhaigang
 * @createTime 2016-04-25 17:30
 */
@Service
public class OrgService extends BaseEntityService<Org> implements IOrgService {

    @Autowired
    private OrgDao orgDao;

    public List<Org> queryUnitList() {
        return this.orgDao.getAll();
    }

    @Override
    public Org getUnitByCode(String code, String id) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code", code);
        paramMap.put("id", id);
        return this.orgDao.findUniqueBy("Org.getUnitByCode", paramMap);
    }

    @Override
    public void deleteById(String insId, String updator) {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("id", insId);
        para.put("updator", updator);
        para.put("updateTime", UtilDateTime.getCurrDateInt());
        this.orgDao.update(getEntityName() + ".deleteById", para);
    }

    @Override
    public List<Org> getUnitByParentId(String parentId, String areaId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("parentId", parentId);
        param.put("areaId", areaId);
        return orgDao.find(getEntityName() + ".getUnitByParentId", param);
    }

    @Override
    public List<Org> getUnitByPid(String parentId) {
        return this.orgDao.findBy("Org.getUnitByPcode", "pid", parentId);
    }

    @Override
    protected IEntityDao<Org> getDao() {
        return orgDao;
    }
}
