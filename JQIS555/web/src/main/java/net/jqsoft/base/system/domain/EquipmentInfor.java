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
public class EquipmentInfor extends Entity<String> {
	
	
	/**服务器类型  */
    private String  type;
	
	/**项目ID*/
    private String  projectId;
    
	/**服务器名*/
    private String  serverName;
	
	/**服务器用户名*/
    private String  serverUsername;
	
	/**服务器密码  */
    private String  serverPassword;
	
	/**服务器序列号*/
    private String  serverIp;
	
	/**服务器物理地址*/
    private String  serverMac;
	
	/**服务器内网地址*/
    private String  serverInsideIp;
	
	/**服务器外网地址*/
    private String  serverOutsideIp;
	
    /**删除标记   "1"是 :已删除 "0" 否:未删除 */
    private String  isDel;

	/**创建者*/
    private String  creator;
	
	/**创建人姓名*/
    private String  createName;
	
	/**创建时间*/
    private Date createTime;
	
	/**更新者*/
    private String  updator;
	
	/**更新者姓名*/
    private String  updatorName;
	
	/**更新时间*/
    private Date  updateTime;
	
	/**备注*/
    private String  remark;
    //------------------GETTER & SETTER--------------------
    public String  getType(){
        return type;
    }
    public void setType(String type){
        this.type=type;
    }
    public String  getProjectId(){
        return projectId;
    }
    public void setProjectId(String projectId){
        this.projectId=projectId;
    }
    public String  getServerUsername(){
        return serverUsername;
    }
    public void setServerUsername(String serverUsername){
        this.serverUsername=serverUsername;
    }
    public String  getServerPassword(){
        return serverPassword;
    }
    public void setServerPassword(String serverPassword){
        this.serverPassword=serverPassword;
    }
    public String  getServerIp(){
        return serverIp;
    }
    public void setServerIp(String serverIp){
        this.serverIp=serverIp;
    }
    public String  getServerMac(){
        return serverMac;
    }
    public void setServerMac(String serverMac){
        this.serverMac=serverMac;
    }
    public String  getServerInsideIp(){
        return serverInsideIp;
    }
    public void setServerInsideIp(String serverInsideIp){
        this.serverInsideIp=serverInsideIp;
    }
    public String  getServerOutsideIp(){
        return serverOutsideIp;
    }
    public void setServerOutsideIp(String serverOutsideIp){
        this.serverOutsideIp=serverOutsideIp;
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
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
}
   /* 
    *//**
	 * 记录日志-新增
	 * @return
	 *//*
	public String newString() {
		StringBuffer sb = new StringBuffer("");
			sb.append("：").append(this.getType());
			sb.append("：").append(this.getProjectId());
			sb.append("：").append(this.getServerUsername());
			sb.append("：").append(this.getServerPassword());
			sb.append("：").append(this.getServerIp());
			sb.append("：").append(this.getServerMac());
			sb.append("：").append(this.getServerInsideIp());
			sb.append("：").append(this.getServerOutsideIp());
			sb.append("：").append(this.getStatus());
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
	 * @param EquipmentInfor 原始未更改前
	 * @return
	 *//*
	public String compareDiff(EquipmentInfor EquipmentInfor){
		StringBuffer sb = new StringBuffer("");
			if(!StringUtils.equals(this.getType(),EquipmentInfor.getType())) {
				sb.append("，").append("：").append(EquipmentInfor.getType()).append(" 修改为：").append(this.getType());
			}
			if(!StringUtils.equals(this.getProjectId(),EquipmentInfor.getProjectId())) {
				sb.append("，").append("：").append(EquipmentInfor.getProjectId()).append(" 修改为：").append(this.getProjectId());
			}
			if(!StringUtils.equals(this.getServerUsername(),EquipmentInfor.getServerUsername())) {
				sb.append("，").append("：").append(EquipmentInfor.getServerUsername()).append(" 修改为：").append(this.getServerUsername());
			}
			if(!StringUtils.equals(this.getServerPassword(),EquipmentInfor.getServerPassword())) {
				sb.append("，").append("：").append(EquipmentInfor.getServerPassword()).append(" 修改为：").append(this.getServerPassword());
			}
			if(!StringUtils.equals(this.getServerIp(),EquipmentInfor.getServerIp())) {
				sb.append("，").append("：").append(EquipmentInfor.getServerIp()).append(" 修改为：").append(this.getServerIp());
			}
			if(!StringUtils.equals(this.getServerMac(),EquipmentInfor.getServerMac())) {
				sb.append("，").append("：").append(EquipmentInfor.getServerMac()).append(" 修改为：").append(this.getServerMac());
			}
			if(!StringUtils.equals(this.getServerInsideIp(),EquipmentInfor.getServerInsideIp())) {
				sb.append("，").append("：").append(EquipmentInfor.getServerInsideIp()).append(" 修改为：").append(this.getServerInsideIp());
			}
			if(!StringUtils.equals(this.getServerOutsideIp(),EquipmentInfor.getServerOutsideIp())) {
				sb.append("，").append("：").append(EquipmentInfor.getServerOutsideIp()).append(" 修改为：").append(this.getServerOutsideIp());
			}
			if(!StringUtils.equals(this.getStatus(),EquipmentInfor.getStatus())) {
				sb.append("，").append("：").append(EquipmentInfor.getStatus()).append(" 修改为：").append(this.getStatus());
			}
			if(!StringUtils.equals(this.getCreator(),EquipmentInfor.getCreator())) {
				sb.append("，").append("：").append(EquipmentInfor.getCreator()).append(" 修改为：").append(this.getCreator());
			}
			if(!StringUtils.equals(this.getCreateName(),EquipmentInfor.getCreateName())) {
				sb.append("，").append("：").append(EquipmentInfor.getCreateName()).append(" 修改为：").append(this.getCreateName());
			}
			if(!StringUtils.equals(this.getCreateTime(),EquipmentInfor.getCreateTime())) {
				sb.append("，").append("：").append(EquipmentInfor.getCreateTime()).append(" 修改为：").append(this.getCreateTime());
			}
			if(!StringUtils.equals(this.getUpdator(),EquipmentInfor.getUpdator())) {
				sb.append("，").append("：").append(EquipmentInfor.getUpdator()).append(" 修改为：").append(this.getUpdator());
			}
			if(!StringUtils.equals(this.getUpdatorName(),EquipmentInfor.getUpdatorName())) {
				sb.append("，").append("：").append(EquipmentInfor.getUpdatorName()).append(" 修改为：").append(this.getUpdatorName());
			}
			if(!StringUtils.equals(this.getUpdateTime(),EquipmentInfor.getUpdateTime())) {
				sb.append("，").append("：").append(EquipmentInfor.getUpdateTime()).append(" 修改为：").append(this.getUpdateTime());
			}
			if(!StringUtils.equals(this.getRemark(),EquipmentInfor.getRemark())) {
				sb.append("，").append("：").append(EquipmentInfor.getRemark()).append(" 修改为：").append(this.getRemark());
			}
		return sb.toString();
	}
	
}
*/

