/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.base.system.domain;

import org.zcframework.core.domain.support.Entity;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
* @author 
* @datetime 2017-10-16
* @desc
* @see
*/
public class DatabaseInfoManagement extends Entity<String> {
	
	
	/**数据库IP*/
    private String  databaseIp;
	
	/**设备ID	*/
    private String  equipmentId;
	
	/**数据库端口*/
    private String  databasePort;
	
	/**数据库sid*/
    private String  databaseSid;
	
	/**数据库用户名*/
    private String  userName;
	
	/**数据库密码*/
    private String  password;
    
    /**删除标记   "1"是 :已删除 "0" 否:未删除 */
    private String  isDel;
	
	/**数据结构升级  "01" 是 "02"否 */
    private String  dataStrucUpdate;
	
    /**数据校验升级  "01" 是 "02"否 */
    private String  checkIsornotUpdate;
    
    /**etl是否升级  "01" 是 "02"否 */
    private String  etlupdate;
	
	/**升级完成时间*/
    private Date  compliteTime;
	
	/**备注*/
    private String  remarks;
	
	/**创建者ID*/
    private String  creator;
	
	/**创建人*/
    private String  createName;
	
	/**创建时间*/
    private Date  createTime;
	
	/**更新者ID*/
    private String  updator;
	
	/**更新者*/
    private String  updatorName;
	
	/**更新时间*/
    private Date  updateTime;
    
