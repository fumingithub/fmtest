/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.repository.domain;

import org.zcframework.core.domain.support.Entity;

/**
 * 知识库热点推荐实体类
 * 
 * @author zhanghui
 * @createDate 2017年1月19日 下午5:31:39
 */
public class RepositoryBaseHots extends Entity<String> {
	private static final long serialVersionUID = 1L;

	/** 主键 */
	private String id;

	/** 用户主键 */
	private String userId;

	/** 知识库种类主键 */
	private String kindId;

	/** 访问量 */
	private String hotsPv;

	/** 文章标题 */
	private String title;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getKindId() {
		return kindId;
	}

	public void setKindId(String kindId) {
		this.kindId = kindId;
	}

	public String getHotsPv() {
		return hotsPv;
	}

	public void setHotsPv(String hotsPv) {
		this.hotsPv = hotsPv;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
