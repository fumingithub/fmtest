/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.attendance.domain;

import org.zcframework.core.domain.support.Entity;

import java.util.Date;

/**
 * 
 * @author mapp 
 * @createTime 2017-01-16
 */
public class WorkRecord extends Entity<String> {

    /**用户ID*/
    private String userId;

    /**签到时间*/
    private Date signInTime;

    /**签退时间*/
    private Date signOutTime;

    /**工作状态 01：已签到，02：已签退，03：迟到，04：旷工，05：请假，06：早退，07：未签到；99：异常*/
    private String workStatus;

    /**备注*/
    private String remark;

    /**更新人ID*/
    private String updator;

    /**更新人name*/
    private String updatorName;

    /**更新时间*/
    private Date updateTime;

    /**创建人ID*/
    private String creator;

    /**创建人name*/
    private String creatorName;

    /**创建时间*/
    private Date createTime;

    /**加班时长*/
    private Double overTime;

    /**加班时长*/
    private Integer realOverTime;

    /**用户名*/
    private String realName;

    /**当前日期*/
    private Date currentTime;

    /**是否统计过 01:统计过 02：没统计过*/
    private String isResert;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(Date signInTime) {
        this.signInTime = signInTime;
    }

    public Date getSignOutTime() {
        return signOutTime;
    }

    public void setSignOutTime(Date signOutTime) {
        this.signOutTime = signOutTime;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
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

    public Double getOverTime() {
        return overTime;
    }

    public void setOverTime(Double overTime) {
        this.overTime = overTime;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

    public String getIsResert() {
        return isResert;
    }

    public void setIsResert(String isResert) {
        this.isResert = isResert;
    }

    public Integer getRealOverTime() {
        return realOverTime;
    }

    public void setRealOverTime(Integer realOverTime) {
        this.realOverTime = realOverTime;
    }
}
