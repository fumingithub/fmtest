package net.jqsoft.base.elevatorfm.domain;

import org.zcframework.core.domain.support.Entity;

import java.util.Date;

/**
 * 电梯管理实体类
 * @author: fumin
 * @date: 2018/12/5
 * @description:
 */
public class Elevator extends Entity<String> {
    /**
     * 电梯编号
     **/
    private String elevatorCode;
    /**
     * 小区名
     **/
    private String villageName;
    /**
     * 楼号
     **/
    private String floor;
    /**
     * 电梯号
     **/
    private String elevatorNumber;
    /**
     * 品牌
     **/
    private String brand;
    /**
     * 负责人ID
     **/
    private String principalor;
    /**
     * 负责人姓名
     **/
    private String principalorName;
    /**
     * 负责人电话
     **/
    private String principalorTel;
    /**
     * 状态 0-故障中 1-正常
     **/
    private String status;
    /**
     * 备注
     **/
    private String reference;
    /**
     *删除标识 1-是 0-否
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


    //------------------GETTER & SETTER--------------------


    public String getElevatorCode() {
        return elevatorCode;
    }

    public void setElevatorCode(String elevatorCode) {
        this.elevatorCode = elevatorCode;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getElevatorNumber() {
        return elevatorNumber;
    }

    public void setElevatorNumber(String elevatorNumber) {
        this.elevatorNumber = elevatorNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrincipalor() {
        return principalor;
    }

    public void setPrincipalor(String principalor) {
        this.principalor = principalor;
    }

    public String getPrincipalorName() {
        return principalorName;
    }

    public void setPrincipalorName(String principalorName) {
        this.principalorName = principalorName;
    }

    public String getPrincipalorTel() {
        return principalorTel;
    }

    public void setPrincipalorTel(String principalorTel) {
        this.principalorTel = principalorTel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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

    public Date getCreatorTime() {
        return creatorTime;
    }

    public void setCreatorTime(Date creatorTime) {
        this.creatorTime = creatorTime;
    }

    public Date getUpdatorTime() {
        return updatorTime;
    }

    public void setUpdatorTime(Date updatorTime) {
        this.updatorTime = updatorTime;
    }

    public Date getDeleterTime() {
        return deleterTime;
    }

    public void setDeleterTime(Date deleterTime) {
        this.deleterTime = deleterTime;
    }
}
