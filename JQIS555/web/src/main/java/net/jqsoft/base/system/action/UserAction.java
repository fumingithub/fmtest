/**
 * Copyright (c) 2013-2015 jqsoft.net
 */
package net.jqsoft.base.system.action;

import net.jqsoft.base.system.domain.Quick;
import net.jqsoft.base.system.domain.Role;
import net.jqsoft.base.system.domain.SysOrg;
import net.jqsoft.base.system.domain.User;
import net.jqsoft.base.system.service.IRoleService;
import net.jqsoft.base.system.service.ISysOrgService;
import net.jqsoft.base.system.service.IUserService;
import net.jqsoft.common.action.MyEntityAction;
import net.jqsoft.common.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.utils.security.MD5Utils;
import org.zcframework.core.view.support.Result;
import org.zcframework.security.SpringSecurityUtils;
import org.zcframework.security.cache.ICacheRefreshService;
import org.zcframework.security.object.Loginer;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户管理Action
 * @author xuhaigang
 * @createTime 2015-06-04 12:13
 */
public class UserAction extends MyEntityAction<User, IUserService> {

    private static final long serialVersionUID = -2865040630611707684L;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ISysOrgService sysOrgService;

    @Autowired
    private ICacheRefreshService cacheRefreshService;

    /** 角色集合 */
    private List<Role> roleList = new ArrayList<Role>();

    /** 组织机构List集合 */
    private List<String> newOrgList;

    /** 角色ID集合 */
    private String roleIds;

    /** 是否是管理员标识 f-否，t-是 */
    private String admin;

    /** 用户单位 */
    private String unitIds;

    /** 组织机构ID */
    private String insId;

    /** zTree树-组织机构编码 */
    private String ztSysOrgId;

    /** 所属机构 */
    private SysOrg parent;
    private String q;// 搜索条件
    private String code;// 回显使用 value的值
    private List<Quick> quicks = new ArrayList<Quick>();

    @Override
    protected IUserService getEntityManager() {
        return userService;
    }

    /**
     * 进入列表页
     * @return String 跳转页面字符串
     * @author xuhaigang
     * @createTime 2017-01-18 09:12:58
     */
    public String list() {
        Map<String, Object> params = getActionContext().getParameters();
        if (params.get("insId") == null) {
            String orgId = CurrentUserUtil.getOrgId();
            if (orgId != "-1") {
                // 根据当前用户的机构ID查询
                params.put("insId", new Object[]{orgId});
            }
        }
        return super.list();
    }

    /**
     * 判断是否为管理员
     * @return String 跳转页面字符串
     * @author xuhaigang
     * @createTime 2017-01-18 09:22:58
     */
    public String isadmin() {
        admin = "f";
        Loginer loginer = SpringSecurityUtils.getCurrentUser();
        if (loginer != null) {
            // 找出当前登录用户拥有几个角色
            List<org.zcframework.security.model.Role> roles = loginer.getRoles();
            for (org.zcframework.security.model.Role r : roles) {
                if (SystemConstants.SYSADMIN.equals(r.getRoleName())) {
                    admin = "t";
                }
            }
        }
        return "json";
    }

    /**
     * 用户增加or修改
     * @return String 跳转页面字符串
     * @author xuhaigang
     * @createTime 2017-01-18 09:32:58
     */
    public String save() {
        // 对密码进行md5加密
        if (entity == null || StringUtils.isBlank(entity.getId())) {
            if (StringUtils.isNotEmpty(entity.getPassWord())) {
                entity.setPassWord(MD5Utils.md5(entity.getPassWord()));
            }
        }
        // 对用户名进行唯一性验证
        if (entity != null && StringUtils.isNotEmpty(entity.getUserName())) {
            List<User> userList = this.userService.getUserByUserName(entity.getUserName());
            User u = null;
            if (userList != null && userList.size() > 0) {
                u = userList.get(0);
            }
            if (u != null) {
                if (StringUtils.isNotEmpty(entity.getId())) {
                    // 修改进入
                    if (!u.getId().equals(entity.getId())) {
                        result = new Result(false, "该用户名已经存在！");
                        return RESULT;
                    }
                } else {
                    // 保存进入
                    result = new Result(false, "该用户名已经存在！");
                    return RESULT;
                }
            }
        }
        // 保存或修改
        int res = this.userService.saveOrUpdateUser(entity);
        if (res == 1) {
//            String forward=entity.getId();
            String forward = getHttpServletRequest().getRequestURI();
            String queryString = getHttpServletRequest().getQueryString();
            if (queryString != null && !queryString.equals(""))
                forward = forward + "?" + queryString + "&id=" + entity.getId();
            else
                forward = forward + "?id=" + entity.getId();
            result = new Result(true, "保存用户信息成功", forward, false);
        } else {
            result = new Result(true, "更新用户信息成功");
        }
        return RESULT;
    }

