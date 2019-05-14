/**
 * Copyright (c) 2013-2015 jqsoft.net
 */
package net.jqsoft.base.system.service.impl;

import java.util.List;
import net.jqsoft.base.system.dao.RoleDao;
import net.jqsoft.base.system.domain.Role;
import net.jqsoft.base.system.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zcframework.core.dao.IEntityDao;
import org.zcframework.core.service.BaseEntityService;

/**
 * @author: jetyou@foxmail.com
 * @date: 2015/6/5 0005-15:46
 * @desc
 */
@Service
public class RoleService extends BaseEntityService<Role> implements IRoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    protected IEntityDao<Role> getDao() {
        return roleDao;
    }
	
	/**
	 * 删除角色
	 * @param role  role对象
	 */
	public void deleteRole(String id) {
		roleDao.remove(getEntityName() + ".deleteAuth", id);
		roleDao.remove(getEntityName() + ".deleteUserRole", id);
		roleDao.remove(getEntityName() + ".deleteById", id);
	}
	
	/**
	 * 根据用户ID查询角色
	 * @param userId  用户主键
	 */
	public List<Role> queryRoleByUserId(String userId) {
		return roleDao.find(getEntityName() + ".queryRoleByUserId", userId);
	}
	
	/**
	 * 查询协同办理单位角色
	 * @param itemId  用户主键
	 */
	public List<Role> queryUnitRole() {
		return roleDao.find(getEntityName() + ".queryUnitRole",(Object)null);
	}
	
	/**
	 * 根据救助项ID查询角色
	 * @param itemId  用户主键
	 */
	public List<Role> queryRoleByItemId(String itemId) {
		return roleDao.find(getEntityName() + ".queryRoleByItemId", itemId);
	}
	
	/**
	 * 根据用户名查询角色
	 * add by liuj 2015-8-19
	 * @param userName
	 */
	public List<Role> queryRoleByUserName(String userName) {
		return roleDao.find(getEntityName() + ".queryRoleByUserName", userName);
	}
	
	/**
	 * 根据角色id查询直接下级角色
	 * add by liuj
	 */
	public List<Role> queryRoleByRoleId(String roleId) {
		return roleDao.find(getEntityName() + ".queryRoleByRoleId", roleId);
	}
}
