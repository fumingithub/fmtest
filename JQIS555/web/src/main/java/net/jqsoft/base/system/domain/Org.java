/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.base.system.domain;

import org.zcframework.core.domain.support.Entity;

/**
 * @author
 * @datetime 2016-4-25
 * @desc
 * @see
 */
public class Org extends Entity<String> {

    private String code;//机构代码
    private String name;//机构名称
    private String parentId;//父机构名称
    private String address;//通讯地址
    private String telephone;//电话号码
    private String email;//电子邮箱
    private String areaId;//地区
    private String areaName;//地区名称
    private Integer status;// 0启用 1禁用
    /**
     * 是否村室,0否,1是
     */
    private Integer isVillage;
    /**
     *是否有子资源1：有 0：无
     */
    private Integer isHaveChild;

    //------------------GETTER & SETTER--------------------

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }



	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}





	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

    public Integer getIsVillage() {
        return isVillage;
    }

    public void setIsVillage(Integer isVillage) {
        this.isVillage = isVillage;
    }

	public Integer getisHaveChild() {
		return isHaveChild;
	}

	public void setisHaveChild(Integer isHaveChild) {
		this.isHaveChild = isHaveChild;
	}
}


