/**
 * Copyright (c) 2013-2015 jqsoft.net
 */
package net.jqsoft.base.system.action;

import net.jqsoft.base.system.domain.Resources;
import net.jqsoft.base.system.service.IResourceService;
import net.jqsoft.common.action.MyEntityAction;
import net.jqsoft.common.util.SystemConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.zcframework.core.view.support.Result;
import org.zcframework.security.SpringSecurityUtils;
import org.zcframework.security.cache.ICacheRefreshService;
import org.zcframework.security.object.Loginer;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * @author: zuoc
 * @date: 2015-06-17
 * @desc: 资源管理Action
 */
public class ResourceAction extends
		MyEntityAction<Resources, IResourceService> {

	private static final long serialVersionUID = -8302955130613307001L;

	private String type; // 0: 添加子资源 1：添加资源或修改资源

	@Autowired
	private IResourceService resourceService;

	@Autowired
	private ICacheRefreshService cacheRefreshService;

	private Resources parent;
	private String parentId;
	String[] resourceId;
	String resourceIds="";
	private String roleId;

    /** zTree树-资源编码 */
    private String ztResourceId;

    /** 资源List集合 */
    private List<Resources> resourceList;

	/** 跳转页面状态：1-新增，2-编辑 */
	private String editType;
	
	/**
	 * 菜单异步加载  显示子菜单
	 */
	public String showchild() {
		entitys = this.resourceService.queryResourceByPid(parentId);
		return "showchild";
	}

	/**
	 * 重写编辑和新增方法，获取父资源
	 * 
	 * @return
	 */
	public String edit() {
		if (parentId != null)
			parent = resourceService.get(parentId);
		return super.edit();
	}
	
	@Override
	public String save() {
		if(entity != null) {
			// 验证code的唯一性
			if (StringUtils.isNotEmpty(entity.getCode())) {
				Resources res = this.resourceService.getResourcesByCode(entity.getCode(),entity.getId());
				if (res != null) {
					result = new Result(false, "该资源编码已经存在");
					return RESULT;
				}
			}
		}
		return super.save();
	}

	/**
	 * 查找资源
	 * 
	 * @return
	 */
	public String lookup() {
		super.list();
		return "lookup";
	}

	/**
	 * 跳转给角色分配资源的页面
	 * 
	 * @return
	 */
	public String assign() {
		Assert.hasText(roleId);
		String loginRoleId = "";
		// 管理员获得所有资源，非管理员取登陆账号所拥有的资源
		boolean isXDArea = false;
		Loginer loginer = SpringSecurityUtils.getCurrentUser();
		List<org.zcframework.security.model.Role> rolelist  = loginer.getRoles();
		for (org.zcframework.security.model.Role r : rolelist) {
			if(r.getRoleName().equals(SystemConstants.SYSADMIN)) {
				isXDArea = true;
				break;
			}
			loginRoleId += ",'"+r.getId()+"'";
		}
		if(isXDArea)
			entitys = resourceService.queryAll(null);
		else {
			loginRoleId = loginRoleId.substring(1);
			entitys = resourceService.queryAll(null);
		}
		// 根据角色id查询资源
		List<Resources> list = resourceService.queryResourceByRoleId(roleId);
		for (Resources r : list) {
			//返回被选中的Id,不存在子资源且被选中
			if (r.getIsHavaChild()==0 && r.getIsCheck() == 1) {
				resourceIds = resourceIds + r.getId() + ",";
			}
		}
		return "assign";
	}

	/**
	 * 分配权限
	 * @return
	 */
	public String auth() {
		Assert.hasText(roleId);
		resourceIds = "1,"+resourceIds;
		resourceId = resourceIds.split(",");
		if (resourceId != null && resourceId.length > 0) {
			resourceService.updateAuth(roleId, resourceId);
			cacheRefreshService.refreshUrl();
			result = new Result(true, getText("entity.saved.update"));
		} else {
			result = new Result(false, "请至少选择一个资源项");
		}
		return RESULT;
	}
	
	/**
	 * 删除资源，与角色的关联关系
	 * @return
	 */
    public String deleteresource() {
    	resourceService.deleteResource(entity.getId());
    	result = new Result(true, "删除成功！");
    	return "result";
    }

    /**
     * 异步加载，用于zTree展开某个父节点的下一级所有子节点
     * @return java.lang.String
     * @author jinliang
     * @create 2016-12-27 16:04:28
     **/
    public String ajaxQueryResourceForZTree() {
        String paramId = entity.getId(); // 资源编码
        String resourceName = this.getHttpServletRequest().getParameter("resourceName"); // 资源名称过滤信息

        if (StringUtils.isNotEmpty(resourceName)) {
            try {
                resourceName = URLDecoder.decode(URLDecoder.decode(resourceName, "UTF-8"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        resourceList = this.resourceService.queryResourceListByParentCode(paramId, resourceName);
        return "resource_result";
    }

    /**
     * 点击左侧树节点加载右侧列表数据
     * @return java.lang.String
     * @author jinliang
     * @create 2016-12-27 15:30:01
     **/
    public String resourceSonList() {
        Map<String, Object> params = getActionContext().getParameters();
        params.put("ztResourceId", new Object[] {ztResourceId});
        super.list();
        return "sonList";
    }

	@Override
	protected IResourceService getEntityManager() {
		return resourceService;
	}

	public boolean needGenerateUUID() {
		return true;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Resources getParent() {
		return parent;
	}

	public void setParent(Resources parent) {
		this.parent = parent;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setResourceId(String[] resourceId) {
		this.resourceId = resourceId;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

    public String getZtResourceId() {
        return ztResourceId;
    }

    public void setZtResourceId(String ztResourceId) {
        this.ztResourceId = ztResourceId;
    }

    public List<Resources> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<Resources> resourceList) {
        this.resourceList = resourceList;
    }

    public String getEditType() {
        return editType;
    }

    public void setEditType(String editType) {
        this.editType = editType;
    }
}
