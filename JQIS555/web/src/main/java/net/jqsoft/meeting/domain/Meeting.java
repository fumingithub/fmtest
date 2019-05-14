/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.meeting.domain;

import java.util.Date;

import org.zcframework.core.domain.support.Entity;

/**
* @author jucj
* @datetime 2017-1-16
* @desc
* @see
*/
public class Meeting extends Entity<String>{
	
	/**会议室名称*/
	private String name;
	
	/**会议室位置*/
	private String location;
	
	/**会议室大小*/
	private String meetingSize;

	/**是否有麦克风：00--没有，01--有*/
	private String haveMicrophone;
	
	/**是否有投影仪：00--没有，01--有*/
	private String haveProjector;
	
	/**是否有WIFI：00--没有，01--有*/
	private String haveWifi;
	
	/**是否有电脑：00--没有，00--有*/
	private String haveComputer;
	
	/**会议室类型：01--圆桌型、02--课桌型、03--U型、04--椭圆型、05--长方型、06--其他*/
	private String type;
	
	/**更新人ID*/
	private String updator;
	
	/**更新人名字*/
	private String updatorName;
	
	/**更新时间*/
	private Date updateTime;
	
	/**创建人ID*/
	private String creator;
	
	/**创建人名字*/
	private String creatorName;
	
	/**创建时间*/
	private Date createTime;
	
	/**备注*/
	private String remark ;
	
	/**是否被使用；00--否  01--是*/
	private String inUse;
	
	/** 状态:1-启用;0-禁用 */
	private String status;

	/**使用者名字*/
	private String userName;

	/**使用时间*/
	private Date useTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMeetingSize() {
		return meetingSize;
	}

	public void setMeetingSize(String meetingSize) {
		this.meetingSize = meetingSize;
	}

	public String getHaveMicrophone() {
		return haveMicrophone;
	}

	public void setHaveMicrophone(String haveMicrophone) {
		this.haveMicrophone = haveMicrophone;
	}

	public String getHaveProjector() {
		return haveProjector;
	}

	public void setHaveProjector(String haveProjector) {
		this.haveProjector = haveProjector;
	}

	public String getHaveWifi() {
		return haveWifi;
	}

	public void setHaveWifi(String haveWifi) {
		this.haveWifi = haveWifi;
	}

	public String getHaveComputer() {
		return haveComputer;
	}

	public void setHaveComputer(String haveComputer) {
		this.haveComputer = haveComputer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getInUse() {
		return inUse;
	}

	public void setInUse(String inUse) {
		this.inUse = inUse;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}
}