    /**
     * 跳转到新增或编辑页面
     * @return String 跳转页面字符串
     * @author xuhaigang
     * @createTime 2017-01-18 09:42:58
     */
    public String queryrole() {
        roleList = roleService.queryRoleByUserId(entity.getId());
        return "role";
    }

    /**
     * 保存用户与角色的对应关系
     * @author xuhaigang
     * @createTime 2017-01-18 09:52:58
     */
    public void saveuserrole() {
        userService.saveUserRole(entity.getId(), roleIds);
        User user = this.userService.get(entity.getId());
        // 更新内存
        if (!CommonUtil.isNullOrEmpty(user)) {
            this.cacheRefreshService.refreshUser(user.getUserName());
        }
    }

    /**
     * 删除用户
     * @return String 跳转页面字符串
     * @author xuhaigang
     * @createTime 2017-01-18 10:02:58
     */
    public String delete() {
        // 删除用户先删除用户拥有的角色关系
        String id = getHttpServletRequest().getParameter(idName);
        if (StringUtils.isNotEmpty(id)) {
            this.userService.deleteRolesByUserId(id);
        }
        return super.delete();
    }

    /**
     * 重置用户密码
     * @return String 跳转页面字符串
     * @author xiaohongfei
     * @createTime 2016年12月9日下午1:51:36
     */
    public String resetPassword() {
        String id = getHttpServletRequest().getParameter(idName);
        try {
            if (StringUtils.isNotBlank(id)) {
                this.userService.resetPassWord(id, "123456");
            }
            result = new Result(true, getText("user.resetPassword"));
            // 记录日志
            LogUtilsBiz.log(MDCS.USER, LogUtilsBiz.MOD, LOGGER, "重置用户密码-成功", "",
                    "重置密码", "", entity.getUserName() + "-" + entity.getRealName(), "resetPassword",
                    this.getHttpServletRequest());
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, getText("user.resetPassword.failed"));
        }
        return RESULT;
    }

    /**
     * 未知
     * @return String 跳转页面字符串
     * @author xuhaigang
     * @createTime 2017-01-18 10:12:58
     */
    public String passwordui() {
        return "passwordUI";
    }

    /**
     * 修改用户密码
     * @return String 跳转页面字符串
     * @author xuhaigang
     * @createTime 2017-01-18 10:22:58
     */
    public String modifypassword() {
        String oldPassWord = this.getHttpServletRequest().getParameter("oldPassword");
        String userName = SpringSecurityUtils.getCurrentUserName();
        List<User> userList = this.userService.getUserByUserName(userName);
        if (userList != null && userList.size() > 0) {
            User user = userList.get(0);
            if (StringUtils.isNotEmpty(user.getPassWord())) {
                if (!user.getPassWord().equals(MD5Utils.md5(oldPassWord))) {
                    result = new Result(false, "原始密码输入不正确");
                    HttpServletRequest request = ServletActionContext.getRequest();
                    request.setAttribute("result", result);
                    return RESULT;
                }
                String newPassWord = this.getHttpServletRequest().getParameter("npassword");
                user.setPassWord(MD5Utils.md5(newPassWord));
                this.userService.update(user);
                this.cacheRefreshService.refreshUser(userName);
                result = new Result(true, "密码修改成功");
            }
        } else {
            result = new Result(false, "该用户数据库中不存在");
        }
        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("result", result);
        return RESULT;
    }

    /**
     * 重写父类edit方法
     * @return String 跳转页面字符串
     * @author jinliang
     * @createTime 2016-11-29 21:17:55
     **/
    public String edit() {
        if (StringUtils.isNotEmpty(entity.getId())) { // 编辑
            entity = this.userService.get(entity.getId());
            if (StringUtils.isNotEmpty(entity.getInsId())) {
                SysOrg sysOrg = this.sysOrgService.get(entity.getInsId());
                if (null != sysOrg) {
                    entity.setInsName(sysOrg.getName());
                }
            }
        } else { // 新增
            parent = this.sysOrgService.get(ztSysOrgId);
        }

        return "dialog";
    }

    /**
     * 修改个人资料
     * @return String 跳转页面字符串
     * @author xuhaigang
     * @createTime 2017-01-18 10:32:58
     */
    public String editloginuser() {
        String userName = SpringSecurityUtils.getCurrentUserName();
        List<User> userList = this.userService.getUserByUserName(userName);
        if (userList != null && userList.size() > 0) {
            entity = userList.get(0);
        }
        String defaultAreaCode = entity.getDefaultAreaCode(); // 默认居住地址的编码
        if (StringUtils.isNotBlank(defaultAreaCode)) {
            /*String areaName = ""; // 区划名称
            AreaDict areaDict = this.areaDictService.get(defaultAreaCode);
            AreaDict parentAreaDict = null;
            if (null != areaDict) {
                areaName = areaDict.getName();
                if (StringUtils.isNotBlank(areaDict.getPCode())) {
                    parentAreaDict = this.areaDictService.get(areaDict.getPCode());
                }
            }
            if (null != parentAreaDict) {
                areaName = parentAreaDict.getName() + "/" + areaDict.getName();
            }
            if (StringUtils.isNotBlank(areaName)) {
                entity.setHospitalName(areaName);
            }*/
        }
        return "viewuser";
    }

    /**
     * 初始化密码
     * @return String 跳转页面字符串
     * @author xuhaigang
     * @createTime 2017-01-18 10:42:58
     */
    public String initpassword() {
        String userId = this.getHttpServletRequest().getParameter("userId");
        result = new Result(false, "密码修改失败");
        if (StringUtils.isNotEmpty(userId)) {
            User user = this.userService.get(userId);
            if (!CommonUtil.isNullOrEmpty(user)) {
                // 查询系统参数初始化密码
//                String pwd = this.configService.getValueByCode(SystemConstants.INIT_PASSWORD,null);
                String pwd = "";
                if (StringUtils.isBlank(pwd)) pwd = "123456";
                user.setPassWord(MD5Utils.md5(pwd));
                this.userService.update(user);
                result = new Result(true, "密码修改成功");
            }
        }
        return RESULT;
    }

    /**
     * 显示机构
     * @return String 跳转页面字符串
     * @author xuhaigang
     * @createTime 2017-01-18 10:52:58
     */
    public String showins() {
        return "showins";
    }

    /**
     * 点击左侧树节点加载右侧列表数据
     * @return String 跳转页面字符串
     * @author jinliang
     * @createTime 2016-11-09 13:52:01
     **/
    public String userSonList() {
        Map<String, Object> params = getActionContext().getParameters();
        params.put("ztSysOrgId", new Object[]{ztSysOrgId});
        super.list();
        return "sonList";
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getUnitIds() {
        return unitIds;
    }

    public void setUnitIds(String unitIds) {
        this.unitIds = unitIds;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getInsId() {
        return insId;
    }

    public void setInsId(String insId) {
        this.insId = insId;
    }

    public String getZtSysOrgId() {
        return ztSysOrgId;
    }

    public void setZtSysOrgId(String ztSysOrgId) {
        this.ztSysOrgId = ztSysOrgId;
    }

    public SysOrg getParent() {
        return parent;
    }

    public void setParent(SysOrg parent) {
        this.parent = parent;
    }

    public List<String> getNewOrgList() {
        return newOrgList;
    }

    public void setNewOrgList(List<String> newOrgList) {
        this.newOrgList = newOrgList;
    }
    public String getQ() {
        return q;
    }
    public void setQ(String q) {
        this.q = q;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}