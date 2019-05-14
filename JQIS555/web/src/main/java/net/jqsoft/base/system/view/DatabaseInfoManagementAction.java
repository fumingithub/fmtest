/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.view;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.view.support.Result;
import org.zcframework.core.view.support.entity.BaseEntityAction;

import net.jqsoft.base.system.domain.DatabaseInfoManagement;
import net.jqsoft.base.system.domain.EquipmentInfor;
import net.jqsoft.base.system.service.IDatabaseInfoManagementService;
import net.jqsoft.base.system.service.IEquipmentInforService;
import net.jqsoft.common.action.MyEntityAction;
import net.jqsoft.common.exception.ManagerException;

/**
 * DatabaseInfoManagementAction
 * 
 * @author wangtao@jqsoft.net
 * @version 1.0
 */
public class DatabaseInfoManagementAction
		extends MyEntityAction<DatabaseInfoManagement, IDatabaseInfoManagementService> {
	private String types; // types:edit--编辑 view--查看
	@Autowired
	private IDatabaseInfoManagementService databaseInfoManagementService;
	@Autowired
	private IEquipmentInforService equipmentInforService;

	@Override
	protected IDatabaseInfoManagementService getEntityManager() {
		return databaseInfoManagementService;
	}

	/**
	 * 跳转到编辑页面
	 * 
	 * @return java.lang.String
	 * @author wangtao
	 * @create 2017-10-18
	 **/
	public String edit() {
		Map<String, Object> params = getActionContext().getParameters();
		// String[] arr = (String[]) params.get("codeCode");
		String[] idarrserv = (String[]) params.get("id");
		String[] serv = (String[]) params.get("serverid");
		String[] typesarrserv = (String[]) params.get("types");
		if (null != serv && serv.length > 0 && StringUtils.isNotEmpty(serv[0])) {
			EquipmentInfor equipmentInfor = equipmentInforService.get(serv[0]);
			// DataElementCode dc = dataElementCodeService.get(arr[0]);
			entity.setEquipmentId(equipmentInfor.getId());
			entity.setDatabaseIp(equipmentInfor.getServerInsideIp());
			// entity.setCodeName(eq.getName());

		}
		if (types != null) {
			return super.edit();
		}
		return INPUT;
		// if(typesarrserv!=null){
		// types = typesarrserv[0];
		// }
		// if(!(idarrserv != null && idarrserv.length > 0)){
		// return super.edit();
		// }
		//// String ida=this.getHttpServletRequest().getParameter("types");
		//// Map<String, Object> idMap = new HashMap<String, Object>();
		//// idMap.put("id",((String[])params.get("id"))[0]);
		// //idMap.put("projectId",entity.getProjectId());
		// entity = databaseInfoManagementService.get(idarrserv[0]);
		// return INPUT;
	}

	/**
	 * 数据库信息删除 author wangtao
	 * 
	 * @createTime 2017-10-25
	 * @return
	 */
	public String delete() {
		try {
			if (StringUtils.isNotBlank(entity.getId())) {
				DatabaseInfoManagement databaseInfoManagement = this.databaseInfoManagementService.get(entity.getId());
				if ("0".equals(databaseInfoManagement.getIsDel())) {
					this.databaseInfoManagementService.deleteDatabaseInfo(entity.getId());
					result = new Result(true, "删除数据库信息成功!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(false, getText("databaseInfoManagement.delete.failed"));
		}
		return RESULT;
	}

	/**
	 * 查询数据（列表页）
	 * 
	 * @return java.lang.String
	 * @author wangtao
	 * @create 2017-10-18
	 **/
	public String list() { // String fg=pro.getId();
		String DataBaseId = this.getHttpServletRequest().getParameter("id");
		EquipmentInfor equipmentInfor = equipmentInforService.get(DataBaseId);
		String ServerType = equipmentInfor.getType();
		if ("03".equals(ServerType) || "02".equals(ServerType)) {
			Map<String, Object> params = getActionContext().getParameters();
			params.put("DataBaseId", new Object[] { DataBaseId });
			super.list();
			return SUCCESS;
		} else {
			result = new Result(false, "不是数据中心服务器或前置机服务器不能进行数据库管理！");
		}
		return RESULT;
	}

	/**
	 * 数据库增加or修改
	 * 
	 * @return java.lang.String
	 * @author wangtao
	 * @create 2017-10-18
	 */
	public String save() {
		try {
			DatabaseInfoManagement databaseInfoManagement = null;
			if (null != entity && StringUtils.isNotEmpty(entity.getUserName())) {
				databaseInfoManagement = this.databaseInfoManagementService.getByUserName(entity.getUserName(),
						entity.getEquipmentId());
				if (null != databaseInfoManagement) {
					// 修改时进入
					if (StringUtils.isNotEmpty(entity.getId())) {
						if (!databaseInfoManagement.getId().equals(entity.getId())) {
							result = new Result(false, "该数据库用户名已经存在！");
							return RESULT;
						}
					} else { // 保存时进入
						result = new Result(false, "该数据库用户名已经存在！");
						return RESULT;
					}
				}
				int res = this.databaseInfoManagementService.saveOrUpdateDatabase(entity);
				if (res == 1) {
					String forward = getHttpServletRequest().getRequestURI();
					String queryString = getHttpServletRequest().getQueryString();
					if (queryString != null && !queryString.equals(""))
						forward = forward + "?" + queryString + "&id=" + entity.getId();
					else
						forward = forward + "?id=" + entity.getId();
					result = new Result(true, "保存数据库信息成功", forward, false);
				} else {
					result = new Result(true, "更新数据库信息成功");
				}
			}
		} catch (ManagerException ex) {
			result = new Result(false, ex.getErrMsg(), "", false);
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(false, "数据库信息保存异常！", "", false);
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
}
