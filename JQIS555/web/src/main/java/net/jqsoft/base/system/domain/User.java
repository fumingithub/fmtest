/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.domain;


import java.util.Date;
import org.zcframework.core.domain.support.Entity;



public class User extends Entity<String> {
    
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String  userName;
    /**
     * 密码
     */
    private String  passWord;
    /**
     * 真实姓名
     */
    private String  realName;
    /**
     * 手机号码
     */
    private String  mobiePhone;
    /**
     * 座机
     */
    private String  telePhone;
    /**
     * 岗位
     */
    private String  office;
    
    /**
     * 状态^0正常1锁定-1删除
     */
    private String  status;
    
    /**
     *确认选中标签1选中，0未选中
     */
    private String  ischeck;

	/**
     * 机构ID
     */
    private String  insId;
    
    /** 机构名称 */
    private String insName;
    
    /**
     * 关联行政区划主键
     */
    private String  areaId;
    /**
     * 关联行政区划名称
     */
    private String  areaName;
    /**
     * 用户类型^0农合机构1医疗机构
     */
    private String  userType;
    
    /**
     * 机构名称
     */
    private String hospitalName;
    
    /** 备注   */
    private String remark;
    
    /** 更新者 */
    private String  updator;
    
    /** 更新者姓名 */
    private String  updatorName;
    
    /** 更新时间     */
    private Integer  updateTime;
    
    /** 创建者     */
    private String  creator;
    
    /** 创建者姓名 */
    private String  creatorName;
    
    /** 创建时间  */
    private Date  createTime;
    
    /**是否区划管理员*/
    private String isAreaAdmin;
    
    
    /** 默认区划，用于核对时自动填写居住地址 */
	private String defaultAreaCode;
    
    
    public String  getUserName(){
        return userName;
    }

    public void setUserName(String userName){
        this.userName=userName;
    }
    public String  getPassWord(){
        return passWord;
    }

    public void setPassWord(String passWord){
        this.passWord=passWord;
    }
    public String  getRealName(){
        return realName;
    }

    public void setRealName(String realName){
        this.realName=realName;
    }
    public String  getMobiePhone(){
        return mobiePhone;
    }

    public void setMobiePhone(String mobiePhone){
        this.mobiePhone=mobiePhone;
    }
    public String  getTelePhone(){
        return telePhone;
    }

    public void setTelePhone(String telePhone){
        this.telePhone=telePhone;
    }
    public String  getOffice(){
        return office;
    }

    public void setOffice(String office){
        this.office=office;
    }
    public Integer  getUpdateTime(){
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime){
        this.updateTime=updateTime;
    }
    public String  getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status=status;
    }
    public String  getInsId(){
        return insId;
    }

    public void setInsId(String insId){
        this.insId=insId;
    }
    public String  getAreaId(){
        return areaId;
    }

    public void setAreaId(String areaId){
        this.areaId=areaId;
    }
    public String  getUserType(){
        return userType;
    }

    public void setUserType(String userType){
        this.userType=userType;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
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

    public String getIsAreaAdmin() {
        return isAreaAdmin;
    }

    public void setIsAreaAdmin(String isAreaAdmin) {
        this.isAreaAdmin = isAreaAdmin;
    }

    public String getInsName() {
        return insName;
    }

    public void setInsName(String insName) {
        this.insName = insName;
    }

	public String getDefaultAreaCode() {
		return defaultAreaCode;
	}

	public void setDefaultAreaCode(String defaultAreaCode) {
		this.defaultAreaCode = defaultAreaCode;
	}
	public String getIscheck() {
		return ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}
}


