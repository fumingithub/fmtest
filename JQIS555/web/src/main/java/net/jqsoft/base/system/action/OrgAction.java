/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.base.system.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.view.support.Result;
import org.zcframework.core.view.support.entity.BaseEntityAction;
import org.zcframework.security.SpringSecurityUtils;

import net.jqsoft.base.system.domain.Org;
import net.jqsoft.base.system.service.IOrgService;
import net.jqsoft.base.system.service.IUserService;
import net.jqsoft.base.system.service.impl.OrgService;

/**
 * 部门管理Action层
 * @author xuhaigang
 * @createTime 2017-01-17 17:48
 */
public class OrgAction extends BaseEntityAction<Org, IOrgService> {

    @Autowired
    private OrgService orgService;

    /** 根据父id查询所有子机构 */
    private List<Org> commList = new ArrayList<Org>();

    /** 标识 f-否，t-是 */
    private String flat;

    /** 父级部门 */
    private Org parentIns;

    /** 是否有查询条件 */
    private String haveCondition;

    /** 是否存在下级：1-存在，0-不存在 */
    private String isHaveChild;

    /**
     * 页面加载方法
     * @return String 跳转页面字符串
     * @author xuhaigang
     * @createTime 2017-01-18 08:42:58
     */
    public String list() {
        Map<String, Object> params = getActionContext().getParameters();
        String[] namestr = (String[]) params.get("name");
        String[] codestr = (String[]) params.get("code");
        String name = "";
        String code = "";
        if (namestr != null) {
            name = namestr[0];
        }
        if (codestr != null) {
            code = codestr[0];
        }

        // 查询条件不为空
        if (StringUtils.isNotEmpty(name) || StringUtils.isNotEmpty(code)) {
            params.put("flag", new Object[]{"true"});
            haveCondition = "isQuery";
        }

        return super.list();
    }

    /**
     * 列表treegrid异步加载
     * @return String 跳转页面字符串
     * @author xuhaigang
     * @createTime 2017-1-18 09:16:58
     */
    public String showchild() {
        String pid = this.getHttpServletRequest().getParameter("parentIns.id");
        entitys = this.orgService.getUnitByPid(pid);
        return "showchild";
    }

    /**
     * 保存
     * @return String 跳转页面字符串
     * @author xuhaigang
     * @createTime 2017-1-18 09:28:58
     */
    public String save() {
        // 验证code的唯一性
        if (StringUtils.isNotEmpty(entity.getCode())) {
            Org unit = this.orgService.getUnitByCode(entity.getCode(), entity.getId());
            if (unit != null) {
                result = new Result(false, "已存在此机构编码！");
                return RESULT;
            }
        }
        return super.save();
    }

    /**
     * 进入编辑页面
     * @return String 跳转页面字符串
     * @author xuhaigang
     * @createTime 2017-1-18 09:48:58
     */
    public String edit() {
        if (parentIns != null && StringUtils.isNotBlank(parentIns.getId())) {
            // 添加子项进入
            parentIns = this.orgService.get(parentIns.getId());
        } else if (this.getEntityId() != null) {
            // 编辑进入
            Org unit = doGetEntity();
            if (StringUtils.isNotBlank(unit.getParentId())) {
                parentIns = this.orgService.get(unit.getParentId());
            }
            // 判断是否存在下级，存在下级，所属地区、编码不可修改
            List<Org> inslist = this.orgService.getUnitByPid(unit.getId());
            if (inslist.size() > 0) {
                isHaveChild = "1";
            }
        }
        return super.edit();
    }

    /**
     * 禁用
     * f-用戶有关联不允许删除
     * @return String 跳转页面字符串
     * @author xuhaigang
     * @createTime 2017-1-18 09:58:58
     */
    public String deleteunit() {
        flat = "t";
        if (entity != null && StringUtils.isNotEmpty(entity.getId())) {
            // 判断是否存在下级，存在下级，所属地区,编码不可修改
            List<Org> inslist = this.orgService.getUnitByPid(entity.getId());
            if (inslist.size() > 0) {
                flat = "1";
                return "deleteJson";
            }
            entity = doGetEntity();
            if (entity.getStatus() == 1) {
                flat = "2";
                return "deleteJson";
            }
            this.orgService.deleteById(entity.getId(), SpringSecurityUtils.getCurrentUserName());
        }
        return "deleteJson";
    }

    @Override
    protected OrgService getEntityManager() {
        return orgService;
    }

    public List<Org> getCommList() {
        return commList;
    }

    public void setCommList(List<Org> commList) {
        this.commList = commList;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public Org getParentIns() {
        return parentIns;
    }

    public void setParentIns(Org parentIns) {
        this.parentIns = parentIns;
    }

    public String getIsHaveChild() {
        return isHaveChild;
    }

    public void setIsHaveChild(String isHaveChild) {
        this.isHaveChild = isHaveChild;
    }

    public String getHaveCondition() {
        return haveCondition;
    }

    public void setHaveCondition(String haveCondition) {
        this.haveCondition = haveCondition;
    }
}
