/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.view;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jqsoft.base.system.domain.SysProPermission;
import net.jqsoft.base.system.domain.User;
import net.jqsoft.base.system.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.zcframework.core.view.support.Result;
import org.zcframework.security.SpringSecurityUtils;
import org.zcframework.security.object.Loginer;

import net.jqsoft.base.system.domain.Project;
import net.jqsoft.common.action.MyEntityAction;
import net.jqsoft.common.exception.ManagerException;
import net.jqsoft.common.util.CommonUtil;
import net.jqsoft.phimp.util.ExcelUtils;

/**
 * ProjectAction
 * 
 * @author wangtao@jqsoft.net
 * @version 1.0
 */
public class ProjectAction extends MyEntityAction<Project, IProjectService> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 功能区分types:add-新增;edit-编辑;view-查看 */
	private String types;
	private String loginId;
	@Autowired
	private IProjectService projectService;
	@Autowired
	private IDatabaseInfoManagementService databaseInfoManagementService;
	@Autowired
	private ISysProPermissionService sysProPermissionService;
	@Autowired
	private IEquipmentInforService equipmentInforService;
	@Autowired
	private IUserService userService;
	@Override
	protected IProjectService getEntityManager() {
		return projectService;
	}
	private String  selectRealName;
	private List<User> userSelectlist;
	/**
	 * 查询数据（列表页）
	 * 
	 * @return java.lang.String
	 * @author wangtao
	 * @create 2017-10-26
	 **/
	@Override
	public String list() {
		List<Project> list=null;
		List<Project> selectguardianList=null;
		Loginer loginer = (Loginer) SpringSecurityUtils.getCurrentUser();
		loginId=loginer.getId().toString();
		String EuipmentId = this.getHttpServletRequest().getParameter("id");
		Map<String, Object> params = getActionContext().getParameters();
		for (Map.Entry entry : params.entrySet()) {
			if (entry.getValue() instanceof String[]) {
				String[] vals = (String[]) entry.getValue();
				entry.setValue(new String[]{vals[0].toString().trim()});
			}
		}
		String[] array = (String[]) params.get("realName");
//		String[] array1 = (String[]) params.get("guardian");
		if((array==null||StringUtils.isBlank(array[0]))){
			params.put("loginId", new Object[] { loginId });
		}else{
			params.put("loginId", new Object[] { loginId });
			selectRealName=array[0];
//			String selectguardian=array1[0];
			if(!"".equals(selectRealName)) {
				list = projectService.selectProjectIdByselectRealName(selectRealName);
			}
//			if(!"".equals(selectguardian)) {
//				selectguardianList = projectService.selectProjectIdByselectguardian(selectguardian);
//			}
			String id="";
			String guardian="";
            if(null!=list && list.size()>0) {
				for (Project values : list) {
					id += ",'" + values.getId().toString() + "'";

				}
				if(!"".equals(id)){
					id = id.substring(1);
				}
			}else{
				id+=" ";
			}
			//
//			if(null!=selectguardianList && selectguardianList.size()>0) {
//				for (Project values : selectguardianList) {
//					if(null!=values.getGuardian()) {
//						guardian += ",'" + values.getGuardian().toString() + "'";
//					}
//				}
//				if(!"".equals(guardian)){
//					guardian = guardian.substring(1);
//				}
//				else{
//					guardian+="";
//				}
//			}else{
//				guardian+=" ";
//			}
			if(StringUtils.isNotBlank(id)){
				params.put("Projectid", new Object[]{id});
			}
//			if(StringUtils.isNotBlank(guardian)){
//				params.put("guardian", new Object[]{guardian});
//			}
		}
		params.put("EuipmentId", new Object[] { EuipmentId });
		super.list();
		return SUCCESS;
	}

	/**
	 * 跳转到 项目维护页面
	 *
	 * @return java.lang.String
	 * @author wangtao
	 * @create 2017-10-16
	 **/
	@Override
	public String edit() {
		userSelectlist=userService.querygetUserByUserNameAll();
		if (!"add".equals(types)) {
			entity = this.projectService.get(entity.getId());
		}
			return INPUT;
		}

	// public String edit() {
	// types = this.getHttpServletRequest().getParameter("types");
	// super.edit();
	// //** 获取当前用户的ID *//*
	// String projectId = CurrentUserUtil.getCurrentUser().getId().toString();
	// Project project = null;
	// if (StringUtils.isNotBlank(projectId)) {
	// project = this.projectService.get(projectId);
	// }
	// return INPUT;
	//
	// }
	/**
	 * 项目增加or修改
	 * 
	 * @return java.lang.String
	 * @author wangtao
	 * @create 2017-10-16
	 */
	@Override
	public String save() {
		try {
			Project project = null;
			List<Project> list = new ArrayList<Project>();
			if (null != entity && (StringUtils.isNotEmpty(entity.getProjectName())
					|| StringUtils.isNotEmpty(entity.getPlatformAddr()))) {
				list = this.projectService.getprojectNameAndUrl(entity.getProjectName(), entity.getPlatformAddr());
				if (null != list) {
					for (Project valueList : list) {
						if (!"".equals(valueList.getProjectName())
								&& entity.getProjectName().equals(valueList.getProjectName())) {
							if (StringUtils.isNotEmpty(entity.getId())) { // 修改时进入
								if (!valueList.getId().equals(entity.getId())) {
									result = new Result(false, "该项目名称已经存在！");
									return RESULT;
								}
							} else { // 保存时进入
								result = new Result(false, "该项目名称已经存在！");
								return RESULT;
							}
						} else {
							if (StringUtils.isNotEmpty(entity.getId())) { // 修改时进入
								if (!valueList.getId().equals(entity.getId())) {
									result = new Result(false, "该平台地址已经存在！");
									return RESULT;
								}
							} else { // 保存时进入
								result = new Result(false, "该平台地址已经存在！");
								return RESULT;
							}
						}

					}

					// if (StringUtils.isNotBlank(entity.getId())) { // 修改时进入
					// if
					// (entity.getProjectName().equals(project.getProjectName())
					// && !entity.getId().equals(project.getId())) {
					// result = new Result(false, "该项目已经存在！");
					// return RESULT;
					// }
					//
					// if
					// (entity.getPlatformAddr().equals(project.getPlatformAddr())
					// && !entity.getId().equals(project.getId())) {
					// result = new Result(false, "该平台地址已经存在！");
					// return RESULT;
					// }
					//
					// } else { // 保存时进入
					// if
					// (entity.getProjectName().equals(project.getProjectName()))
					// {
					// result = new Result(false, "该项目已经存在！");
					// return RESULT;
					// }
					//
					// if
					// (entity.getPlatformAddr().equals(project.getPlatformAddr()))
					// {
					// result = new Result(false, "该平台地址已经存在！");
					// return RESULT;
					// }
					// }

				}
				int res = this.projectService.saveOrUpdateProject(entity);
				if (res == 1) {
					String forward = getHttpServletRequest().getRequestURI();
					String queryString = getHttpServletRequest().getQueryString();
					if (queryString != null && !queryString.equals(""))
						forward = forward + "?" + queryString + "&id=" + entity.getId();
					else
						forward = forward + "?id=" + entity.getId();
					result = new Result(true, "保存项目维护信息成功", forward, false);
				} else {
					result = new Result(true, "更新项目维护信息成功");
				}
			}
		} catch (ManagerException ex) {
			result = new Result(false, ex.getErrMsg(), "", false);
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(false, "项目信息保存异常！", "", false);
		} finally {
			// 4、返回提示信息到页面
			return RESULT;
		}
	}
	

	/**
	 * 项目删除 author wangtao
	 * 
	 * @createTime 2017-10-25
	 * @return
	 */
	@Override
	public String delete() {
		try {
			if (entity != null) {
				String[] ids = entity.getId().split(",");
				for (String id : ids) {
					if (StringUtils.isNotBlank(id)) {
						Project project = this.projectService.get(id);
						if ("0".equals(project.getIsDel())) {
							this.projectService.deleteProject(id);
							equipmentInforService.deleteEquipInfoByProjId(id);
//							databaseInfoManagementService.deleteDatabaseInfo(equipmentInforService.get(project.getId()));
							result = new Result(true, "删除项目信息成功!");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(false, "删除项目信息异常!");
		}
		return RESULT;
	}

	// /** 导出Excel文件名称 */
	// private String fileName;
	// /** 流 */
	// private InputStream excelFile;
	// private StringBuffer excelName = new StringBuffer();
	//
	// public String reportExcel(){
	// Map<String,Object> param = new HashMap<String, Object>();
	// String projectNameId =
	// this.getHttpServletRequest().getParameter("projectNameId");
	// if (StringUtils.isNotBlank(projectNameId)){
	// param.put("projectNameId",projectNameId);
	// }
	// String platformTypeId =
	// this.getHttpServletRequest().getParameter("platformTypeId");
	// if (StringUtils.isNotBlank(platformTypeId)){
	// param.put("platformTypeId",platformTypeId);
	// }
	// String checkSysTypeId =
	// this.getHttpServletRequest().getParameter("checkSysTypeId");
	// if (StringUtils.isNotBlank(checkSysTypeId)){
	// param.put("checkSysTypeId",checkSysTypeId);
	// }
	// String isDelId = this.getHttpServletRequest().getParameter("isDelId");
	// if (StringUtils.isNotBlank(isDelId)){
	// param.put("isDelId",isDelId);
	// }
	// List<Project> list = projectService.queryAllList(param);
	// try {
	// String sheetName ="项目信息导出";
	// SimpleDateFormat simpleDateFormat = new
	// SimpleDateFormat("yyyyMMddHHmmss");
	// fileName = sheetName + simpleDateFormat.format(new Date()) +".xlsx";
	// fileName=new String(fileName.getBytes("gb2312"), "iso8859-1");
	// List<Object[]> exList = installExcel(list);
	// // 获取系统绝对路径
	// String rootPath = CommonUtil.getWebContentPath(DictDownLoadAction.class);
	// excelFile = ExcelUtils.export(rootPath + "template\\项目信息导出.xlsx", exList,
	// 2, 0);
	// getHttpServletResonse().setHeader("Set-Cookie", "fileDownload=true;
	// path=/");
	// result = new Result(true, "导出成功");
	//
	// }catch (Exception e) {
	// e.printStackTrace();
	// result = new Result(false, "操作失败！");
	// }
	// return "excel";
	// }
	/**
	 * 
	 * @author wangtao
	 * @createTime 2017-10-30
	 * @return
	 * @throws IOException
	 */
	private InputStream excelFile;
	private String fileName;
	/** 导出名称 */
	@Value("${pr_excel_name}")
	private String FILE_EXCEL_NAME;

	public String reportExcel() throws IOException {   
		List<Project> projectInfoList = null;
		Map<String, Object> param = new HashMap<String, Object>();
		// String id = this.getHttpServletRequest().getParameter("ids");
		//
		// if (StringUtils.isNotBlank(id)){
		// param.put("id",id);
		// }
		if (entity != null) {
			String proId = "";
			String[] ids = entity.getId().split(",");
			if (!"".equals(ids[0])) {
				for (String id : ids) {
					id = ",'" + id + "'";
					proId = proId + id;
				}
				proId = proId.substring(1, proId.length());
			}
			param.put("id", proId);
		}

		String projectName = this.getHttpServletRequest().getParameter("projectName");
		if (StringUtils.isNotBlank(projectName)) {
			param.put("projectName", projectName);
		}
		String platformType = this.getHttpServletRequest().getParameter("platformType");
		if (StringUtils.isNotBlank(platformType)) {
			param.put("platformType", platformType);
		}
		String checkSysType = this.getHttpServletRequest().getParameter("checkSysType");
		if (StringUtils.isNotBlank(checkSysType)) {
			param.put("checkSysType", checkSysType);
		}
		String isDel = this.getHttpServletRequest().getParameter("isDel");
		if (StringUtils.isNotBlank(isDel)) {
			param.put("isDel", isDel);
		}
		List<Project> list = projectService.queryAllList(param);
		try {
			projectName = URLDecoder.decode(URLDecoder.decode(projectName, "UTF-8"), "UTF-8");
			fileName = projectName + " " + FILE_EXCEL_NAME + ".xlsx";
			fileName = new String(fileName.getBytes("gb2312"), "iso8859-1");
			List<Object[]> exList = installExcel(list);
			// 获取 系统绝对路径
			String rootPath = CommonUtil.getWebContentPath(ProjectAction.class);
			// 赋值内容数据
			excelFile = ExcelUtils.export(rootPath + "template\\项目信息导出模板.xlsx", exList, 2, 0);
			getHttpServletResonse().setHeader("Set-Cookie", "fileDownload=true; path=/");
			result = new Result(true, "导出成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(false, "操作失败！");
		}
		return "excel";
	}
	/**
	 * 接入方导入
	 */
	public String AccessImport() {
//		try {
//			if (StringUtils.isNotEmpty(organizationId)) {
//				Organization organization = organizationService.get(organizationId);
//				if (organization != null && StringUtils.isNotBlank(organization.getName())) {
//					organName = URLDecoder.decode(URLDecoder.decode(organization.getName(), "UTF-8"), "UTF-8");
//					hrInsType = organization.getHrInsType();
//					if (StringUtils.isBlank(hrInsType)) {
//						hrInsType = "999";
//					}
//				}
//			}
//		} catch (Exception e) {
//			result = new Result(false, "操作失败！");
//			return RESULT;
//		}
		return "accessImport";
	}
	/**
	 * 转换数据格式
	 * 
	 * @author wangtao
	 * @createTime 2017-10-30
	 * @param projectList
	 * @return List<Object[]>
	 */
	private List<Object[]> installExcel(List<Project> projectList) {
		List<Object[]> list = new ArrayList<Object[]>();
		Object[] objs;
		// SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd
		// HH:mm:ss");
		for (Project pro : projectList) {
			objs = new Object[14];
			objs[0] = pro.getProjectName();
			objs[1] = pro.getArea();
			objs[2] = pro.getEquipment();
			objs[3] = pro.getEquipmentIp();
			objs[4] = pro.getAdmin();
			objs[5] = pro.getAdminPw();
			objs[6] = pro.getDomainName();
			objs[7] = pro.getDomainCode();
			objs[8] = pro.getDataAddr();
			objs[9] = pro.getUserName();
			objs[10] = pro.getPassWord();
			objs[11] = pro.getIsDataStruUp();
			objs[12] = pro.getIsCheckUp();
			objs[13] = pro.getIsEtlUp();
			list.add(objs);
		}
		return list;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(InputStream excelFile) {
		this.excelFile = excelFile;
	}
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getSelectRealName() {
		return selectRealName;
	}

	public void setSelectRealName(String selectRealName) {
		this.selectRealName = selectRealName;
	}
	public List<User> getUserSelectlist() {
		return userSelectlist;
	}

	public void setUserSelectlist(List<User> userSelectlist) {
		this.userSelectlist = userSelectlist;
	}

}
