/**
 * Copyright (c) 2013-2015 jqsoft.net
 */
package net.jqsoft.repository.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.utils.date.UtilDateTime;
import org.zcframework.security.SpringSecurityUtils;
import org.zcframework.security.object.Loginer;

import net.jqsoft.common.action.MyEntityAction;
import net.jqsoft.common.util.CurrentUserUtil;
import net.jqsoft.repository.domain.RepositoryBase;
import net.jqsoft.repository.domain.RepositoryBaseHots;
import net.jqsoft.repository.service.IRepositoryBaseService;

/**
 * 知识库基本数据action层
 * 
 * @author zhanghui
 * @createDate 2017年2月8日 上午10:19:15
 */
public class RepositoryBaseAction extends MyEntityAction<RepositoryBase, IRepositoryBaseService> {
	private static final long serialVersionUID = 1L;
	@Autowired
	private IRepositoryBaseService repoBaseService;

	/** zTree树-编码 */
	private String ztSysOrgId;

	/** 知识库数据集合 */
	private List<RepositoryBase> repoBaseList;

	@Override
	protected IRepositoryBaseService getEntityManager() {
		return repoBaseService;
	}

	@Override
	public boolean needGenerateUUID() {
		return true;
	}

	/**
	 * 右列表数据加载
	 * 
	 * @author zhanghui
	 * @createDate 2017年1月18日下午1:58:36
	 * @return
	 */
	public String repoBaseSonList() {
		Map<String, Object> params = getActionContext().getParameters();
		params.put("ztSysOrgId", new Object[] { ztSysOrgId });
		super.list();

		return "sonList";

	}

	/**
	 * 查看知识库数据 查看一次增加一次访问量
	 * 
	 * @author zhanghui
	 * @createDate 2017年1月18日下午4:24:15
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String viewRepoBase() {
		String id = entity.getId();
		// 获取查看的信息
		repoBaseList = repoBaseService.queryBaseById(id);

		// 封装浏览记录对象
		// 获取查看知识库的分类编码
		String KindId = repoBaseList.get(0).getType();

		// 获取登陆对象,获取ID
		Loginer loginer = SpringSecurityUtils.getCurrentUser();
		String userId = loginer.getId().toString();

		RepositoryBaseHots reHots = new RepositoryBaseHots();
		reHots.setKindId(KindId);
		reHots.setUserId(userId);

		// 生成主键ID
		reHots.setId(UUID.randomUUID().toString());

		// 根据ID获取访问量
		int pv = repoBaseService.findPvById(id);
		pv = pv + 1;
		Map map = new HashMap();
		map.put("pv", pv);
		map.put("id", id);

		// 每次查看更新一次访问量
		repoBaseService.updatePvById(map);

		// 插入一次浏览记录
		repoBaseService.insertHotSpot(reHots);
		return "RepoBase";
	}

	/**
	 * 热点查询
	 * 
	 * @author zhanghui
	 * @createDate 2017年1月20日上午11:21:21
	 * @return
	 */
	public String viewHots() {
		// 1.根据用户ID查询浏览的分类；
		String userId = (String) CurrentUserUtil.getCurrentUser().getId();
		String kindId = repoBaseService.findKindIdByUserId(userId);

		// 2.根据分类向用户推荐种类与文章
		entitys = repoBaseService.queryHotsByType(kindId);
		return "viewHots";

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
		if (entity.getId() != null) {
			entity.setCreateTime(UtilDateTime.nowDate());
			entity.setCreateName(SpringSecurityUtils.getCurrentUserName());
		} else {
			entity.setUpdateName(SpringSecurityUtils.getCurrentUserName());
			entity.setUpdateTime(UtilDateTime.nowDate());
		}
		return super.save();
	}

	public String getZtSysOrgId() {
		return ztSysOrgId;
	}

	public void setZtSysOrgId(String ztSysOrgId) {
		this.ztSysOrgId = ztSysOrgId;
	}

	public List<RepositoryBase> getRepoBaseList() {
		return repoBaseList;
	}

	public void setRepoBaseList(List<RepositoryBase> repoBaseList) {
		repoBaseList = repoBaseList;
	}

}
