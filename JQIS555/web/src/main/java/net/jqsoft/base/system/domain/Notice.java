package net.jqsoft.base.system.domain;

import java.util.Date;

import org.zcframework.core.domain.support.Entity;

/**
 * 公告管理实体类
 * 
 * @author guoxx
 * @createTime 2017/1/17
 */
public class Notice extends Entity<String> {

	private static final long serialVersionUID = 1L;

	/** 公告标题 */
	private String title;

	/** 副标题 */
	private String subTitle;

	/** 作者 */
	private String author;

	/** 发布状态 1发布 0 不发布 */
	private String status;

	/** 公告内容 */
	private String context;

	/** 是否置顶 1置顶 0不置顶 */
	private String isTop;

	/** 创建人ID */
	private String creator;

	/** 创建人姓名 */
	private String creatorName;

	/** 创建时间 */
	private Date createTime;

	/** 更新人ID */
	private String updator;

	/** 更新人姓名 */
	private String updatorName;

	/** 更新时间 */
	private Date updateTime;

	/** 发布时间 */
	private Date releaseTime;

	/** 备注 */
	private String remark;

	/** 发布人 */
	private String releasePerson;

	// ------getter和setter-----

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

	public String getUpdatorName() {
		return updatorName;
	}

	public void setUpdatorName(String updatorName) {
		this.updatorName = updatorName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReleasePerson() {
		return releasePerson;
	}

	public void setReleasePerson(String releasePerson) {
		this.releasePerson = releasePerson;
	}

}
