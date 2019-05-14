/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.repository.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zcframework.core.dao.IEntityDao;
import org.zcframework.core.service.BaseEntityService;
import org.zcframework.core.service.IBaseEntityService;

import net.jqsoft.repository.dao.RepositoryBaseHotsDao;
import net.jqsoft.repository.domain.RepositoryBaseHots;

/**
 * 知识库热点接口实现曾层
 * 
 * @author zhanghui
 * @createDate 2017年1月20日 下午2:22:37
 */
@Service
public class RepositoryBaseHotsService extends BaseEntityService<RepositoryBaseHots>
		implements IBaseEntityService<RepositoryBaseHots> {
	@Autowired
	private RepositoryBaseHotsDao repoDapo;

	@Override
	protected IEntityDao<RepositoryBaseHots> getDao() {
		return repoDapo;
	}

}
