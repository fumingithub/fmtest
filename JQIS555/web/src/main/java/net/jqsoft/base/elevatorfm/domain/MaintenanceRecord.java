package net.jqsoft.base.elevatorfm.domain;

import org.zcframework.core.domain.support.Entity;

import java.util.Date;

/**
 * 维保记录实体类
 * @author: Administrator
 * @date: 2018/12/17
 * @description:
 */
public class MaintenanceRecord extends Entity<String> {
    /**电梯ID**/
    private String elevatorId;
    /**
     * 电梯编号
     **/
    private String elevatorCode;
    /**
     * 电梯故障ID
     **/
    private String elevatorTroubleId;

    /**
     * 维保人Id
     **/
    private String technicianId;
    /**
     * 维保人姓名
     **/
    private String technicianName;
    /**
     * 维保人联系电话
     **/
    private String technicianTel;
    /**
     * 维保时间
     **/
    private Date maintenanceorTime;
    /**
     * 维保类型 1-维修 2-保养
     **/
    private String maintenanceType;
    /**
     * 维保状态 1-维保中 2-已完成
     **/
    private String maintenanceStatus;
    /**
     * 其他维保人
     **/
    private String maintenanceorOther;
    /**
     * 维保内容
     **/
    private String maintenanceorContent;
    /**
     * 维保确认人Id
     **/
    private String confirmor;
    /**
     * 维保确认人姓名
     **/
    private String confirmorName;
    /**
     * 维保确认时间
     **/
    private Date confirmorTime;
    /**
     * 维保确认内容
     **/
    private String confirmorContent;
    /**
     *删除标识
     */
    private String isDel;
    /**
     * 创建人ID
     **/
    private String creator;
    /**
     * 创建人姓名
     **/
    private String creatorName;
    /**
     * 创建时间
     **/
    private Date creatorTime;
    /**
     * 更新人ID
     **/
    private String updator;
    /**
     * 更新人姓名
     **/
    private String updatorName;
    /**
     * 更新时间
     **/
    private Date updatorTime;
    /**
     * 删除人ID
     **/
    private String deleter;
    /**
     * 删除人姓名
     **/
    private String deleterName;
    /**
     * 删除时间
     **/
    private Date deleterTime;

    //------------------------GETTER OR SETTER-------------------------


    public String getElevatorId() {
        return elevatorId;
    }

    public void setElevatorId(String elevatorId) {
        this.elevatorId = elevatorId;
    }

    public String getElevatorCode() {
        return elevatorCode;
    }

    public void setElevatorCode(String elevatorCode) {
        this.elevatorCode = elevatorCode;
    }

    public String getElevatorTroubleId() {
        return elevatorTroubleId;
    }

    public void setElevatorTroubleId(String elevatorTroubleId) {
        this.elevatorTroubleId = elevatorTroubleId;
    }

    public String getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(String maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

    public String getMaintenanceStatus() {
        return maintenanceStatus;
    }

    public void setMaintenanceStatus(String maintenanceStatus) {
        this.maintenanceStatus = maintenanceStatus;
    }

    public String getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(String technicianId) {
        this.technicianId = technicianId;
    }

    public String getTechnicianName() {
        return technicianName;
    }

    public void setTechnicianName(String technicianName) {
        this.technicianName = technicianName;
    }

    public String getTechnicianTel() {
        return technicianTel;
    }

    public void setTechnicianTel(String technicianTel) {
        this.technicianTel = technicianTel;
    }

    public Date getMaintenanceorTime() {
        return maintenanceorTime;
    }

    public void setMaintenanceorTime(Date maintenanceorTime) {
        this.maintenanceorTime = maintenanceorTime;
    }

    public String getMaintenanceorOther() {
        return maintenanceorOther;
    }

    public void setMaintenanceorOther(String maintenanceorOther) {
        this.maintenanceorOther = maintenanceorOther;
    }

    public String getMaintenanceorContent() {
        return maintenanceorContent;
    }

    public void setMaintenanceorContent(String maintenanceorContent) {
        this.maintenanceorContent = maintenanceorContent;
    }

    public String getConfirmor() {
        return confirmor;
    }

    public void setConfirmor(String confirmor) {
        this.confirmor = confirmor;
    }

    public String getConfirmorName() {
        return confirmorName;
    }

    public void setConfirmorName(String confirmorName) {
        this.confirmorName = confirmorName;
    }

    public Date getConfirmorTime() {
        return confirmorTime;
    }

    public void setConfirmorTime(Date confirmorTime) {
        this.confirmorTime = confirmorTime;
    }

    public String getConfirmorContent() {
        return confirmorContent;
    }

    public void setConfirmorContent(String confirmorContent) {
        this.confirmorContent = confirmorContent;
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
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

    public Date getCreatorTime() {
        return creatorTime;
    }

    public void setCreatorTime(Date creatorTime) {
        this.creatorTime = creatorTime;
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

    public Date getUpdatorTime() {
        return updatorTime;
    }

    public void setUpdatorTime(Date updatorTime) {
        this.updatorTime = updatorTime;
    }

    public String getDeleter() {
        return deleter;
    }

    public void setDeleter(String deleter) {
        this.deleter = deleter;
    }

    public String getDeleterName() {
        return deleterName;
    }

    public void setDeleterName(String deleterName) {
        this.deleterName = deleterName;
    }

    public Date getDeleterTime() {
        return deleterTime;
    }

    public void setDeleterTime(Date deleterTime) {
        this.deleterTime = deleterTime;
    }
}
