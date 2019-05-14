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
public class EtlManagement extends Entity<String> {
	
	
	/**  */
    private String  equipmentId;
	
	/**  */
    private String  isornotUpdate;
	
	/**  */
    private String  creator;
	
	/**  */
    private String  createName;
	
	/**  */
    private Date  createTime;
	
	/**  */
    private String  updator;
	
	/**  */
    private String  updatorName;
	
	/**  */
    private Date updateTime;
	
	/**  */
    private String  remarks;
    //------------------GETTER & SETTER--------------------
    public String  getEquipmentId(){
        return equipmentId;
    }
    public void setEquipmentId(String equipmentId){
        this.equipmentId=equipmentId;
    }
    public String  getIsornotUpdate(){
        return isornotUpdate;
    }
    public void setIsornotUpdate(String isornotUpdate){
        this.isornotUpdate=isornotUpdate;
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
    public String  getRemarks(){
        return remarks;
    }
    public void setRemarks(String remarks){
        this.remarks=remarks;
    }
}
    /*
    *//**
	 * 记录日志-新增
	 * @return
	 *//*
	public String newString() {
		StringBuffer sb = new StringBuffer("");
			sb.append("：").append(this.getEquipmentId());
			sb.append("：").append(this.getIsornotUpdate());
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
	 * @param EtlManagement 原始未更改前
	 * @return
	 *//*
	public String compareDiff(EtlManagement EtlManagement){
		StringBuffer sb = new StringBuffer("");
			if(!StringUtils.equals(this.getEquipmentId(),EtlManagement.getEquipmentId())) {
				sb.append("，").append("：").append(EtlManagement.getEquipmentId()).append(" 修改为：").append(this.getEquipmentId());
			}
			if(!StringUtils.equals(this.getIsornotUpdate(),EtlManagement.getIsornotUpdate())) {
				sb.append("，").append("：").append(EtlManagement.getIsornotUpdate()).append(" 修改为：").append(this.getIsornotUpdate());
			}
			if(!StringUtils.equals(this.getCreator(),EtlManagement.getCreator())) {
				sb.append("，").append("：").append(EtlManagement.getCreator()).append(" 修改为：").append(this.getCreator());
			}
			if(!StringUtils.equals(this.getCreateName(),EtlManagement.getCreateName())) {
				sb.append("，").append("：").append(EtlManagement.getCreateName()).append(" 修改为：").append(this.getCreateName());
			}
			if(!StringUtils.equals(this.getCreateTime(),EtlManagement.getCreateTime())) {
				sb.append("，").append("：").append(EtlManagement.getCreateTime()).append(" 修改为：").append(this.getCreateTime());
			}
			if(!StringUtils.equals(this.getUpdator(),EtlManagement.getUpdator())) {
				sb.append("，").append("：").append(EtlManagement.getUpdator()).append(" 修改为：").append(this.getUpdator());
			}
			if(!StringUtils.equals(this.getUpdatorName(),EtlManagement.getUpdatorName())) {
				sb.append("，").append("：").append(EtlManagement.getUpdatorName()).append(" 修改为：").append(this.getUpdatorName());
			}
			if(!StringUtils.equals(this.getUpdateTime(),EtlManagement.getUpdateTime())) {
				sb.append("，").append("：").append(EtlManagement.getUpdateTime()).append(" 修改为：").append(this.getUpdateTime());
			}
			if(!StringUtils.equals(this.getRemarks(),EtlManagement.getRemarks())) {
				sb.append("，").append("：").append(EtlManagement.getRemarks()).append(" 修改为：").append(this.getRemarks());
			}
		return sb.toString();
	}
	
}

*/
