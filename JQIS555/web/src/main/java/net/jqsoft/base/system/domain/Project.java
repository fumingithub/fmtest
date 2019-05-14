/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.base.system.domain;

import org.zcframework.core.domain.support.Entity;
import org.zcframework.core.utils.date.UtilDateTime;
import org.zcframework.security.SpringSecurityUtils;
import org.zcframework.security.object.Loginer;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
* @author wangtao 
* @datetime 2017-10-16
* @desc
* @see
*/
public class Project extends Entity<String> {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**项目名称  */
    private String  projectName;
    /**服务器名称*/
    private String  equipment; 
    /**是否拥有子资源 1：有 0：无*/
    private String isHavaChild;
    
    /**是否勾选1：勾选 0：未勾选*/
    private String ischeck;
    
	/**项目ID*/
    private String  serProId;
    
	/**项目授权标示*/
    private String  sysuserproid;
    
	/**服务器IP*/
    private String  equipmentIp; 
    /**管理员*/
    private String  admin; 
    /**管理员密码 */
    private String  adminPw; 
    /**域名称  */
    private String  domainName; 
    /**域编码  */
    private String  domainCode; 
    /**数据库地址 */
    private String  dataAddr; 
    /**用户名  */
    private String  userName; 
    /**密码 */
    private String  passWord; 
    
	/**数据结构是否升级 */
    private String  isDataStruUp; 
	/**校验是否 升级  */
    private String  isCheckUp; 
    /**etl是否 升级 */
    private String  isEtlUp; 
		
	/**版本号*/
     private String  edition;
	
	/**地区*/
    private String  area;
    
	/**平台地址*/
    private String  platformAddr;
    
	/**平台用户名*/
    private String  platformUserName;
    
	/**平台密码*/
    private String  platformPassword;
    
	/**校验系统地址*/
    private String  checkSysAddr;

	/**接口地址*/
	private String  apiAddress;

	/**平台类型   "1":.NET "2" Java */
    private String  platformType;
    
    /**校验系统类型   "1":.NET "2" Java */
    private String  checkSysType;
    
    /**项目地址 */
    private String  projectAddr;
    
    /**项目负责人 */
    private String  projectLeader;

	/**维护人 */
	private String  guardian;
    
    /**电话*/
    private String  phone;
    
	/**qq*/
    private String  qq;
    
    /**电子邮箱 */
    private String  email;
	
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
    
    
    //-----------ztree 
    
    private String code;
    private String name;
    private String pCode;
    private String pName;
    
