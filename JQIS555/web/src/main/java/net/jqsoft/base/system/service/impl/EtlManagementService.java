/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.service.impl;

import net.jqsoft.base.system.domain.Application;
import net.jqsoft.base.system.domain.EtlManagement;
import net.jqsoft.base.system.dao.EtlManagementDao;
import net.jqsoft.base.system.service.IEtlManagementService;
import net.jqsoft.common.util.CommonUtil;

import org.zcframework.core.service.BaseEntityService;
import org.zcframework.core.utils.date.UtilDateTime;
import org.zcframework.security.SpringSecurityUtils;
import org.zcframework.security.object.Loginer;
import org.zcframework.core.dao.IEntityDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * EtlManagementService
 * @author wangtao@jqsoft.net
 * @version 1.0
 */
@Service
public class EtlManagementService extends BaseEntityService<EtlManagement> implements IEtlManagementService{

	@Autowired
	private EtlManagementDao etlManagementDao;

	@Override
    protected IEntityDao<EtlManagement> getDao() {
        return etlManagementDao;
    }
	
	@Override
    public int saveOrUpdateEtl(EtlManagement entity) {
        int result = 0;
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("projectId", entity.getProjectId());
        Loginer loginer = (Loginer) SpringSecurityUtils.getCurrentUser();
        entity.setUpdator(SpringSecurityUtils.getCurrentUserName());
        entity.setUpdateTime(UtilDateTime.nowDate());
        entity.setCreator(loginer.getId().toString());            
        entity.setCreateTime(UtilDateTime.nowDate());
        entity.setCreateName(loginer.getUsername());
      
        if (StringUtils.isNotEmpty(entity.getId())) {
            this.etlManagementDao.update(entity);
            result = 2;
        } else {
//        	entity.setProjectId(loginer.getUsername());
        	entity.setId(CommonUtil.getUUID());
            this.etlManagementDao.create(entity);
            result = 1;
        }
        return result;
    }

	

	
}