    //------------------GETTER & SETTER--------------------
    public String  getDatabaseIp(){
        return databaseIp;
    }
    public void setDatabaseIp(String databaseIp){
        this.databaseIp=databaseIp;
    }
    public String  getEquipmentId(){
        return equipmentId;
    }
    public void setEquipmentId(String equipmentId){
        this.equipmentId=equipmentId;
    }
    public String  getDatabasePort(){
        return databasePort;
    }
    public void setDatabasePort(String databasePort){
        this.databasePort=databasePort;
    }
    public String  getDatabaseSid(){
        return databaseSid;
    }
    public void setDatabaseSid(String databaseSid){
        this.databaseSid=databaseSid;
    }
    public String  getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName=userName;
    }
    public String  getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public String  getDataStrucUpdate(){
        return dataStrucUpdate;
    }
    public void setDataStrucUpdate(String dataStrucUpdate){
        this.dataStrucUpdate=dataStrucUpdate;
    }
    public String  getCheckIsornotUpdate(){
        return checkIsornotUpdate;
    }
    public void setCheckIsornotUpdate(String checkIsornotUpdate){
        this.checkIsornotUpdate=checkIsornotUpdate;
    }
    public Date  getCompliteTime(){
        return compliteTime;
    }
    public void setCompliteTime(Date compliteTime){
        this.compliteTime=compliteTime;
    }
    public String  getRemarks(){
        return remarks;
    }
    public void setRemarks(String remarks){
        this.remarks=remarks;
    }
    public String  getCreator(){
        return creator;
    }
    public void setCreator(String creator){
        this.creator=creator;
    }
    public String  getCreateName(){
        return createName;
    }
    public void setCreateName(String createName){
        this.createName=createName;
    }
    public Date  getCreateTime(){
        return createTime;
    }
    public void setCreateTime(Date createTime){
        this.createTime=createTime;
    }
    public String  getUpdator(){
        return updator;
    }
    public void setUpdator(String updator){
        this.updator=updator;
    }
    public String  getUpdatorName(){
        return updatorName;
    }
    public void setUpdatorName(String updatorName){
        this.updatorName=updatorName;
    }
    public Date getUpdateTime(){
        return updateTime;
    }
    public void setUpdateTime(Date updateTime){
        this.updateTime=updateTime;
    }
	public String getEtlupdate() {
		return etlupdate;
	}
	public void setEtlupdate(String etlupdate) {
		this.etlupdate = etlupdate;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
    
    
}
    
   /* *//**
	 * 记录日志-新增
	 * @return
	 *//*
	public String newString() {
		StringBuffer sb = new StringBuffer("");
			sb.append("：").append(this.getDatabaseIp());
			sb.append("：").append(this.getEquipmentId());
			sb.append("：").append(this.getDatabasePort());
			sb.append("：").append(this.getDatabaseSid());
			sb.append("：").append(this.getUserName());
			sb.append("：").append(this.getPassword());
			sb.append("：").append(this.getDataStrucUpdate());
			sb.append("：").append(this.getCheckIsornotUpdate());
			sb.append("：").append(this.getCompliteTime());
			sb.append("：").append(this.getRemarks());
			sb.append("：").append(this.getCreator());
			sb.append("：").append(this.getCreateName());
			sb.append("：").append(this.getCreateTime());
			sb.append("：").append(this.getUpdator());
			sb.append("：").append(this.getUpdatorName());
			sb.append("：").append(this.getUpdateTime());
		return sb.toString();
	}
	
	*//**
	 * 记录日志-修改
	 * @param DatabaseInfoManagement 原始未更改前
	 * @return
	 *//*
	public String compareDiff(DatabaseInfoManagement DatabaseInfoManagement){
		StringBuffer sb = new StringBuffer("");
			if(!StringUtils.equals(this.getDatabaseIp(),DatabaseInfoManagement.getDatabaseIp())) {
				sb.append("，").append("：").append(DatabaseInfoManagement.getDatabaseIp()).append(" 修改为：").append(this.getDatabaseIp());
			}
			if(!StringUtils.equals(this.getEquipmentId(),DatabaseInfoManagement.getEquipmentId())) {
				sb.append("，").append("：").append(DatabaseInfoManagement.getEquipmentId()).append(" 修改为：").append(this.getEquipmentId());
			}
			if(!StringUtils.equals(this.getDatabasePort(),DatabaseInfoManagement.getDatabasePort())) {
				sb.append("，").append("：").append(DatabaseInfoManagement.getDatabasePort()).append(" 修改为：").append(this.getDatabasePort());
			}
			if(!StringUtils.equals(this.getDatabaseSid(),DatabaseInfoManagement.getDatabaseSid())) {
				sb.append("，").append("：").append(DatabaseInfoManagement.getDatabaseSid()).append(" 修改为：").append(this.getDatabaseSid());
			}
			if(!StringUtils.equals(this.getUserName(),DatabaseInfoManagement.getUserName())) {
				sb.append("，").append("：").append(DatabaseInfoManagement.getUserName()).append(" 修改为：").append(this.getUserName());
			}
			if(!StringUtils.equals(this.getPassword(),DatabaseInfoManagement.getPassword())) {
				sb.append("，").append("：").append(DatabaseInfoManagement.getPassword()).append(" 修改为：").append(this.getPassword());
			}
			if(!StringUtils.equals(this.getDataStrucUpdate(),DatabaseInfoManagement.getDataStrucUpdate())) {
				sb.append("，").append("：").append(DatabaseInfoManagement.getDataStrucUpdate()).append(" 修改为：").append(this.getDataStrucUpdate());
			}
			if(!StringUtils.equals(this.getCheckIsornotUpdate(),DatabaseInfoManagement.getCheckIsornotUpdate())) {
				sb.append("，").append("：").append(DatabaseInfoManagement.getCheckIsornotUpdate()).append(" 修改为：").append(this.getCheckIsornotUpdate());
			}
			if(!StringUtils.equals(this.getCompliteTime(),DatabaseInfoManagement.getCompliteTime())) {
				sb.append("，").append("：").append(DatabaseInfoManagement.getCompliteTime()).append(" 修改为：").append(this.getCompliteTime());
			}
			if(!StringUtils.equals(this.getRemarks(),DatabaseInfoManagement.getRemarks())) {
				sb.append("，").append("：").append(DatabaseInfoManagement.getRemarks()).append(" 修改为：").append(this.getRemarks());
			}
			if(!StringUtils.equals(this.getCreator(),DatabaseInfoManagement.getCreator())) {
				sb.append("，").append("：").append(DatabaseInfoManagement.getCreator()).append(" 修改为：").append(this.getCreator());
			}
			if(!StringUtils.equals(this.getCreateName(),DatabaseInfoManagement.getCreateName())) {
				sb.append("，").append("：").append(DatabaseInfoManagement.getCreateName()).append(" 修改为：").append(this.getCreateName());
			}
			if(!StringUtils.equals(this.getCreateTime(),DatabaseInfoManagement.getCreateTime())) {
				sb.append("，").append("：").append(DatabaseInfoManagement.getCreateTime()).append(" 修改为：").append(this.getCreateTime());
			}
			if(!StringUtils.equals(this.getUpdator(),DatabaseInfoManagement.getUpdator())) {
				sb.append("，").append("：").append(DatabaseInfoManagement.getUpdator()).append(" 修改为：").append(this.getUpdator());
			}
			if(!StringUtils.equals(this.getUpdatorName(),DatabaseInfoManagement.getUpdatorName())) {
				sb.append("，").append("：").append(DatabaseInfoManagement.getUpdatorName()).append(" 修改为：").append(this.getUpdatorName());
			}
			if(!StringUtils.equals(this.getUpdateTime(),DatabaseInfoManagement.getUpdateTime())) {
				sb.append("，").append("：").append(DatabaseInfoManagement.getUpdateTime()).append(" 修改为：").append(this.getUpdateTime());
			}
		return sb.toString();
	}
	
}
*/

