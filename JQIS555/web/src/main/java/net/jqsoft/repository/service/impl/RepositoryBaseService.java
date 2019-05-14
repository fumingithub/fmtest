/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.repository.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zcframework.core.dao.IEntityDao;
import org.zcframework.core.service.BaseEntityService;

import net.jqsoft.repository.dao.RepositoryBaseDao;
import net.jqsoft.repository.dao.RepositoryBaseHotsDao;
import net.jqsoft.repository.domain.RepositoryBase;
import net.jqsoft.repository.domain.RepositoryBaseHots;
import net.jqsoft.repository.service.IRepositoryBaseService;

/**
 * 知识库基本数据接口实现层
 * 
 * @author zhanghui
 * @createDate 2017年2月8日 上午10:14:28
 */
@Service
public class RepositoryBaseService extends BaseEntityService<RepositoryBase> implements IRepositoryBaseService {
	@Autowired
	private RepositoryBaseDao repBaseDao;
	@Autowired
	private RepositoryBaseHotsDao repoHotsDao;

	@Override
	protected IEntityDao<RepositoryBase> getDao() {
		return repBaseDao;
	}

	@Override
	public List<RepositoryBase> queryBaseById(String id) {
		return repBaseDao.find(getEntityName() + ".get", id);
	}

	@Override
	public int findPvById(String id) {
		List<RepositoryBase> RepoList = new ArrayList<RepositoryBase>();
		RepoList = repBaseDao.find(getEntityName() + ".get", id);
		int pv = RepoList.get(0).getPv();
		return pv;
	}

	@Override
	public void updatePvById(Map map) {
		repBaseDao.getSqlMapClientTemplate().update(getEntityName() + ".updatePvById", map);
	}

	@Override
	public void insertHotSpot(RepositoryBaseHots reHots) {
		repoHotsDao.insert("RepositoryHot.insert", reHots);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String findKindIdByUserId(String userId) {
		List<RepositoryBaseHots> hotsList = new ArrayList<RepositoryBaseHots>();
		hotsList = repoHotsDao.find("RepositoryHot.findOne", userId);
		String kindId = hotsList.get(0).getKindId();
		return kindId;
	}

	@Override
	public List<RepositoryBase> queryHotsByType(String type) {
		return repBaseDao.find(getEntityName() + ".queryHotsByType", type);
	}

}
