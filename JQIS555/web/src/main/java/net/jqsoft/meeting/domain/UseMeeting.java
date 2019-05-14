package net.jqsoft.meeting.domain;

import org.zcframework.core.domain.support.Entity;

import java.util.Date;

/**
 *
 * @author sijf
 * @create Time 2017/1/17
 *
 */
public class UseMeeting extends Entity<String> {

    /**会议室的ID*/
    private String roomId;

    /**会议室的名字*/
    private String roomName;

    /**使用者ID*/
    private String userId;

    /**使用者名字*/
    private String userName;

    /**使用日期*/
    private Date useDate;

    /**使用时长*/
    private String useTime;

    /**备注*/
    private String remark;

    /**使用状态*/
    private String inUse;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getUseDate() {
        return useDate;
    }

    public void setUseDate(Date useDate) {
        this.useDate = useDate;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getInUse() {
        return inUse;
    }

    public void setInUse(String inUse) {
        this.inUse = inUse;
    }
}
