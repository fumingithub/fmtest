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
public class AreaManagement extends Entity<String> {
	
	
	/**域名称*/
    private String  areaName;
	
	/**域编码*/
    private String  areaCode;
	
	/**设备ID*/
    private String  equipmentId;
	
	/**服务器名*/
    private String  serverName;
    
    /**删除标记   "1"是 :已删除 "0" 否:未删除 */
    private String  isDel;
	
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
	
	/**备注*/
    private String  remarks;
    //------------------GETTER & SETTER--------------------
    public String  getAreaName(){
        return areaName;
    }
    public void setAreaName(String areaName){
        this.areaName=areaName;
    }
    public String  getAreaCode(){
        return areaCode;
    }
    public void setAreaCode(String areaCode){
        this.areaCode=areaCode;
    }
    public String  getEquipmentId(){
        return equipmentId;
    }
    public void setEquipmentId(String equipmentId){
        this.equipmentId=equipmentId;
    }
    public String  getServerName(){
        return serverName;
    }
    public void setServerName(String serverName){
        this.serverName=serverName;
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
    public Date getCreateTime(){
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
    public Date  getUpdateTime(){
        return updateTime;
    }
    public void setUpdateTime(Date updateTime){
        this.updateTime=updateTime;
    }
    public String  getRemarks(){
        return remarks;
    }
    public void setRemarks(String remarks){
        this.remarks=remarks;
    }
    public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
    
}
    /**
	 * 记录日志-新增
	 * @return
	 *//*
	public String newString() {
		StringBuffer sb = new StringBuffer("");
			sb.append("：").append(this.getAreaName());
			sb.append("：").append(this.getAreaCode());
			sb.append("：").append(this.getEquipmentId());
			sb.append("：").append(this.getServerName());
			sb.append("：").append(this.getCreator());
			sb.append("：").append(this.getCreateName());
			sb.append("：").append(this.getCreateTime());
			sb.append("：").append(this.getUpdator());
			sb.append("：").append(this.getUpdatorName());
			sb.append("：").append(this.getUpdateTime());
			sb.append("：").append(this.getRemarks());
		return sb.toString();
	}
	
	*//**
	 * 记录日志-修改
	 * @param AreaManagement 原始未更改前
	 * @return
	 *//*
	public String compareDiff(AreaManagement AreaManagement){
		StringBuffer sb = new StringBuffer("");
			if(!StringUtils.equals(this.getAreaName(),AreaManagement.getAreaName())) {
				sb.append("，").append("：").append(AreaManagement.getAreaName()).append(" 修改为：").append(this.getAreaName());
			}
			if(!StringUtils.equals(this.getAreaCode(),AreaManagement.getAreaCode())) {
				sb.append("，").append("：").append(AreaManagement.getAreaCode()).append(" 修改为：").append(this.getAreaCode());
			}
			if(!StringUtils.equals(this.getEquipmentId(),AreaManagement.getEquipmentId())) {
				sb.append("，").append("：").append(AreaManagement.getEquipmentId()).append(" 修改为：").append(this.getEquipmentId());
			}
			if(!StringUtils.equals(this.getServerName(),AreaManagement.getServerName())) {
				sb.append("，").append("：").append(AreaManagement.getServerName()).append(" 修改为：").append(this.getServerName());
			}
			if(!StringUtils.equals(this.getCreator(),AreaManagement.getCreator())) {
				sb.append("，").append("：").append(AreaManagement.getCreator()).append(" 修改为：").append(this.getCreator());
			}
			if(!StringUtils.equals(this.getCreateName(),AreaManagement.getCreateName())) {
				sb.append("，").append("：").append(AreaManagement.getCreateName()).append(" 修改为：").append(this.getCreateName());
			}
			if(!StringUtils.equals(this.getCreateTime(),AreaManagement.getCreateTime())) {
				sb.append("，").append("：").append(AreaManagement.getCreateTime()).append(" 修改为：").append(this.getCreateTime());
			}
			if(!StringUtils.equals(this.getUpdator(),AreaManagement.getUpdator())) {
				sb.append("，").append("：").append(AreaManagement.getUpdator()).append(" 修改为：").append(this.getUpdator());
			}
			if(!StringUtils.equals(this.getUpdatorName(),AreaManagement.getUpdatorName())) {
				sb.append("，").append("：").append(AreaManagement.getUpdatorName()).append(" 修改为：").append(this.getUpdatorName());
			}
			if(!StringUtils.equals(this.getUpdateTime(),AreaManagement.getUpdateTime())) {
				sb.append("，").append("：").append(AreaManagement.getUpdateTime()).append(" 修改为：").append(this.getUpdateTime());
			}
			if(!StringUtils.equals(this.getRemarks(),AreaManagement.getRemarks())) {
				sb.append("，").append("：").append(AreaManagement.getRemarks()).append(" 修改为：").append(this.getRemarks());
			}
		return sb.toString();
	}
	
}
*/