    //------------------GETTER & SETTER--------------------
    public String  getProjectName(){
        return projectName;
    }
    public void setProjectName(String projectName){
        this.projectName=projectName;
    }
    public String  getEdition(){
        return edition;
    }
    public void setEdition(String edition){
        this.edition=edition;
    }
    public String  getArea(){
        return area;
    }
    public void setArea(String area){
        this.area=area;
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
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	 public String getPlatformAddr() {
			return platformAddr;
		}
		public void setPlatformAddr(String platformAddr) {
			this.platformAddr = platformAddr;
		}
		public String getPlatformUserName() {
			return platformUserName;
		}
		public void setPlatformUserName(String platformUserName) {
			this.platformUserName = platformUserName;
		}
		public String getPlatformPassword() {
			return platformPassword;
		}
		public void setPlatformPassword(String platformPassword) {
			this.platformPassword = platformPassword;
		}
		public String getCheckSysAddr() {
			return checkSysAddr;
		}
		public void setCheckSysAddr(String checkSysAddr) {
			this.checkSysAddr = checkSysAddr;
		}
		public String getPlatformType() {
			return platformType;
		}
		public void setPlatformType(String platformType) {
			this.platformType = platformType;
		}
		public String getCheckSysType() {
			return checkSysType;
		}
		public void setCheckSysType(String checkSysType) {
			this.checkSysType = checkSysType;
		}
		public String getEquipment() {
			return equipment;
		}
		public void setEquipment(String equipment) {
			this.equipment = equipment;
		}
		public String getEquipmentIp() {
			return equipmentIp;
		}
		public void setEquipmentIp(String equipmentIp) {
			this.equipmentIp = equipmentIp;
		}
		public String getAdmin() {
			return admin;
		}
		public void setAdmin(String admin) {
			this.admin = admin;
		}
		public String getAdminPw() {
			return adminPw;
		}
		public void setAdminPw(String adminPw) {
			this.adminPw = adminPw;
		}
		public String getDomainName() {
			return domainName;
		}
		public void setDomainName(String domainName) {
			this.domainName = domainName;
		}
		public String getDomainCode() {
			return domainCode;
		}
		public void setDomainCode(String domainCode) {
			this.domainCode = domainCode;
		}
		public String getDataAddr() {
			return dataAddr;
		}
		public String getIsDataStruUp() {
			return isDataStruUp;
		}
		public void setIsDataStruUp(String isDataStruUp) {
			this.isDataStruUp = isDataStruUp;
		}
		public String getIsCheckUp() {
			return isCheckUp;
		}
		public void setIsCheckUp(String isCheckUp) {
			this.isCheckUp = isCheckUp;
		}
		public String getIsEtlUp() {
			return isEtlUp;
		}
		public void setIsEtlUp(String isEtlUp) {
			this.isEtlUp = isEtlUp;
		}
		public String getProjectAddr() {
			return projectAddr;
		}
		public void setProjectAddr(String projectAddr) {
			this.projectAddr = projectAddr;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getPassWord() {
			return passWord;
		}
		public void setPassWord(String passWord) {
			this.passWord = passWord;
		}
		public String getSysuserproid() {
			return sysuserproid;
		}
		public void setSysuserproid(String sysuserproid) {
			this.sysuserproid = sysuserproid;
		}
		public String getSerProId() {
			return serProId;
		}
		public void setSerProId(String serProId) {
			this.serProId = serProId;
		}
		public String getIsHavaChild() {
			return isHavaChild;
		}
		public void setIsHavaChild(String isHavaChild) {
			this.isHavaChild = isHavaChild;
		}
		public String getIscheck() {
			return ischeck;
		}
		public void setIscheck(String ischeck) {
			this.ischeck = ischeck;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getpCode() {
			return pCode;
		}
		public void setpCode(String pCode) {
			this.pCode = pCode;
		}
		public String getpName() {
			return pName;
		}
		public void setpName(String pName) {
			this.pName = pName;
		}
		public String getProjectLeader() {
			return projectLeader;
		}
		public void setProjectLeader(String projectLeader) {
			this.projectLeader = projectLeader;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getQq() {
			return qq;
		}
		public void setQq(String qq) {
			this.qq = qq;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}

	public String getGuardian() {
		return guardian;
	}

	public void setGuardian(String guardian) {
		this.guardian = guardian;
	}
	public String getApiAddress() {
		return apiAddress;
	}

	public void setApiAddress(String apiAddress) {
		this.apiAddress = apiAddress;
	}
}
    
    /**
	 * 记录日志-新增
	 * @return
	 *//*
	public String newString() {
		StringBuffer sb = new StringBuffer("");
			sb.append("：").append(this.getProjectName());
			sb.append("：").append(this.getProjectUrl());
			sb.append("：").append(this.getUsername());
			sb.append("：").append(this.getPassword());
			sb.append("：").append(this.getEdition());
			sb.append("：").append(this.getArea());
			sb.append("：").append(this.getStatus());
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
	 * @param Project 原始未更改前
	 * @return
	 *//*
	public String compareDiff(Project Project){
		StringBuffer sb = new StringBuffer("");
			if(!StringUtils.equals(this.getProjectName(),Project.getProjectName())) {
				sb.append("，").append("：").append(Project.getProjectName()).append(" 修改为：").append(this.getProjectName());
			}
			if(!StringUtils.equals(this.getProjectUrl(),Project.getProjectUrl())) {
				sb.append("，").append("：").append(Project.getProjectUrl()).append(" 修改为：").append(this.getProjectUrl());
			}
			if(!StringUtils.equals(this.getUsername(),Project.getUsername())) {
				sb.append("，").append("：").append(Project.getUsername()).append(" 修改为：").append(this.getUsername());
			}
			if(!StringUtils.equals(this.getPassword(),Project.getPassword())) {
				sb.append("，").append("：").append(Project.getPassword()).append(" 修改为：").append(this.getPassword());
			}
			if(!StringUtils.equals(this.getEdition(),Project.getEdition())) {
				sb.append("，").append("：").append(Project.getEdition()).append(" 修改为：").append(this.getEdition());
			}
			if(!StringUtils.equals(this.getArea(),Project.getArea())) {
				sb.append("，").append("：").append(Project.getArea()).append(" 修改为：").append(this.getArea());
			}
			if(!StringUtils.equals(this.getStatus(),Project.getStatus())) {
				sb.append("，").append("：").append(Project.getStatus()).append(" 修改为：").append(this.getStatus());
			}
			if(!StringUtils.equals(this.getCreator(),Project.getCreator())) {
				sb.append("，").append("：").append(Project.getCreator()).append(" 修改为：").append(this.getCreator());
			}
			if(!StringUtils.equals(this.getCreateName(),Project.getCreateName())) {
				sb.append("，").append("：").append(Project.getCreateName()).append(" 修改为：").append(this.getCreateName());
			}
			if(!StringUtils.equals(this.getCreateTime(),Project.getCreateTime())) {
				sb.append("，").append("：").append(Project.getCreateTime()).append(" 修改为：").append(this.getCreateTime());
			}
			if(!StringUtils.equals(this.getUpdator(),Project.getUpdator())) {
				sb.append("，").append("：").append(Project.getUpdator()).append(" 修改为：").append(this.getUpdator());
			}
			if(!StringUtils.equals(this.getUpdatorName(),Project.getUpdatorName())) {
				sb.append("，").append("：").append(Project.getUpdatorName()).append(" 修改为：").append(this.getUpdatorName());
			}
			if(!StringUtils.equals(this.getUpdateTime(),Project.getUpdateTime())) {
				sb.append("，").append("：").append(Project.getUpdateTime()).append(" 修改为：").append(this.getUpdateTime());
			}
			if(!StringUtils.equals(this.getRemarks(),Project.getRemarks())) {
				sb.append("，").append("：").append(Project.getRemarks()).append(" 修改为：").append(this.getRemarks());
			}
		return sb.toString();
	}
	
}


*/