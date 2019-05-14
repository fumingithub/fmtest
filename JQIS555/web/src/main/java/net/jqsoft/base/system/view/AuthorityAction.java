/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.view;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.jqsoft.common.util.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.dao.support.ORMType;
import org.zcframework.core.dao.support.ibatis.IBatisModelSetup;
import org.zcframework.core.view.support.Result;
import org.zcframework.security.SpringSecurityUtils;
import org.zcframework.security.cache.ICacheRefreshService;
import org.zcframework.security.object.Loginer;
import com.alibaba.dubbo.common.utils.Assert;

import net.jqsoft.base.system.domain.Project;
import net.jqsoft.base.system.domain.Role;
import net.jqsoft.base.system.domain.User;
import net.jqsoft.base.system.service.IProjectService;
import net.jqsoft.base.system.service.IResourceService;
import net.jqsoft.base.system.service.IUserService;
import net.jqsoft.common.action.MyEntityAction;
/**
 * AuthorityAction
 * 
 * @author wangtao@jqsoft.net
 * @version 1.0
 */
public class AuthorityAction extends MyEntityAction<User, IUserService> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private IUserService userService;
	@Autowired
	private IProjectService projectService;
	@Autowired
	private ICacheRefreshService cacheRefreshService;
	@Override
	protected IUserService getEntityManager() {
		return userService;
	}
	/** 角色集合 */
    private List<User> AuthorityList = new ArrayList<User>();
	private String resourceIds="";
	private String selectUserId;
	String[] resourceId;
	private String  currentUserId;
	
	/**
	    * 根据当前用户查询当前用户所创建的角色
	    * @param
	    * @return
	    * @exception
	    * @author wangtao
	    * @createTime 2017-03-31 9:06 
	   */
	    public String list() {
	        Loginer loginer = SpringSecurityUtils.getCurrentUser();
//	        Map<String, Object> parms = this.getActionContext().getParameters();
//	        // 是系统管理员，查看所有角色
//	        Boolean isAdmin = CurrentUserUtil.isadmin();
//	        boolean isbmadmin = CurrentUserUtil.isbmadmin();
//	        if (isAdmin || isbmadmin) {
//	            parms.put("isadmin", new Object[]{new String("11")});
//	        }else {
//	            // 不是sysadmin，查询该角色创建的创色
//	            parms.put("creator", new Object[]{loginer.getId()});
//	        }
	        return super.list();
	    }

	/**
	    * @param
		 * @return
	    * @exception
	    * @author wangtao
	    * @createTime 2018-03-19 9:06
	     */
	    public String authority(){
//            IBatisModelSetup model = (IBatisModelSetup) getModelSetupFromRequest(ORMType.IBATIS);
//            model.setCountName("Organization.selectSpecialApplyOrgCount");
//            model.setSqlName("Organization.selectSpecialApplyOrg");
//            this.setFilter(model);
//            doPageEntity(model);
			Map<String, Object> params = getActionContext().getParameters();
			for (Map.Entry entry : params.entrySet()) {
				if (entry.getValue() instanceof String[]) {
					String[] vals = (String[]) entry.getValue();
					entry.setValue(new String[]{vals[0].toString().trim()});
				}
			}
			Loginer loginer = CurrentUserUtil.getCurrentUser();
			 currentUserId = loginer.getId().toString();
			IBatisModelSetup model = (IBatisModelSetup) getModelSetupFromRequest(ORMType.IBATIS);
			model.setCountName("User.queryAuthrityUserCount");
			model.setSqlName("User.queryAuthrityUser");
			this.setFilter(model);
			doPageEntity(model);
			return "authorityList";
	    }

		/**
		 * 项目授权
		 * @author wangtao
		 * @createTime 2018-01-18
		 */
		public String  saveServerAssign(){
			try {
				String projectId = this.getHttpServletRequest().getParameter("projectId");
				String[] selectUserId = this.getHttpServletRequest().getParameter("selectUserId").split(",");
				projectService.saveGrantResource(projectId, selectUserId);
				result = new Result(true, "用户授权成功！");
			}
			catch (Exception e){
				e.printStackTrace();
				result = new Result(false,e.getMessage());
				return RESULT;
			}
			return RESULT;
		}

		/**
		 * 项目授权
		 * @author wangtao
		 * @createTime 2018-01-18
		 */
		public String serverAssign(){
			 selectUserId = this.getHttpServletRequest().getParameter("selectUserId");
			currentUserId = this.getHttpServletRequest().getParameter("currentUserId");
			String loginRoleId = "";
			boolean isXDArea = false;
			Loginer loginer = SpringSecurityUtils.getCurrentUser();
			List<org.zcframework.security.model.Role> rolelist  = loginer.getRoles();
			for (org.zcframework.security.model.Role r : rolelist) {
					if("sysadmin".equals(r.getRoleName())) {
						isXDArea = true;
						break;
					}
				else{
						isXDArea = true;
					}
			}
			if(isXDArea){
				entitys = projectService.queryAll(null);
			}
			// 根据用户id查询资源
			List<Project> list = projectService.queryProAndServResourceByUserId(selectUserId);
			for (Project r : list) {
				//返回被选中的Id,不存在子资源且被选中
				if (r.getIsHavaChild().equals("0") && r.getIscheck().equals("1")) {
					resourceIds = resourceIds + r.getCode() + ",";
				}
			}
			return "assign";
		}
		/**
		 * 项目服务器授权
		 * @return
		 */
		public String auth() {
			String selectUserId = this.getHttpServletRequest().getParameter("selectUserId");
			resourceIds = "1,"+resourceIds;
			resourceId = resourceIds.split(",");
			if (resourceId != null && resourceId.length > 0) {
				projectService.updateAuth(selectUserId, resourceId);
				cacheRefreshService.refreshUrl();
				result = new Result(true, getText("entity.saved.update"));
			} else {
				result = new Result(false, "请至少选择一个资源项");
			}
			return RESULT;
		}
		
		public String getResourceIds() {
			return resourceIds;
		}
		public void setResourceIds(String resourceIds) {
			this.resourceIds = resourceIds;
		}
		public String getSelectUserId() {
			return selectUserId;
		}
		public void setSelectUserId(String selectUserId) {
			this.selectUserId = selectUserId;
		}
		public List<User> getAuthorityList() {
			return AuthorityList;
		}
		public void setAuthorityList(List<User> authorityList) {
			AuthorityList = authorityList;
		}
		public String getCurrentUserId() {
			return currentUserId;
		}

		public void setCurrentUserId(String currentUserId) {
			this.currentUserId = currentUserId;
		}
}
