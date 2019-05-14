/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.base.system.action;

import net.jqsoft.base.system.domain.SysOrg;
import net.jqsoft.base.system.service.ISysOrgService;
import net.jqsoft.common.action.MyEntityAction;
import net.jqsoft.common.exception.ManagerException;
import net.jqsoft.common.util.CurrentUserUtil;
import net.jqsoft.common.util.LogUtilsBiz;
import net.jqsoft.common.util.MDCS;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.view.support.Result;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SysOrgAction
 * @author jinliang@jqsoft.net
 * @version 1.0
 */
public class SysOrgAction extends MyEntityAction<SysOrg, ISysOrgService> {
    @Autowired
    private ISysOrgService sysOrgService;

    @Override
    protected ISysOrgService getEntityManager() {
        return sysOrgService;
    }

    /** 组织机构List集合 */
    private List<SysOrg> sysOrgList;

    /** 父级组织机构实体对象 */
    private SysOrg parent;

    /** zTree树-组织机构编码 */
    private String ztId;

    /** zTree树-属地区划编码 */
    private String ztAreaId;

    /** 跳转页面状态：1-新增，2-编辑（树），3-添加子项（列表），4-编辑（列表） */
    private String editType;

    /** 操作类型：edit-编辑，view-查看 */
    private String types;

    /** 需要调动的组织机构编码 */
    private String transferSysOrgId;


    /**
     * ztree树页面展示
     * @return java.lang.String
     * @author jinliang
     * @create 2016-11-09 10:38:45
     **/
    public String list() {
        sysOrgList = this.sysOrgService.querySysOrgList("");
        return super.list();
    }

    /**
     * 重写编辑和新增方法，获取父组织机构
     * @return java.lang.String
     * @author jinliang
     * @create 2016-11-09 11:36:34
     **/
    public String edit() {

        boolean flag = true;

        try {
            String id = this.getHttpServletRequest().getParameter("id");
            String parentId = this.getHttpServletRequest().getParameter("parentId");
            editType = this.getHttpServletRequest().getParameter("editType");
            types = this.getHttpServletRequest().getParameter("types");

            if ("3".equals(editType)) { // 添加子项（列表）

                if (StringUtils.isNotEmpty(parentId)) {
                    parent = this.sysOrgService.get(parentId);
                }
            }
            if ("4".equals(editType)) { // 编辑（列表）
                entity = this.sysOrgService.get(id);

                SysOrg sysOrg = null;
                if (StringUtils.isNotEmpty(entity.getParentId())) {
                    sysOrg = this.sysOrgService.get(entity.getParentId());
                }
                if (null != sysOrg) {
                    entity.setParentName(sysOrg.getName());
                }
            }

        } catch (ManagerException ex) {
            flag = false;
            result = new Result(false, ex.getErrMsg(), "", false);
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
            result = new Result(false, "查询部门信息异常！（ACTION）", "", false);
        } finally {
            if (flag) {
                return "dialog";
            } else {
                return "_result";
            }
        }
    }

