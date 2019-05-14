/**
 * Copyright (c) 2013-2015 jqsoft.net
 */
package net.jqsoft.repository.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.utils.date.UtilDateTime;
import org.zcframework.core.view.support.Result;
import org.zcframework.security.SpringSecurityUtils;

import net.jqsoft.common.action.MyEntityAction;
import net.jqsoft.common.util.CurrentUserUtil;
import net.jqsoft.repository.domain.RepositoryKind;
import net.jqsoft.repository.service.IRepositoryKindService;

/**
 * 知识库种类action
 * 
 * @author zhanghui
 * @createDate 2017年2月8日 上午10:25:51
 */
public class RepositoryKindAction extends MyEntityAction<RepositoryKind, IRepositoryKindService> {

	private static final long serialVersionUID = 1L;
	@Autowired
	private IRepositoryKindService repoKindService;

	/** 知识库种类集合 */
	private List<RepositoryKind> proKindList;

	@Override
	protected IRepositoryKindService getEntityManager() {
		// TODO Auto-generated method stub
		return repoKindService;
	}

	@Override
	public boolean needGenerateUUID() {
		return true;
	}

	/**
	 * edit方法重写 用户过滤
	 * 
	 * @author zhanghui
	 * @createTime 2017-1-17
	 * @return
	 */
	public String edit() {

		if (!CurrentUserUtil.isadmin()) {
			// 主键不为空，不能编辑
			if (entity.getId() != null) {
				result = new Result(false, "您不是管理员，不能编辑知识库分类！");
			} else {// 否则，不能添加
				result = new Result(false, "您不是管理员，不能添加知识库分类！");
			}

			return "result";
		}

		return super.edit();
	}

	/**
	 * delete方法重写 用户过滤
	 * 
	 * @author zh
	 * @createTime 2017-1-16
	 * @return DELETE
	 */
	public String delete() {

		if (!CurrentUserUtil.isadmin()) {
			result = new Result(false, "您不是管理员，不能维护知识库分类！");
			return RESULT;
		}

		return super.delete();
	}

	/**
	 * 重写保存方法
	 * 
	 * @author zhanghui
	 * @createDate 2017年1月18日上午9:40:50
	 * @return
	 */

	@Override
	public String save() {
		// 保存创建人创建时间
		if (entity.getId() == null) {
			entity.setCreateTime(UtilDateTime.nowDate());
			entity.setCreateName(SpringSecurityUtils.getCurrentUserName());
		} else {// 保存更新人、更新时间
			entity.setUpdateName(SpringSecurityUtils.getCurrentUserName());
			entity.setUpdateTime(UtilDateTime.nowDate());
		}
		return super.save();
	}

	/**
	 * 查看知识库种类
	 * 
	 * @author zhanghui
	 * @createDate 2017年1月18日上午8:47:02
	 * @return
	 */
	public String viewRepoKind() {
		proKindList = repoKindService.queryKindById(entity.getId());
		return "RepoKind";
	}

	/**
	 * 异步加载树
	 * 
	 * @author zhanghui
	 * @createDate 2017年1月18日上午8:40:50
	 * @return
	 */
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
		if (StringUtils.isEmpty(paramId) && StringUtils.isNotEmpty(currentOrgId) && !CurrentUserUtil.isadmin()) {
			filterOrgId = currentOrgId;
		}

		proKindList = this.repoKindService.queryKindListByParentId(paramId, sysOrgName, transferSysOrgIds, filterOrgId);
		return "proKindList";
	}

	public List<RepositoryKind> getProKindList() {
		return proKindList;
	}

	public void setProKindList(List<RepositoryKind> proKindList) {
		this.proKindList = proKindList;
	}

}
