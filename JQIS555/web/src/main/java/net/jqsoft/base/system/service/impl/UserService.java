/**
 * Copyright (c) 2013-2015 jqsoft.net
 */
package net.jqsoft.base.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jqsoft.base.system.domain.Quick;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zcframework.core.dao.IEntityDao;
import org.zcframework.core.service.BaseEntityService;
import org.zcframework.core.utils.date.UtilDateTime;
import org.zcframework.core.utils.security.MD5Utils;
import org.zcframework.security.SpringSecurityUtils;
import org.zcframework.security.object.Loginer;

import net.jqsoft.base.system.dao.UserDao;
import net.jqsoft.base.system.domain.User;
import net.jqsoft.base.system.service.IUserService;
import net.jqsoft.common.exception.ManagerException;
import net.jqsoft.common.util.CommonUtil;

/**
 * 用户管理Service接口实现类
 * @author xuhaigang
 * @createTime 2015-06-04 12:14
 */
@Service
public class UserService extends BaseEntityService<User> implements IUserService {

    @Autowired
    private UserDao userDao;

    @Override
    protected IEntityDao<User> getDao() {
        return userDao;
    }

    private List<Quick> userQuickList = new ArrayList<Quick>();

    @Override
    public List<User> queryUserByRoleId(String roleId) {
        return userDao.find(getEntityName() + ".queryUserByRoleId", roleId);
    }

    @Override
    public void saveUserRole(String userId, String roleIds) {
        // 删除用户与角色的对应关系
        userDao.update(getEntityName() + ".deleteUserRole", userId);
        if (StringUtils.isNotEmpty(roleIds)) {
            // 如果勾选了角色，则将这些角色分批插入SYS_USER_ROLE关联表
            Map<String, Object> para = null;
            String[] roleArray = roleIds.split(",");
            for (String roleId : roleArray) {
                para = new HashMap<String, Object>();
                para.put("USER_ID", userId);
                para.put("ROLE_ID", roleId);
                userDao.insert(getEntityName() + ".insertUserRole", para);
            }
        }
    }

    @Override
    public List<User> getUserByUserName(String userName) {
        return this.userDao.find("User.getUserByUserName", userName);
    }

    @Override
    public User queryUserByUserName(String userName) {
        return this.userDao.findUniqueBy("User.queryUserByUserName", "userName", userName);
    }
    @Override
    public User getUserIdByRealName(String selectRealName) {
        return this.userDao.findUniqueBy("User.getUserIdByRealName", "selectRealName", selectRealName);
    }

    @Override
    public List<User> queryUserByInsId(String insId) {
        return this.userDao.findBy("User.queryUserByInsId", "insId", insId);
    }

    @Override
    public void deleteRolesByUserId(String userId) {
        this.userDao.remove("User.deleteUserRole", userId);
    }

    @Override
    public int saveOrUpdateUser(User entity) {
        int result = 0;
        Loginer loginer = (Loginer) SpringSecurityUtils.getCurrentUser();
        entity.setUpdator(SpringSecurityUtils.getCurrentUserName());
        entity.setUpdateTime(UtilDateTime.getCurrDateInt());
        entity.setCreator(loginer.getId().toString());
        entity.setCreateTime(UtilDateTime.nowDate());
        entity.setCreatorName(loginer.getUsername());
        if (StringUtils.isNotEmpty(entity.getId())) {
            this.userDao.update(entity);
            result = 2;
        } else {
            entity.setId(CommonUtil.getUUID());
            this.userDao.create(entity);
            result = 1;
        }
        return result;
    }

    @Override
    public void resetPassWord(String id, String passWord) throws ManagerException {
        try {
            if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(passWord)) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("id", id);
                params.put("passWord", MD5Utils.md5(passWord));
                userDao.update("User.resetPassword", params);
            } else {
                throw new ManagerException("缺少必要的输入条件：id、password");
            }
        } catch (Exception e) {
            throw new ManagerException(e);
        }
    }

    /**
     * 根据id获得用户信息
     * @author lj
     * @createDate  2017-01-18
     * @param id
     * @return
     */
    @Override
    public  User getUserById(String id){
        Map<String,String> map =new HashMap();
        map.put("id",id);
        return this.userDao.get("User.getUserById", map);
    }
    /**
     * 获取已经授权的用户
     * @author wangtao
     * @createDate  2018-03-19
     * @param
     * @return
     */
    @Override
    public  List<User> queryAuthrityUser(){
    	return userDao.getAll(getEntityName() + ".queryAuthrityUser");
    }

    @Override
    public List<User> queryAuthrityUserByUserName(String userName){
        return userDao.get(getEntityName() + ".queryAuthrityUserByUserName","userName");
    }

    @Override
    public List<User> querygetUserByUserNameAll(){
        return userDao.getAll(getEntityName() + ".querygetUserByUserNameAll");
    }
}

