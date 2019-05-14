/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.repository.service;

import java.util.List;

import org.zcframework.core.service.IBaseEntityService;

import net.jqsoft.repository.domain.RepositoryKind;

/**
 * 知识库种类接口层
 * 
 * @author zhanghui
 * @createDate 2017年2月8日 上午10:30:51
 */
public interface IRepositoryKindService extends IBaseEntityService<RepositoryKind> {
	/**
	 * 
	 * @param code
	 *            种类编码
	 * @param string
	 * @param Name
	 *            种类名称
	 * @return 种类集合
	 */
	public List<RepositoryKind> queryAreaDictListByPCode(String code, String string, String Name);

	/**
	 * 
	 * @author zhanghui
	 * @createDate 2017年1月18日上午8:50:46
	 * @param id
	 *            知识库分类的主键
	 * @return 查询的知识库数据
	 */
	public List<RepositoryKind> queryKindById(String id);

	/**
	 * 根据父级机构编码获取机构List集合，用于zTree搜索
	 * 
	 * @param parentId
	 *            父级机构编码
	 * @param sysOrgName
	 *            机构名称过滤参数
	 * @param transferOrgId
	 *            需要调动的机构编码
	 * @param filterOrgId
	 *            根据当前用户所在的机构id过滤组织机构树
	 **/
	public List<RepositoryKind> queryKindListByParentId(String parentId, String sysOrgName, String transferOrgId,
			String filterOrgId);

}
