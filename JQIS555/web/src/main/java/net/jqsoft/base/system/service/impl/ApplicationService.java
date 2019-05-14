/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.service.impl;

import net.jqsoft.base.system.domain.Application;
import net.jqsoft.base.system.domain.AreaManagement;
import net.jqsoft.base.system.dao.ApplicationDao;
import net.jqsoft.base.system.service.IApplicationService;
import net.jqsoft.common.exception.ManagerException;
import net.jqsoft.common.util.CommonUtil;

import org.zcframework.core.service.BaseEntityService;
import org.zcframework.core.utils.date.UtilDateTime;
import org.zcframework.security.SpringSecurityUtils;
import org.zcframework.security.object.Loginer;
import org.zcframework.core.dao.IEntityDao;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ApplicationService
 * @author wangtao@jqsoft.net
 * @version 1.0
 */
@Service
public class ApplicationService extends BaseEntityService<Application> implements IApplicationService{

	@Autowired
	private ApplicationDao applicationDao;

	@Override
    protected IEntityDao<Application> getDao() {
        return applicationDao;
    }
	@Override
    public int saveOrUpdateApplication(Application entity) {
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
            this.applicationDao.update(entity);
            result = 2;
        } else {
//        	entity.setProjectId(loginer.getUsername());
        	entity.setId(CommonUtil.getUUID());
        	entity.setIsDel("0");
            this.applicationDao.create(entity);
            result = 1;
        }
        return result;
    }
	@Override
	 public void deleteAppliationInfo(String id) throws ManagerException {
			try {
				if (StringUtils.isNotBlank(id)) {
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", id);
					applicationDao.update("Application.delapplicationInfo", params);
				} else {
					throw new ManagerException("缺少必要的输入条件：id");
				}
			} catch (Exception e) {
				throw new ManagerException(e);
			}

		}

	

	
}
