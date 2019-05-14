/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.service.impl;

import net.jqsoft.base.system.domain.EquipmentInfor;
import net.jqsoft.base.system.domain.Project;
import net.jqsoft.base.system.dao.EquipmentInforDao;
import net.jqsoft.base.system.domain.SysProPermission;
import net.jqsoft.base.system.service.IEquipmentInforService;
import net.jqsoft.base.system.service.ISysProPermissionService;
import net.jqsoft.common.exception.ManagerException;
import net.jqsoft.common.util.CommonUtil;

import org.zcframework.core.service.BaseEntityService;
import org.zcframework.core.utils.date.UtilDateTime;
import org.zcframework.security.SpringSecurityUtils;
import org.zcframework.security.object.Loginer;
import org.zcframework.core.dao.IEntityDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * EquipmentInforService
 * @author wangtao@jqsoft.net
 * @version 1.0
 */
@Service
public class EquipmentInforService extends BaseEntityService<EquipmentInfor> implements IEquipmentInforService{

	@Autowired
	private EquipmentInforDao equipmentInforDao;
	@Autowired
	private ISysProPermissionService sysProPermissionService;
	@Override
    protected IEntityDao<EquipmentInfor> getDao() {
        return equipmentInforDao;
    }
	@Override
    public int saveOrUpdateEquipment(EquipmentInfor entity) {
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
            this.equipmentInforDao.update(entity);
            result = 2;
        } else {
			String[] userId=new String[2];
//        	entity.setProjectId(loginer.getUsername());
        	entity.setId(CommonUtil.getUUID());
        	entity.setIsDel("0");
            this.equipmentInforDao.create(entity);
			SysProPermission sysProPermission=new SysProPermission();
			String userIdd=loginer.getId().toString();
			if("1".equals(userIdd)){
				userId[0]="1";
			}else{
				userId[0]="1";
				userId[1]=userIdd;
			}
			for(String userIddd:userId) {
				sysProPermission.setUserId(userIddd);
				sysProPermission.setId(CommonUtil.getUUID());
				sysProPermission.setServerId(entity.getId());
				sysProPermissionService.saveSysproPermission(sysProPermission);
			}
            result = 1;
        }
        return result;
    }
	@Override
	public List<EquipmentInfor> getByServerIP(String ServerIp){
		Map<String, Object> param = new HashMap<String, Object>();
		//param.put("id", id);
    	param.put("serverIp", ServerIp);
    	//param.put("projectUrl", projectUrl);
		return this.equipmentInforDao.find("EquipmentInfor.getServerIp",param);
	}
	@Override
	public List<EquipmentInfor> getProjectIdById(String projectId){
		Map<String, Object> param = new HashMap<String, Object>();
		//param.put("id", id);
		param.put("projectId", projectId);
		//param.put("projectUrl", projectUrl);
		return this.equipmentInforDao.find("EquipmentInfor.getProjectId",param);
	}

	@Override
	public List<EquipmentInfor> getServerByServerName(String serverName){
		Map<String, Object> param = new HashMap<String, Object>();
		//param.put("id", id);
		param.put("serverName", serverName);
		//param.put("projectUrl", projectUrl);
		 return this.equipmentInforDao.find("EquipmentInfor.getServerByServerName",param);
	}

	@Override
	 public void deleteEquipInfo(String id) throws ManagerException {
			try {
				if (StringUtils.isNotBlank(id)) {
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", id);
					// params.put("whiteList","1");
					equipmentInforDao.update("EquipmentInfor.delEquipmentInfo", params);
				} else {
					throw new ManagerException("缺少必要的输入条件：id");
				}
			} catch (Exception e) {
				throw new ManagerException(e);
			}

		}
	@Override
	 public void deleteEquipInfoByProjId(String id) throws ManagerException {
			try {
				if (StringUtils.isNotBlank(id)) {
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", id);
					// params.put("whiteList","1");
					equipmentInforDao.update("EquipmentInfor.deleteEquipInfoByProjId", params);
				} else {
					throw new ManagerException("缺少必要的输入条件：id");
				}
			} catch (Exception e) {
				throw new ManagerException(e);
			}

		}
}
