/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.service.impl;

import net.jqsoft.base.system.domain.EquipmentInfor;
import net.jqsoft.base.system.domain.Project;
import net.jqsoft.base.system.domain.SysProPermission;
import net.jqsoft.base.system.domain.User;
import net.jqsoft.base.system.dao.ProjectDao;
import net.jqsoft.base.system.service.IProjectService;
import net.jqsoft.base.system.service.ISysProPermissionService;
import net.jqsoft.common.exception.ManagerException;
import net.jqsoft.common.util.CommonUtil;
import net.jqsoft.base.system.service.IEquipmentInforService;
import org.zcframework.core.service.BaseEntityService;
import org.zcframework.core.utils.date.UtilDateTime;
import org.zcframework.security.SpringSecurityUtils;
import org.zcframework.security.object.Loginer;

import com.google.common.collect.Maps;

import org.zcframework.core.dao.IEntityDao;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ProjectService
 * @author wangtao@jqsoft.net
 * @version 1.0
 */
@Service
public class ProjectService extends BaseEntityService<Project> implements IProjectService{

	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private ISysProPermissionService sysProPermissionService;
	@Autowired
	private IEquipmentInforService equipmentInforService;
	@Override
    protected IEntityDao<Project> getDao() {
        return projectDao;
    }
	 @Override
	    public int saveOrUpdateProject(Project entity) {
	        int result = 0;
	        Loginer loginer = (Loginer) SpringSecurityUtils.getCurrentUser();
	        entity.setUpdator(loginer.getId().toString());
	        entity.setUpdatorName(loginer.getUsername());
	        entity.setUpdateTime(UtilDateTime.nowDate());
	        entity.setCreator(loginer.getId().toString());            
	        entity.setCreateTime(UtilDateTime.nowDate());
	        entity.setCreateName(loginer.getUsername());
	        if (StringUtils.isNotEmpty(entity.getId())) {
	            this.projectDao.update(entity);
	            result = 2;
	        } else {
	        	entity.setId(CommonUtil.getUUID());
	        	entity.setIsDel("0");
	            this.projectDao.create(entity);
				SysProPermission sysProPermission=new SysProPermission();
				sysProPermission.setUserId(loginer.getId().toString());
				sysProPermission.setId(CommonUtil.getUUID());
				sysProPermission.setServerId(entity.getId());
				sysProPermissionService.saveSysproPermission(sysProPermission);
	            result = 1;
	        }
	        return result;
	    }
//	    public String deleteProject(String isDel) { 
//	        if (StringUtils.isNotEmpty(isDel) {
//	            this.projectDao.update(isDel);
//	            result = 2;
//	        } else {
//	        	entity.setId(CommonUtil.getUUID());
//	            this.projectDao.create(entity);
//	            result = 1;
//	        }
//	        return result;
//	    }
	 @Override
	 public void deleteProject(String id) throws ManagerException {
			try {
				if (StringUtils.isNotBlank(id)) {
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", id);
					// params.put("whiteList","1");
					projectDao.update("Project.delProject", params);
				} else {
					throw new ManagerException("缺少必要的输入条件：id");
				}
			} catch (Exception e) {
				throw new ManagerException(e);
			}

		}
	 @Override
	    public List<Project> queryAllList(Map params) {
	        return projectDao.find("Project.excelExport",params);
	    }
	     /**
	     * 查询所有项目服务器资源
	     * @param 
	     * @return
	     */
	    public List<Project> queryAll(String loginRoleId) {
	        if(StringUtils.isBlank(loginRoleId)){
	            return projectDao.findBy("Project.queryAll", "loginRoleId", null);
	        }
	        else {
	            Map<String,String> map = Maps.newHashMap();
	            map.put("loginRoleId",loginRoleId);
	            return projectDao.find("Project.queryAll",map);
	        }
	    }
	    /**
	     * 通过用户ID返回全部项目服务器资源，并在resources标注某个resourcess是否被某个用户拥有
	     * @param loginRoleId 用户ID
	     * @return
	     */
	    public List<Project> queryProAndServResourceByUserId(String loginRoleId) {
	        return projectDao.find("Project.queryProAndServResourceByUserId", loginRoleId);
	    }
	/**
	 * 通过用户真实姓名查询项目ID
	 * @param selectRealName 用户真实姓名
	 * @return
	 */
	public List<Project> selectProjectIdByselectRealName(String selectRealName) {
		Map<String,String> map = Maps.newHashMap();
		map.put("selectRealName",selectRealName);
		return projectDao.find("Project.selectProjectIdByselectRealName", map);
	}
	public List<Project> selectProjectIdByselectguardian(String selectguardian) {
		Map<String,String> map = Maps.newHashMap();
		map.put("selectguardian",selectguardian);
		return projectDao.find("Project.selectProjectIdByselectguardian", map);
	}
	 
