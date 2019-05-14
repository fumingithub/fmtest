/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.base.system.service;

import java.util.List;

import net.jqsoft.base.system.domain.Quick;

/**
 * @author xnn
 * @datetime 2016-04-22
 * @desc
 * @see
 */
public interface IQuickDictService {

	List<Quick> search(String input, String areaId, String dictCode);

	List<Quick> searchByCode(String itemCode, String areaId, String dictCode);

	// 两级字典
	List<Quick> search2(String areaId, String dictCode);

	List<Quick> serachAreaDictAll(String input, String areaId, String dictCode);

	List<Quick> queryOrg(String input, String insId);

	// 知识库种类快捷查询
	List<Quick> RepoKind(String code);

	List<Quick> searchRepoKind(String codeOrName);
}
