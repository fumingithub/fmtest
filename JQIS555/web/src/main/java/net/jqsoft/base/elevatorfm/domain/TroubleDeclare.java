package net.jqsoft.base.elevatorfm.domain;

import org.zcframework.core.domain.support.Entity;

import java.util.Date;

/**电梯故障申报信息实体类
 * @author: Administrator
 * @date: 2018/12/17
 * @description:
 */
public class TroubleDeclare extends Entity<String>{

    /**
     * 电梯ID
     */
    private String elevatorId;

    /**
     * 申报时间
     */
    private Date declareTime;
    /**
     * 申报人
     */
    private String declareName;
    /**
     * 申报电话
     */
    private String declareTel;
    /**
     * 故障状态
     */
    private String troubleStatus;
    /**
     * 故障类型
     */
    private String troubleType;
    /**
     * 故障描述
     */
    private String troubleDescribe;
    /**
     * 故障确认人ID
     */
    private String confirmor;
    /**
     * 故障确认人姓名
     */
    private String confirmorName;
    /**
     * 故障确认时间
     */
    private Date confirmorTime;
    /**
     * 故障确认内容
     */
    private String confirmorContent;
    /**
     * 技师ID
     */
    private String technicianId;
    /**
     * 技师Name
     */
    private String technicianName;
    /**
     * 技师电话
     */
    private String technicianTel;

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

    //-------------------- 冗余字段 --------------------------
    /**
     * 登录用户真实姓名
     */
    private String userName;
    /**
     * 登录用户Id
     */
    private String userId;
    /**
     * 登录用户手机号码
     */
    private String mobiePhone;
    /**
     * 登录用户座机号码
     */
    private String telePhone;

    //------------------GETTER & SETTER------------------------

    public String getElevatorId() {
        return elevatorId;
    }

    public void setElevatorId(String elevatorId) {
        this.elevatorId = elevatorId;
    }

    public Date getDeclareTime() {
        return declareTime;
    }

    public void setDeclareTime(Date declareTime) {
        this.declareTime = declareTime;
    }

    public String getDeclareName() {
        return declareName;
    }

    public void setDeclareName(String declareName) {
        this.declareName = declareName;
    }

    public String getDeclareTel() {
        return declareTel;
    }

    public void setDeclareTel(String declareTel) {
        this.declareTel = declareTel;
    }

    public String getTroubleStatus() {
        return troubleStatus;
    }

    public void setTroubleStatus(String troubleStatus) {
        this.troubleStatus = troubleStatus;
    }

    public String getTroubleType() {
        return troubleType;
    }

    public void setTroubleType(String troubleType) {
        this.troubleType = troubleType;
    }

    public String getTroubleDescribe() {
        return troubleDescribe;
    }

    public void setTroubleDescribe(String troubleDescribe) {
        this.troubleDescribe = troubleDescribe;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTelePhone() {
        return telePhone;
    }

    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobiePhone() {
        return mobiePhone;
    }

    public void setMobiePhone(String mobiePhone) {
        this.mobiePhone = mobiePhone;
    }
}
