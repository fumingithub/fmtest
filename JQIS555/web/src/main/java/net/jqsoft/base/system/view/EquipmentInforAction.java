/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.view;

import net.jqsoft.base.system.domain.EquipmentInfor;
import net.jqsoft.base.system.domain.Project;
import net.jqsoft.base.system.domain.SysProPermission;
import net.jqsoft.base.system.service.IEquipmentInforService;
import net.jqsoft.base.system.service.IProjectService;
import net.jqsoft.base.system.service.ISysProPermissionService;
import net.jqsoft.base.system.service.IUserService;
import net.jqsoft.common.action.MyEntityAction;
import net.jqsoft.common.exception.ManagerException;
import net.jqsoft.base.system.domain.User;
import net.jqsoft.common.util.CommonUtil;
import net.jqsoft.common.util.CurrentUserUtil;
import org.apache.commons.lang3.StringUtils;
import org.zcframework.core.dao.support.ORMType;
import org.zcframework.core.dao.support.ibatis.IBatisModelSetup;
import org.zcframework.core.view.support.Result;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.view.support.entity.BaseEntityAction;
import org.zcframework.security.SpringSecurityUtils;
import org.zcframework.security.object.Loginer;

import javax.swing.*;

/**
 * EquipmentInforAction
 * 
 * @author wangtao@jqsoft.net
 * @version 1.0
 */