	    /**
	     * 更新用户权限
	     * @param selectUserId 用户ID
	     * @param resourceId 资源ID数组
	     */
	    public void updateAuth(String selectUserId, String[] resourceId) {
	        //先删除ROLE的权限
	    	projectDao.remove("Project.deleteAuth",selectUserId);
	        //重新赋权
	        Map<String,String> parameters = null;
	        for (String r : resourceId) {
	        	String id=CommonUtil.getUUID();
	            parameters = new HashMap<String,String>();
	            parameters.put("selectUserId",selectUserId);
	            parameters.put("resourceId",r);
	            parameters.put("id",id);
	            projectDao.insert("Project.insertAuth", parameters);
	        }       
//	        LOGGER.info("更新角色{}的权限:{}",roleId, Arrays.toString(resourceId));
	    }

	/**
	 * 选择项目对用户进行授权
	 * @param selectUserId 用户ID
	 * @param projectId 项目ID
	 */
	public void saveGrantResource(String projectId, String[] selectUserId) {
		//先删除用户的权限
		String id = "";
		String idd = "";
		String newId = "";
		String ProjectIdAndSerId = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<EquipmentInfor> equipmentInfor = new ArrayList<EquipmentInfor>();
		equipmentInfor = equipmentInforService.getProjectIdById(projectId);
//		if (null != equipmentInfor) {
//			for (EquipmentInfor valueList : equipmentInfor) {
//				id += ",'" + valueList.getId().toString() + "'";
//			}
//				id = id.substring(1);
//
//			idd = id + ",'" + projectId + "'";
//			if(selectUserId.length>1) {
//				for (String ids : selectUserId) {
//					if (StringUtils.isNotBlank(ids)) {
//						//param.put("projectId", projectId);
//						param.put("selectUserId", ids);
//						param.put("idd", idd);
//						projectDao.remove("Project.deleteGrantAuth", param);
//					}
//				}
//			}
//			else{
//				param.put("idd", idd);
//				projectDao.remove("Project.deleteGrantAuth", param);
//			}
//		}
			param.put("idd", projectId);
			projectDao.remove("Project.deleteGrantAuth", param);

		//重新赋权
		if(!"".equals(selectUserId[0])){
		Map<String, String> parameters = null;
		ProjectIdAndSerId = projectId;
			for (String r : selectUserId) {
				String iddd = CommonUtil.getUUID();
				parameters = new HashMap<String, String>();
				parameters.put("projectId", ProjectIdAndSerId);
				parameters.put("selectUserId", r);
				parameters.put("id", iddd);
				projectDao.insert("Project.insertGrantAuth", parameters);
			}
	}
//	        LOGGER.info("更新角色{}的权限:{}",roleId, Arrays.toString(resourceId));
	}
//	 /**
//	     * @return 
//	     * @author wangtao
//	     * @create 2017-10-17
//	     */
//		@Override
//		public Project getprojectNameAndUrl(String projectName){
//			Map<String, Object> param = new HashMap<String, Object>();
//	    	param.put("projectName", projectName);
//			return this.projectDao.findUniqueBy("Project.getProjectNameAndProjectUrl",param);
//		}
		/**
		 * 根据项目名获得实体
		 * @author wangtao
		 * @param projectName
		 * @return
		 */
		@Override
		public List<Project> getprojectNameAndUrl(String projectName,String platformAddr) {
			Map<String, String> map = new HashMap<String, String>();
			
			map.put("projectName", projectName);
			map.put("platformAddr", platformAddr);
//			return this.projectDao.findUniqueBy("Project.getProjectName", map);
			return this.projectDao.find("Project.getProjectName", map);
		}
		@Override
		public int saveImpExlProject(List<Project> project) throws ManagerException {
			try {
				for (Project pe : project) {
//					pe.createEntity();
					Loginer loginer = (Loginer) SpringSecurityUtils.getCurrentUser();
					pe.setId(CommonUtil.getUUID());
			        pe.setUpdator(loginer.getId().toString());
			        pe.setUpdatorName(loginer.getUsername());
			        pe.setUpdateTime(UtilDateTime.nowDate());
			        pe.setCreator(loginer.getId().toString());            
			        pe.setCreateTime(UtilDateTime.nowDate());
			        pe.setCreateName(loginer.getUsername());
			        pe.setIsDel("0");
					this.projectDao.create(pe);
				}

			} catch (Exception e) {
				throw new ManagerException("数据导入异常");
			}
			return 1;
		}
		
}
