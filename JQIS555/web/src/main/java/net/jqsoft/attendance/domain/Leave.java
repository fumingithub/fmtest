package net.jqsoft.attendance.domain;

import org.zcframework.core.domain.support.Entity;

import java.util.Date;

/**
 * 请假实体类
 * @author: liujun
 * @createTime: 2017-01-16
 */
public class Leave  extends Entity<String> {

    /** 用户ID */
    private String userId;

    /** 用户姓名 */
    private String userName;

    /** 请假开始时间 */
    private Date leaveStartTime;

    /** 请假结束时间 */
    private Date leaveEndTime;

    /** 请假原因 */
    private String leaveReason;

    /** 请假天数 */
    private Double leaveDays;

    /** 备注 */
    private String remark;

    /** 修改人ID */
    private String updator;

    /** 修改人 */
    private String updatorName;

    /** 创建人ID */
    private String creator;

    /** 创建人 */
    private String creatorName;

    /** 修改时间 */
    private Date updateTime;

    /** 创建时间 */
    private Date createTime;

    // =====================set and get=============

    public Date getLeaveStartTime() {
        return leaveStartTime;
    }

    public void setLeaveStartTime(Date leaveStartTime) {
        this.leaveStartTime = leaveStartTime;
    }

    public Date getLeaveEndTime() {
        return leaveEndTime;
    }

    public void setLeaveEndTime(Date leaveEndTime) {
        this.leaveEndTime = leaveEndTime;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public Double getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(Double leaveDays) {
        this.leaveDays = leaveDays;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