public class EquipmentInforAction extends MyEntityAction<EquipmentInfor, IEquipmentInforService> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String types; // types:edit--编辑 view--查看
	@Autowired
	private IEquipmentInforService equipmentInforService;
	@Autowired
	private IProjectService projectService;
	@Autowired
	private ISysProPermissionService sysProPermissionService;
	@Autowired
	private IUserService userService;
	/** 项目实体 */
	private Project project;
	private EquipmentInfor equipmentInfor;
	private String serverImportSelected;
	private String selectRealName;
	private String serverUsername;
	private String serverPassword;
	private String serverIp;
	@Override
	protected IEquipmentInforService getEntityManager() {
		return equipmentInforService;
	}

	/**
	 * 查询数据（列表页）
	 * 
	 * @return java.lang.String
	 * @author wangtao
	 * @create 2017-10-17
	 **/
	public String list() {
		String EuipmentId = this.getHttpServletRequest().getParameter("id");
		Map<String, Object> params = getActionContext().getParameters();
		for (Map.Entry entry : params.entrySet()) {
			if (entry.getValue() instanceof String[]) {
				String[] vals = (String[]) entry.getValue();
				entry.setValue(new String[]{vals[0].toString().trim()});
			}
		}
		params.put("EuipmentId", new Object[] { EuipmentId });
//			String loginId = this.getHttpServletRequest().getParameter("loginId");
//			params.put("loginId", new Object[] { loginId });
			IBatisModelSetup model = (IBatisModelSetup) getModelSetupFromRequest(ORMType.IBATIS);
			model.setCountName("EquipmentInfor.count");
			model.setSqlName("EquipmentInfor.select");
			this.setFilter(model);
			doPageEntity(model);
		return SUCCESS;

	}
	/**
	 * 服务器信息导入
	 */
	public String serList() throws UnsupportedEncodingException {
		//参数去空格
		Map<String, Object> params = getActionContext().getParameters();
		 serverImportSelected = this.getHttpServletRequest().getParameter("serverImportSelected");
			Loginer loginer = CurrentUserUtil.getCurrentUser();
			String  currentUserId = loginer.getId().toString();
			params.put("currentUserId", new Object[] { currentUserId });
		   params.put("serverImportSelected", new Object[] { serverImportSelected });
		for (Map.Entry entry : params.entrySet()) {
			if (entry.getValue() instanceof String[]) {
				String[] vals = (String[]) entry.getValue();
				entry.setValue(new String[]{vals[0].toString().trim()});
			}
		}
		IBatisModelSetup model = (IBatisModelSetup) getModelSetupFromRequest(ORMType.IBATIS);
		model.setCountName("EquipmentInfor.countEquipmentInforList");
		model.setSqlName("EquipmentInfor.qryEquipmentInforList");
		this.setFilter(model);
		doPageEntity(model);
		return "serverSelectList";
	}

	/**
	 * 保存选择的服务器信息
	 */
	public String getById(){
		String id = this.getHttpServletRequest().getParameter("id");
		String json = "";
		equipmentInfor = equipmentInforService.get(id);
		json = "equipmentInfor";
		return json;
	}

	/**
	 * 跳转到编辑页面

	 * 
	 * @return java.lang.String
	 * @author wangtao
	 * @create 2017-10-17
	 **/
	public String edit() {
		Map<String, Object> params = getActionContext().getParameters();
		selectRealName=this.getHttpServletRequest().getParameter("selectRealName");
		// String[] idarr = (String[]) params.get("id");
		String[] proj = (String[]) params.get("projectid");
		// String typesarr = (String) params.get("types");
		if (null != proj && proj.length > 0 && StringUtils.isNotEmpty(proj[0])) {
			Project pro = projectService.get(proj[0]);
			// DataElementCode dc = dataElementCodeService.get(arr[0]);
			entity.setProjectId(pro.getId());
			// entity.setCodeName(eq.getName());

		}
		if (types != null) {
			return super.edit();
		}
		return INPUT;
	}

	/**
	 * 服务器信息删除 author wangtao
	 * 
	 * @createTime 2017-10-25
	 * @return
	 */
	public String delete() {
		try {
			if (StringUtils.isNotBlank(entity.getId())) {
				EquipmentInfor equipmentInfor = this.equipmentInforService.get(entity.getId());
				if ("0".equals(equipmentInfor.getIsDel())) {
					this.equipmentInforService.deleteEquipInfo(entity.getId());
					result = new Result(true, "删除服务器信息成功!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(false, getText("equipmentInfor.delete.failed"));
		}
		return RESULT;
	}

	/**
	 * 服务器增加or修改
	 * 
	 * @return java.lang.String
	 * @author wangtao
	 * @create 2017-10-16
	 */
	public String save() {
		try {
			EquipmentInfor equipmentInfor = null;
			List<EquipmentInfor> list = new ArrayList<EquipmentInfor>();
			if (null != entity && (StringUtils.isNotEmpty(entity.getServerName()))) {
				list=equipmentInforService.getServerByServerName(entity.getServerName());
				if (null != list) {
					for (EquipmentInfor valueList : list) {
						if (!"".equals(valueList.getServerName())
								&& entity.getServerName().equals(valueList.getServerName())) {
							if (StringUtils.isNotEmpty(entity.getId())) { // 修改时进入
								if (!valueList.getId().equals(entity.getId())) {
									result = new Result(false, "该项目名称已经存在！");
									return RESULT;
								}
							} else { // 保存时进入
								result = new Result(false, "该项目名称已经存在！");
								return RESULT;
							}
						}
					}
				}
				int res = this.equipmentInforService.saveOrUpdateEquipment(entity);
				if (res == 1) {
					String forward = getHttpServletRequest().getRequestURI();
					String queryString = getHttpServletRequest().getQueryString();
					if (queryString != null && !queryString.equals("")){
						forward = forward + "?" + queryString + "&id=" + entity.getId();
					}
					else{
						forward = forward + "?id=" + entity.getId();
					    result = new Result(true, "保存服务器信息成功", forward, false);
					}
				} else {
					result = new Result(true, "更新服务器信息成功");
				}
			}

		} catch (ManagerException ex) {
			result = new Result(false, ex.getErrMsg(), "", false);
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(false, "服务器信息保存异常！", "", false);
		} finally {
			// 4、返回提示信息到页面
			return RESULT;
		}
	}
	//远程桌面管理
	public String remote(){
		serverUsername = this.getHttpServletRequest().getParameter("serverUsername");
		serverPassword = this.getHttpServletRequest().getParameter("serverPassword");
		serverIp = this.getHttpServletRequest().getParameter("serverIp");
		return "serverremote";
	}

	public String reSave() {
		try {
			if (null != entity) {
				int res = this.equipmentInforService.saveOrUpdateEquipment(entity);
				if (res == 1) {
					String forward = getHttpServletRequest().getRequestURI();
					String queryString = getHttpServletRequest().getQueryString();
					if (queryString != null && !queryString.equals("")){
						forward = forward + "?" + queryString + "&id=" + entity.getId();
					}
					else{
						forward = forward + "?id=" + entity.getId();
						result = new Result(true, "保存服务器信息成功", forward, false);
					}
				} else {
					result = new Result(true, "更新服务器信息成功");
				}
			}

		} catch (ManagerException ex) {
			result = new Result(false, ex.getErrMsg(), "", false);
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(false, "服务器信息保存异常！", "", false);
		} finally {
			// 4、返回提示信息到页面
			return RESULT;
		}
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project Project) {
		this.project = project;
	}
	public EquipmentInfor getEquipmentInfor() {
		return equipmentInfor;
	}

	public void setEquipmentInfor(EquipmentInfor equipmentInfor) {
		this.equipmentInfor = equipmentInfor;
	}

	public String getServerImportSelected() {
		return serverImportSelected;
	}

	public void setServerImportSelected(String serverImportSelected) {
		this.serverImportSelected = serverImportSelected;
	}

	public String getSelectRealName() {
		return selectRealName;
	}

	public void setSelectRealName(String selectRealName) {
		this.selectRealName = selectRealName;
	}
	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getServerPassword() {
		return serverPassword;
	}

	public void setServerPassword(String serverPassword) {
		this.serverPassword = serverPassword;
	}

	public String getServerUsername() {
		return serverUsername;
	}

	public void setServerUsername(String serverUsername) {
		this.serverUsername = serverUsername;
	}

}
