/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.base.system.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.zcframework.core.view.support.BaseAction;

import net.jqsoft.base.system.domain.Quick;
import net.jqsoft.base.system.service.IQuickDictService;

/**
 * @author xnn@jqsoft.net
 * @datetime 2016-04-22
 * @desc 字典快捷查询
 * @see
 */
public class QuickDictAction extends BaseAction {
	private List<Quick> quicks = new ArrayList<Quick>();

	private String q;// 搜索条件
	private String area;
	private String code;// 回显使用 value的值
	private String dictCode;
	private String insId;

	@Autowired
	private IQuickDictService quickDictService;

	public String serachAreaDictAll() {
		quicks = quickDictService.serachAreaDictAll(q, area, dictCode);
		return SUCCESS;
	}

	/**
	 * 知识库种类快捷查询
	 * 
	 * @return
	 */
	public String RepoKind() {
		if (!StringUtils.isEmpty(code))// 回显使用
			quicks = quickDictService.RepoKind(code);
		if (q != null && !"".equals(q))
			quicks = quickDictService.searchRepoKind(q);
		return SUCCESS;

	}

	public String queryOrg() {
		quicks = quickDictService.queryOrg(q, insId);
		return SUCCESS;
	}

	/**
	 * @author xnn
	 * @date 2016/04/22
	 * @desc quick查询药品数据
	 */
	public String singledis() {
		Assert.notNull(area);
		if (StringUtils.isNotEmpty(code))// 用于回显
			quicks = quickDictService.searchByCode(code, area, dictCode);
		if (q != null && !"".equals(q))
			quicks = this.quickDictService.search(q, area, dictCode);
		return SUCCESS;
	}

	/**
	 * @author xnn
	 * @date 2016/04/22
	 * @desc 针对两级的字典进行数据的获取，返回到前段组装数据
	 */
	public String twoLevel() {
		Assert.notNull(area);
		quicks = quickDictService.search2(area, dictCode);
		return SUCCESS;
	}

	public void setQuicks(List<Quick> quicks) {
		this.quicks = quicks;
	}

	public List<Quick> getQuicks() {
		return quicks;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}

}
