/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.domain;


import org.zcframework.core.domain.support.Entity;



public class Role extends Entity<String> {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 角色编码
	 */
    private String  code;
	/**
	 * 角色名称
	 */
    private String  name;
	/**
	 * 区划编码
	 */
    private String  areaId;
    /**
     * 区划名称
     */
    private String  areaName;
	/**
	 * 父角色ID^只能创建自己角色以下的角色，且area_id只能现则自己地区及以下的地区
	 */
    private String  proleId;
    /**
     * 父角色名称
     */
    private String  parentName;
	/**
	 * 角色描述
	 */
    private String  remark;
    /**
     * 是否被勾选1：勾选 0：未勾选
     * 是否父角色1：是 0：否
     */
    private String isCheck;

    public String  getCode(){
        return code;
    }

    public void setCode(String code){
        this.code=code;
    }
    public String  getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }
    public String  getAreaId(){
        return areaId;
    }

    public void setAreaId(String areaId){
        this.areaId=areaId;
    }
    public String  getProleId(){
        return proleId;
    }

    public void setProleId(String proleId){
        this.proleId=proleId;
    }
    public String  getRemark(){
        return remark;
    }

    public void setRemark(String remark){
        this.remark=remark;
    }

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

}


