/**
 * Copyright (c) 2013-2015 jqsoft.net
 */
package net.jqsoft.base.system.service;

import java.util.List;

import net.jqsoft.base.system.domain.Quick;
import net.jqsoft.base.system.domain.User;
import net.jqsoft.common.exception.ManagerException;

import org.zcframework.core.service.IBaseEntityService;

/**
 * 用户管理Service接口层
 * @author xuhaigang
 * @createTime 2015-06-04 12:13
 */
public interface IUserService extends IBaseEntityService<User> {
    
    /**
     * 查询角色下的用户
     * @param roleId 角色id
     * @return List<User> 用户List集合
     * @author xuhaigang
     * @createTime 2017-01-17 14:22:55
     */
    public List<User> queryUserByRoleId(String roleId);
    
    /**
     * 保存用户与角色的对应关系
     * @param userId  用户id
     * @param roleIds 角色id集合
     * @author xuhaigang
     * @createTime 2017-01-17 14:32:55
     */
    public void saveUserRole(String userId, String roleIds);

    /**
     * 根据用户名查询用户
     * @param userName 用户名
     * @return List<User> 用户List集合
     * @author xuhaigang
     * @createTime 2017-01-17 14:42:55
    */
    public List<User> getUserByUserName(String userName);
    
    /**
     * 根据机构id获取用户
     * @param insId 部门id
     * @return List<User> 用户List集合
     * @author xuhaigang
     * @createTime 2017-01-17 14:52:55
     */
    public List<User> queryUserByInsId(String insId);

    /**
     * 删除用户前先删除用户拥有的角色关系
     * @param userId 用户id
     * @author xuhaigang
     * @createTime 2017-01-17 15:02:55
    */
    public void deleteRolesByUserId(String userId);

    /**
     * 保存用户
     * @param entity 用户实体对象
     * @return int 成功标识 0-失败，非0-成功
     * @author xuhaigang
     * @createTime 2017-01-17 15:12:55
    */
    public int saveOrUpdateUser(User entity);
    
    /**
     * 根据用户名获得实体
     * @param userName 用户名
     * @return User 单个用户实体对象
     * @author xuhaigang
     * @createTime 2017-01-17 15:22:55
     */
    public User queryUserByUserName(String userName);

    /**
     * 　根据真实姓名获取用户Id
     * @param selectRealName
     * @return
     */
    public User getUserIdByRealName(String selectRealName);

    /**
     * 重置用户密码
     * @param  id       用户id
     * @param  passWord 用户密码
     * @throws ManagerException
     * @author     xiaohf
     * @createTime 2016年12月9日下午2:08:59
     */
    public void resetPassWord(String id, String passWord) throws ManagerException;

    /**
     * 根据id获得用户信息
     * @author lj
     * @createDate  2017-01-18
     * @param id
     * @return
     */
    public User getUserById(String id);
    /**
     * 根据id获得用户信息
     * @author lj
     * @createDate  2017-01-18
     * @param id
     * @return
     */
    public List<User> queryAuthrityUser();
    public List<User> queryAuthrityUserByUserName(String userName);
    List<User> querygetUserByUserNameAll();

}
