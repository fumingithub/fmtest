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
public class Application extends Entity<String> {  
	
	
	/**设备ID*/
    private String  equipmentId;
    
    /**运用URL*/
    private String  applicationUrl;
	
	/**JDK版本*/
    private String  editionJdk;
    /**删除标记   "1"是 :已删除 "0" 否:未删除 */
    private String  isDel;
	
	/**创建者ID*/
    private String  creator;
	
	/**创建者*/
    private String  createName;
	
	/**创建时间*/
    private Date createTime;
	
	/**更新者ID*/
    private String  updator;
	
	/***/
    private String  updatorName;
	
	/**  */
    private Date updateTime;
	
	/**  */
    private String  remark;
    //------------------GETTER & SETTER--------------------
    public String  getEquipmentId(){
        return equipmentId;
    }
    public void setEquipmentId(String equipmentId){
        this.equipmentId=equipmentId;
    }
    public String  getEditionJdk(){
        return editionJdk;
    }
    public void setEditionJdk(String editionJdk){
        this.editionJdk=editionJdk;
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
    public Date  getUpdateTime(){
        return updateTime;
    }
    public void setUpdateTime(Date updateTime){
        this.updateTime=updateTime;
    }
    public String  getRemark(){
        return remark;
    }
    public void setRemark(String remark){
        this.remark=remark;
    }
    public String getApplicationUrl() {
		return applicationUrl;
	}
	public void setApplicationUrl(String applicationUrl) {
		this.applicationUrl = applicationUrl;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
}
    
  /*  *//**
	 * 记录日志-新增
	 * @return
	 *//*
	public String newString() {
		StringBuffer sb = new StringBuffer("");
			sb.append("：").append(this.getEquipmentId());
			sb.append("：").append(this.getEditionJdk());
			sb.append("：").append(this.getCreator());
			sb.append("：").append(this.getCreateName());
			sb.append("：").append(this.getCreateTime());
			sb.append("：").append(this.getUpdator());
			sb.append("：").append(this.getUpdatorName());
			sb.append("：").append(this.getUpdateTime());
			sb.append("：").append(this.getRemark());
		return sb.toString();
	}
	
	*//**
	 * 记录日志-修改
	 * @param Application 原始未更改前
	 * @return
	 *//*
	public String compareDiff(Application Application){
		StringBuffer sb = new StringBuffer("");
			if(!StringUtils.equals(this.getEquipmentId(),Application.getEquipmentId())) {
				sb.append("，").append("：").append(Application.getEquipmentId()).append(" 修改为：").append(this.getEquipmentId());
			}
			if(!StringUtils.equals(this.getEditionJdk(),Application.getEditionJdk())) {
				sb.append("，").append("：").append(Application.getEditionJdk()).append(" 修改为：").append(this.getEditionJdk());
			}
			if(!StringUtils.equals(this.getCreator(),Application.getCreator())) {
				sb.append("，").append("：").append(Application.getCreator()).append(" 修改为：").append(this.getCreator());
			}
			if(!StringUtils.equals(this.getCreateName(),Application.getCreateName())) {
				sb.append("，").append("：").append(Application.getCreateName()).append(" 修改为：").append(this.getCreateName());
			}
			if(!StringUtils.equals(this.getCreateTime(),Application.getCreateTime())) {
				sb.append("，").append("：").append(Application.getCreateTime()).append(" 修改为：").append(this.getCreateTime());
			}
			if(!StringUtils.equals(this.getUpdator(),Application.getUpdator())) {
				sb.append("，").append("：").append(Application.getUpdator()).append(" 修改为：").append(this.getUpdator());
			}
			if(!StringUtils.equals(this.getUpdatorName(),Application.getUpdatorName())) {
				sb.append("，").append("：").append(Application.getUpdatorName()).append(" 修改为：").append(this.getUpdatorName());
			}
			if(!StringUtils.equals(this.getUpdateTime(),Application.getUpdateTime())) {
				sb.append("，").append("：").append(Application.getUpdateTime()).append(" 修改为：").append(this.getUpdateTime());
			}
			if(!StringUtils.equals(this.getRemark(),Application.getRemark())) {
				sb.append("，").append("：").append(Application.getRemark()).append(" 修改为：").append(this.getRemark());
			}
		return sb.toString();
	}
	
}
*/