    /**
     * 新增/修改 组织机构信息
     * @return java.lang.String
     * @author jinliang
     * @create 2016-11-09 11:38:47
     **/
    public String save() {

        try {
            // 1、对组织机构编码进行唯一性验证
            if (null != entity && StringUtils.isNotEmpty(entity.getId())) {
                SysOrg org = this.sysOrgService.get(entity.getId());
                if (null != org) {
                    if (StringUtils.isNotEmpty(entity.getId())) { // 修改时进入
                        if (!org.getId().equals(entity.getId())) {
                            result = new Result(false, "该部门编码已经存在！");
                            return "_result";
                        }
                    } else { // 保存时进入
                        result = new Result(false, "该部门编码已经存在！");
                        return "_result";
                    }
                }
            }

            // 2、进行新增/修改操作
            int res = this.sysOrgService.saveOrUpdateSysOrg(entity); // 影响行数
            if (res == 1) {
                String forward = getHttpServletRequest().getRequestURI();
                String queryString = getHttpServletRequest().getQueryString();
                if (StringUtils.isNotEmpty(queryString)) {
                    forward = forward + "?" + queryString + "&id=" + entity.getId();
                } else {
                    forward = forward + "?id=" + entity.getId();
                }
                result = new Result(true, "保存部门信息成功！", forward, false);
            } else {
                result = new Result(true, "更新部门信息成功！");
            }
        } catch (ManagerException ex) {
            result = new Result(false, ex.getErrMsg(), "", false);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "保存部门信息异常！（ACTION）", "", false);
        } finally {
            // 3、返回提示信息到页面
            return "_result";
        }
    }

    /**
     * 停用操作
     * @return java.lang.String
     * @author jinliang
     * @create 2016-10-24 15:11:17
     **/
    public String setStatus() {
        String id = this.getHttpServletRequest().getParameter("id");
        String statusTemp = this.getHttpServletRequest().getParameter("statusTemp");
        String forward = getHttpServletRequest().getRequestURI();
        String status = "1"; // 保存到数据表里的值，默认：状态
        String rtnMsg = ""; // 返回页面的提示信息

        if ("0".equals(statusTemp)) {
            status = "1";
            rtnMsg = "启用成功！";
        } else {
            status = "0";
            rtnMsg = "禁用成功！";
        }
        if (StringUtils.isNotEmpty(id)) {
            List<SysOrg> adList = this.sysOrgService.hasSonSysOrgById(id);
            if (null != adList && adList.size() > 0) { // 有下级组织机构
                this.sysOrgService.updateStatusById(status, id);
                result = new Result(true, rtnMsg, forward, false);
            } else { // 无下级组织机构
                entity.setStatus(status);
                this.sysOrgService.saveOrUpdateSysOrg(entity);
                result = new Result(true, rtnMsg, forward, false);
            }
        }

        return "_result";
    }

    /**
     * 异步加载，用于zTree展开某个父节点的下一级所有子节点
     * @return java.lang.String
     * @author jinliang
     * @create 2016-11-09 10:39:28
     **/
    public String ajaxQueryOrgForZTree() {
        String paramId = entity.getId(); // 组织机构编码
        String sysOrgName = this.getHttpServletRequest().getParameter("sysOrgName"); // 组织机构名称过滤信息
        String transferSysOrgIds = this.getHttpServletRequest().getParameter("transferSysOrgIds");

        if (StringUtils.isEmpty(transferSysOrgIds) || "undefined".equals(transferSysOrgIds)) {
            transferSysOrgIds = this.getHttpServletRequest().getParameter("currentId");
        }

        if (StringUtils.isNotEmpty(sysOrgName)) {
            try {
                sysOrgName = URLDecoder.decode(URLDecoder.decode(sysOrgName, "UTF-8"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        /* 当前用户所在的机构过滤，“管理员角色”除外 addby jinliang at 2016-11-30 */
        String filterOrgId = "";
        String currentOrgId = CurrentUserUtil.getOrgId(); // 当前用户所在的机构
        if (StringUtils.isEmpty(paramId) && StringUtils.isNotEmpty(currentOrgId)
                && !CurrentUserUtil.isadmin()) {
            filterOrgId = currentOrgId;
        }

        sysOrgList = this.sysOrgService.querySysOrgListByParentId(paramId, sysOrgName, transferSysOrgIds, filterOrgId);
        return "result";
    }

    /**
     * 点击左侧树节点加载右侧列表数据
     * @return java.lang.String
     * @author jinliang
     * @create 2016-11-09 13:52:01
     **/
    public String orgSonList() {
        ztId = this.getHttpServletRequest().getParameter("ztId");
        Map<String, Object> params = getActionContext().getParameters();
        params.put("ztId", new Object[] {ztId});
        super.list();
        return "sonList";
    }

    /**
     * 跳转到组织机构调动页面
     * @return java.lang.String
     * @author jinliang
     * @create 2016-11-11 15:02:15
     **/
    public String orgTransfer() {
        return "transfer";
    }

    /**
     * 组织机构调动
     * @return java.lang.String
     * @author jinliang
     * @create 2016-11-11 15:02:06
     **/
    public String transferOrg() {
        String transferId = this.getHttpServletRequest().getParameter("transferId"); // 需要调动的组织机构
        String selected = this.getHttpServletRequest().getParameter("selected"); // 被调动到的组织机构

        if (StringUtils.isNotEmpty(transferId) && StringUtils.isNotEmpty(selected)) {
            SysOrg org = this.sysOrgService.get(selected); // 获取被调动到的组织机构的id、name信息
            entity.setId(transferId);
            if (null != org) {
                entity.setParentId(org.getId());
            }
            int rs = this.sysOrgService.saveOrUpdateSysOrg(entity);
            if (rs > 0) {
                result = new Result(true, "组织机构调动成功！", "", false);
            } else {
                result = new Result(true, "组织机构调动失败！");
            }
        } else {
            result = new Result(true, "组织机构调动失败！");
        }
        return "_result";
    }

    /**
     * 组织机构删除
     * @return java.lang.String
     * @author xuhaigang
     * @create 2016-01-19 11:017:06
     **/
    public String delete() {
    	if (entity != null && StringUtils.isNotEmpty(entity.getId())) {
    		List<SysOrg> orglist = sysOrgService.hasSonSysOrgById(entity.getId());
    		if (orglist.size() > 0) {  //存在下级部门
    			result = new Result(false, "该部门存在下级部门，不允许删除！", "", false);
            }else{
            	sysOrgService.removeById(entity.getId());
            	result = new Result(true, "删除成功！", "", false);
            }
    	}
        return "_result";
    }


    /**
     * 跳转到组织授权界面
     * @return
     */
    public String grantAuth(){
            return "grantauth";
    }

    public String orgGrantAuth(){
        try{
            String transferId = this.getHttpServletRequest().getParameter("transferId"); // 授权机构id
            String selected = this.getHttpServletRequest().getParameter("selected"); // 区划编码
            if (StringUtils.isNotEmpty(selected)) {
                String sysorgName = sysOrgService.queryByAuthAreaCode(selected);
                if(sysorgName!=null && !("").equals(sysorgName)){   //该村的授权区划编码已存在
                    result = new Result(false, "已授权给<'"+sysorgName+"'>，请选择其他区划！");
                    return RESULT;
                }else{
                    sysOrgService.updateAuthAreaCode(transferId,selected);
                    result = new Result(true, "组织机构授权成功！");
                }
            }else {
                result = new Result(false, "组织机构授权失败！");
            }
        } catch (ManagerException ex) {
            result = new Result(false, ex.getErrMsg(), "", false);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "组织机构授权异常！（ACTION）", "", false);
        } finally {
            // 3、返回提示信息到页面
            return "_result";
        }

    }

    /**
     * 异步加载，用于zTree展开某个父节点的下一级所有子节点
     * @return java.lang.String
     * @author jinliang
     * @create 2016-11-02 14:16:28
     **/
    public String ajaxQueryAreasForZTree() {
        String paramCode = entity.getId(); // 行政区划编码
        String ncmsAreaName = this.getHttpServletRequest().getParameter("ncmsAreaName"); // 行政区划名称过滤信息
        if (StringUtils.isNotEmpty(ncmsAreaName)) {
            try {
                ncmsAreaName = URLDecoder.decode(URLDecoder.decode(ncmsAreaName, "UTF-8"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pCode", paramCode);
        params.put("areaLevel", "");
        params.put("ncmsAreaName", ncmsAreaName);
        params.put("isFilter", "false");
        return "ncms_result";
    }

    /**
     * 取消机构授权
     * @author			xiaohf
     * @createDate		2016年12月9日下午3:38:23                                                                 
     * @return
     */
    public String cancelAuth(){
		String id = getHttpServletRequest().getParameter(idName);
		try {
			if (StringUtils.isNotBlank(id)) {
				 this.sysOrgService.cancelAuth(id);
			}
			result = new Result(true, getText("sysOrg.cancelAuth"));
			 // 记录日志
            LogUtilsBiz.log(MDCS.ORG, LogUtilsBiz.MOD, LOGGER, "取消组织机构授权-成功", "",
                "取消组织机构授权",entity.getName(), "cancelAuth",
                this.getHttpServletRequest());
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(false, getText("sysOrg.cancelAuth.failed"));
		}
		return "_result";
	}
    
    public List<SysOrg> getSysOrgList() {
        return sysOrgList;
    }

    public void setSysOrgList(List<SysOrg> sysOrgList) {
        this.sysOrgList = sysOrgList;
    }

    public SysOrg getParent() {
        return parent;
    }

    public void setParent(SysOrg parent) {
        this.parent = parent;
    }

    public String getZtId() {
        return ztId;
    }

    public void setZtId(String ztId) {
        this.ztId = ztId;
    }

    public String getEditType() {
        return editType;
    }

    public void setEditType(String editType) {
        this.editType = editType;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getTransferSysOrgId() {
        return transferSysOrgId;
    }

    public void setTransferSysOrgId(String transferSysOrgId) {
        this.transferSysOrgId = transferSysOrgId;
    }

    public String getZtAreaId() {
        return ztAreaId;
    }

    public void setZtAreaId(String ztAreaId) {
        this.ztAreaId = ztAreaId;
    }


}
