/**
 * Copyright (c) 2013-2015 jqsoft.net
 */
package net.jqsoft.base.system.service;

import java.util.List;

import net.jqsoft.base.system.domain.Role;

import org.zcframework.core.service.IBaseEntityService;

/**
 * @author: zuoc
 * @date: 2015/6/4 0004-13:44
 * @desc 角色管理
 */
public interface IRoleService extends IBaseEntityService<Role> {
	
	/**
	 * 删除角色
	 * @param id  主键
	 */
	public void deleteRole(String id);
	
	/**
	 * 根据用户ID查询角色
	 * @param userId  用户主键
	 */
	public List<Role> queryRoleByUserId(String userId);	
	
	/**
	 * 根据救助项ID查询角色
	 * @param itemId  用户主键
	 */
	public List<Role> queryRoleByItemId(String itemId);	
	
	/**
	 * 查询协同办理单位角色
	 * @param itemId  用户主键
	 */
	public List<Role> queryUnitRole();	
	
	/**
	 * 根据用户名查询角色
	 * add by liuj 2015-8-19
	 * @param userName
	 */
	public List<Role> queryRoleByUserName(String userName);
	
	/**
	 * 根据角色id查询直接下级角色
	 * add by liuj
	 */
	public List<Role> queryRoleByRoleId(String roleId);
}
