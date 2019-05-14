/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.repository.domain;

import java.util.Date;

import org.zcframework.core.domain.support.Entity;

/**
 * 知识库种类实体类
 * 
 * @author zhanghui
 * @createDate 2017年2月8日 上午10:29:30
 */
public class RepositoryKind extends Entity<String> {

	private static final long serialVersionUID = 1L;

	/** 主键 */
	private String id;

	/** 知识库种类名称 */
	private String name;

	/** 种类编码 */
	private String code;

	/** 备注 */
	private String remark;

	/** 更新人ID */
	private String updator;

	/** 父级机构 */
	private String parentId;

	/** 状态 */
	private String status;

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

	/** zTree树根节点id */
	private String rootId;

	/** zTree树根节点Name */
	private String rootName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	public String getRootName() {
		return rootName;
	}

	public void setRootName(String rootName) {
		this.rootName = rootName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
