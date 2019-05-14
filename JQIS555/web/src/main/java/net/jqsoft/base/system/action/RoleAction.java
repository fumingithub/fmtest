/**
 * Copyright (c) 2013-2015 jqsoft.net
 */
package net.jqsoft.base.system.action;

import net.jqsoft.base.system.domain.Role;
import net.jqsoft.base.system.domain.User;
import net.jqsoft.base.system.service.IRoleService;
import net.jqsoft.base.system.service.IUserService;
import net.jqsoft.common.action.MyEntityAction;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.view.support.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zuoc
 * @date: 2015-06-04 
 * @desc 角色管理Action
 */
public class RoleAction extends MyEntityAction<Role,IRoleService> {
    
    private static final long serialVersionUID = -7328965290424952183L;
    
    private List<User> userList = new ArrayList<User>();    // 用户集合
    
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserService userService;    
    private String parentId;
    private Role parentRole;
    /**
     * 是否存在下级，1-存在 0-不存在
     */
    private String isHaveChild;
    
    @Override
    public String list() {
        return super.list();
    }
    
    /**
     * 重写编辑和新增方法，获取父资源
     * 
     * @return
     */
    public String edit() {
        if (parentId != null)// 添加子项
            parentRole = roleService.get(parentId);
        else if(getEntityId() != null) {
            // 编辑
            Role prole = doGetEntity();
            if(StringUtils.isNotBlank(prole.getProleId())) {
                // 限定所属地区
                parentRole = roleService.get(prole.getProleId());
            }
            // 判断是否存在下级，存在下级，所属地区,编码不可修改
            List<Role> rlist = this.roleService.queryRoleByRoleId(getEntityId().toString());
            if(rlist.size()>0) {
                isHaveChild = "1";
            }
        }
        return super.edit();
    }
    
    /**
     * 用户查看页面
     * @return
     */
    public String viewusers() {
        userList = userService.queryUserByRoleId(entity.getId());
//        entity.setTotalCount(userList.size());
        totalCount = userList.size();
        return "users";
    }

    
    /**
     * 删除角色，与资源的关联关系
     * @return
     */
    public String deleterole() {
        try {
            roleService.deleteRole(entity.getId());
            result = new Result(true, "删除成功！");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return RESULT;
    }
    
    /**
     * 验证是否可以删除
     * add by liuj
     * @return
     */
    public String checkdel() {
        result = new Result(true, "可以删除");
        List<Role> rolelist = this.roleService.queryRoleByRoleId(entity.getId());
        if(rolelist.size()>0) {
            // 存在下级
            result = new Result(false, "所选角色存在子角色");
        }
        return RESULT;
    }
    
    @Override
    public boolean needGenerateUUID() {
        return true;
    }
    
    @Override
    protected IRoleService getEntityManager() {
        return roleService;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Role getParentRole() {
        return parentRole;
    }

    public void setParentRole(Role parentRole) {
        this.parentRole = parentRole;
    }

    public String getIsHaveChild() {
        return isHaveChild;
    }

    public void setIsHaveChild(String isHaveChild) {
        this.isHaveChild = isHaveChild;
    } 
    
}
