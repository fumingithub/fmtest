/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.repository.domain;

import java.util.Date;

import org.zcframework.core.domain.support.Entity;

/**
 * 知识库基本数据实体类
 * 
 * @author zhanghui
 * @createDate 2017年2月8日 上午10:30:04
 */
public class RepositoryBase extends Entity<String> {

	private static final long serialVersionUID = 1L;

	/** 主键id */
	private String id;

	/** 标题 */
	private String title;

	/** 内容 */
	private String context;

	/** 种类 */
	private String type;

	/** 访问量 */
	private Integer pv;

	/** 编码，种类表的主键 */
	private String code;

	/** 备注 */
	private String remark;

	/** 更新人ID */
	private String updator;

	/** 更新人name */
	private String updateName;

	/** 更新时间 */
	private Date updateTime;

	/** 创建人ID */
	private String creator;

	/** 创建人name */
	private String createName;

	/** 创建时间 */
	private Date createTime;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getPv() {
		return pv;
	}

	public void setPv(Integer pv) {
		this.pv = pv;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
