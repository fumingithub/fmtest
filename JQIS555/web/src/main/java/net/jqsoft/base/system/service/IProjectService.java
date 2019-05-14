/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.service;


import net.jqsoft.base.system.domain.EquipmentInfor;
import net.jqsoft.base.system.domain.Project;
import net.jqsoft.base.system.domain.User;

import org.zcframework.core.service.IBaseEntityService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * IProjectService
 * @author wangtao@jqsoft.net
 * @version 1.0
 */
public interface IProjectService extends IBaseEntityService<Project> {
	/**
     * 保存项目
     * @param entity 项目实体对象
     * @author wangtao
     * @createTime 2017-10-16
    */
    public int saveOrUpdateProject(Project entity);
    
    /**
	 * 根据项目名获得实体
	 * @author wangtao
	 * @date: 2017-10-16
	 */
    List<Project> getprojectNameAndUrl(String projectName,String platformAddr);
	/**
	 * 删除项目
	 * @author wangtao
	 * @date: 2017-10-25
	 */
	public void deleteProject(String id);
	/**
	 * chaxunshuju
	 * @author wangtao
	 * @date: 2017-10-25
	 */
	
	public List<Project> queryAllList(Map params);
	/**
	 * 
	 * @param project
	 * @return
	 */
	public int saveImpExlProject(List<Project> project);
	/**
     * 查询所有项目和服务器作为资源
     * @param 
     * @return
     */
    public List<Project> queryAll(String  loginRoleId);
    /**
     * 通过用户ID返回全部项目服务器资源，并在resources标注某个resourcess是否被某个用户拥有
     * @param loginRoleId 用户ID
     * @return
     */
    public List<Project> queryProAndServResourceByUserId(String  loginRoleId);
	/**
	 * 通过用户真实姓名查询项目ID
	 * @param selectRealName 用户真实姓名
	 * @return
	 */
	public List<Project> selectProjectIdByselectRealName(String  selectRealName);
	public List<Project> selectProjectIdByselectguardian(String  selectguardian);
    /**
     * 更新用户权限
     * @param role 用户ID
     * @param resourceId 资源ID
     */
    public void updateAuth(String selectUserId, String[] resourceId);

	public void saveGrantResource(String projectId,String[] selectUserId);
    

}
