package net.jqsoft.base.meetingFm.domain;


import org.zcframework.core.domain.support.Entity;

/**
 * 会议室管理实体类
 * @author: fumin
 * @date: 2018/11/19
 */
public class MeetingFm extends Entity<String>{
    /** 会议室名称 **/
    private String name;

    /** 所在楼层 **/
    private String floor;

    /** 可容纳人数 **/
    private String people;

    /** 是否有投影仪  0-无，1-有**/
    private String projector;

    /** 会议室使用状态 1-使用，2-空闲 **/
    private String status;

    /** 是否启用 0-禁用，1-启用 **/
    private String enable;

    /** 禁用原因 **/
    private String disableReason;

    /** 备注 **/
    private String reference;

    /** 创建人 **/
    private String creator;

    /** 创建人 **/
    private String creatorId;

    /** 创建时间 **/
    private String creatorTime;

    /** 更新人 **/
    private String updator;

    /** 更新人 **/
    private String updatorId;

    /** 更新时间 **/
    private String updatorTime;

   //------------------GETTER & SETTER--------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getProjector() {
        return projector;
    }

    public void setProjector(String projector) {
        this.projector = projector;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getDisableReason() {
        return disableReason;
    }

    public void setDisableReason(String disableReason) {
        this.disableReason = disableReason;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCreator() { return creator; }

    public void setCreator(String creator) { this.creator = creator; }

    public String getCreatorId() { return creatorId; }

    public void setCreatorId(String creatorId) { this.creatorId = creatorId; }

    public String getCreatorTime() { return creatorTime; }

    public void setCreatorTime(String creatorTime) { this.creatorTime = creatorTime; }

    public String getUpdator() { return updator; }

    public void setUpdator(String updator) { this.updator = updator; }

    public String getUpdatorId() { return updatorId; }

    public void setUpdatorId(String updatorId) { this.updatorId = updatorId; }

    public String getUpdatorTime() { return updatorTime; }

    public void setUpdatorTime(String updatorTime) { this.updatorTime = updatorTime; }
}
