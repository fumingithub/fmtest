/**
 * Copyright (c) 2013-2015 jqsoft.net
 */
package net.jqsoft.base.system.action;

import net.jqsoft.base.system.domain.News;
import net.jqsoft.base.system.service.INewsService;
import net.jqsoft.common.action.MyEntityAction;
import net.jqsoft.common.util.SystemConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.utils.date.UtilDateTime;
import org.zcframework.core.view.support.Result;
import org.zcframework.security.SpringSecurityUtils;
import org.zcframework.security.object.Loginer;

import java.util.List;

/**
 * @author: zuoc
 * @date: 2015/5/25
 * @desc: 文章管理Action
 */
public class NewsAction extends MyEntityAction<News,INewsService> {
	
	private static final long serialVersionUID = 6399275036282478859L;
	
	private static Log LOG = LogFactory.getLog(NewsAction.class);
	
	@Autowired
    private INewsService newsService;
	
    /**
     * 0--没有修改增加权限，1--有修改增加权限
     */
    private String viewFlag = "0"; 
    private String areaId;

    /**
     * 重写list方法，带入权限
     */
	public String list() {
    	try {
    		// 是否显示新增/编辑等操作按钮
    		Loginer loginer = SpringSecurityUtils.getCurrentUser();
			List<org.zcframework.security.model.Role> rolelist  = loginer.getRoles();
			for (org.zcframework.security.model.Role r : rolelist) {
				if(r.getRoleName().equals(SystemConstants.SYSADMIN)) {
					viewFlag = "1";
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return super.list();
	}
	
	/**
	 * deleteNews 删除新闻
	 * @return
	 */
	public String deletenews(){
		try {
			entity = newsService.get(entity.getId());
			Loginer loginer = SpringSecurityUtils.getCurrentUser();
			// 如果当前登陆人是此新闻的发布者，则允许删除
			if (entity.getReleaseperson().equalsIgnoreCase(loginer.getUsername())) {
				newsService.deleteVisNews(entity.getId());
				result = new Result(true, getText("entity.deleted"));
			} else {
				result = new Result(false, "此新闻不是您录入的，您无法删除！");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			result = new Result(false, getText("entity.deleted.failed"));
		}
		return "result";
	}   
    
	/**
	 * releaseNews 发布新闻
	 * @return
	 */
	public String releasenews(){
		entity = newsService.get(entity.getId());
		if ("0".equals(entity.getStatus())) {
			result = new Result(false, "此新闻已经被发布！");
		} else {
			Loginer loginer = SpringSecurityUtils.getCurrentUser();
			if (!entity.getReleaseperson().equalsIgnoreCase(loginer.getUsername())) {
				result = new Result(false, "此新闻不是您录入的，您无法发布！");
			} else {
				newsService.releaseNews(entity.getId());
				result = new Result(true, "发布新闻成功！");					
			}
		}
		return "result";
	}   
    
	/**
	 * 跳转到新增或编辑页面
	 * @return
	 * @throws Exception 
	 */
	public String editpage() throws Exception{
		if (StringUtils.isNotEmpty(entity.getId())) {
			entity = newsService.get(entity.getId());
			Loginer loginer = SpringSecurityUtils.getCurrentUser();
			if (!entity.getReleaseperson().equalsIgnoreCase(loginer.getUsername())) {
				result = new Result(false, "此新闻不是您录入的，您无法编辑！");
				return "result";
			}
		}
		return "edit";
	}
	
	/**
	 * 跳转到查看页面
	 * @return
	 */
	public String viewpage(){
		if (StringUtils.isNotEmpty(entity.getId())) {
			entity = newsService.get(entity.getId());
		}
		return "view";
	}
	
	/**
	 * 执行新增或编辑操作
	 * @return
	 */
	@Override
	public String save() {
		//可以新增或编辑操作时，此新闻必是当前登录用户所录入的
		entity.setUpdator(SpringSecurityUtils.getCurrentUserName());
		entity.setUpdateTime(UtilDateTime.getCurrDateInt());
		entity.setReleaseperson(SpringSecurityUtils.getCurrentUserName());
		entity.setType("0");
		entity.setReadAreaId("0");
		if(entity.getStatus().equals("0")) {
			// 已发布状态
			entity.setReleasetime(UtilDateTime.getCurrDateInt());
		}else {
			//未发布状态
			entity.setReleasetime(null);
		}
		return super.save();
	}
    
	@Override
	public boolean needGenerateUUID() {
		return true;
	}
	
	@Override
    protected INewsService getEntityManager() {
        return newsService;
    }
	 
	public String getViewFlag() {
		return viewFlag;
	}

	public void setViewFlag(String viewFlag) {
		this.viewFlag = viewFlag;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
}
