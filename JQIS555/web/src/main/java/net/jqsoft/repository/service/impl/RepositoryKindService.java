/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.repository.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zcframework.core.dao.IEntityDao;
import org.zcframework.core.service.BaseEntityService;

import com.google.common.collect.Maps;

import net.jqsoft.repository.dao.RepositoryKindDao;
import net.jqsoft.repository.domain.RepositoryKind;
import net.jqsoft.repository.service.IRepositoryKindService;

/**
 * 知识库分类接口实现曾
 * 
 * @author zhanghui
 * @createDate 2017年2月8日 上午10:15:40
 */
@Service
public class RepositoryKindService extends BaseEntityService<RepositoryKind> implements IRepositoryKindService {
	@Autowired
	private RepositoryKindDao repKindDao;

	@Override
	protected IEntityDao<RepositoryKind> getDao() {
		return repKindDao;
	}

	@Override
	public List<RepositoryKind> queryAreaDictListByPCode(String code, String string, String Name) {
		Map<String, Object> param = Maps.newHashMap();
		param.put("code", code);
		param.put("Name", Name);
		return this.repKindDao.find("RepositoryKind.getAreaDictListByPCode", param);
	}

	@Override
	public List<RepositoryKind> queryKindListByParentId(String parentId, String sysOrgName, String transferOrgId,
			String filterOrgId) {
		Map<String, Object> param = Maps.newHashMap();
		param.put("parentId", parentId);
		param.put("sysOrgName", sysOrgName);
		param.put("transferOrgId", transferOrgId);
		param.put("filterOrgId", filterOrgId);
		return this.repKindDao.find("RepositoryKind.getSysOrgListByParentId", param);
	}

	@Override
	public List<RepositoryKind> queryKindById(String id) {
		return this.repKindDao.find(getEntityName() + ".queryKindById", id);
	}

}
