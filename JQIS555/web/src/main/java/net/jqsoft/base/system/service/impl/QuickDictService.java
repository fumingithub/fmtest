/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.base.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.jqsoft.base.system.dao.QuickDao;
import net.jqsoft.base.system.domain.Quick;
import net.jqsoft.base.system.service.IQuickDictService;

/**
 * @author xnn
 * @datetime 2016-04-22
 * @desc
 * @see
 */
@Service
public class QuickDictService implements IQuickDictService {
	@Autowired
	public QuickDao quickDao;

	@Override
	public List<Quick> search(String input, String areaId, String dictCode) {
		return quickDao.search(input, areaId, dictCode);
	}

	@Override
	public List<Quick> searchByCode(String itemCode, String areaId, String dictCode) {
		return quickDao.searchByCode(itemCode, areaId, dictCode);
	}

	@Override
	public List<Quick> search2(String areaId, String dictCode) {
		return quickDao.searchDict2(areaId, dictCode);
	}

	@Override
	public List<Quick> serachAreaDictAll(String input, String areaId, String dictCode) {
		return quickDao.serachAreaDictAll(input, areaId, dictCode);
	}

	@Override
	public List<Quick> queryOrg(String input, String insId) {
		return quickDao.queryOrg(input, insId);
	}

	@Override
	public List<Quick> RepoKind(String code) {
		// TODO Auto-generated method stub
		return quickDao.RepoKind(code);
	}

	@Override
	public List<Quick> searchRepoKind(String codeOrName) {
		// TODO Auto-generated method stub
		return quickDao.searchRepoKind(codeOrName);
	}

}
