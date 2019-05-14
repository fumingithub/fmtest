/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.base.system.domain;

import org.zcframework.core.domain.support.Entity;

/**
 * 组织机构实体类
 * @author jinliang
 * @datetime 2016-11-25
 */
public class SysOrg extends Entity<String> {
    
    /** 机构代码 */
    private String  code;

    /** 机构名称 */
    private String  name;

    /** 区域编码 */
    private String  areaId;

    /** 区域名称 */
    private String  areaName;
    
    /** 父级机构 */
    private String  parentId;

    /** 父级机构名称 */
    private String  parentName;

    /** 通讯地址 */
    private String  address;
    
    /** 手机号码 */
    private String  telephone;

    /** 电子邮箱 */
    private String  email;

    /** 状态 */
    private String  status;

    /** 是否为父节点（只有两层关系） 0：父级 1：子级 */
    private String  isParent;

    /** 授权农合行政区划编码 */
    private String authAreaCode;

    /** 授权农合行政区划名称 */
    private String authAreaName;

    /** zTree树根节点id */
    private String rootId;

    /** zTree树根节点Name */
    private String rootName;

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

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getIsParent() {
        return isParent;
    }

    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getAuthAreaCode() {
        return authAreaCode;
    }

    public void setAuthAreaCode(String authAreaCode) {
        this.authAreaCode = authAreaCode;
    }

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }

    public String getAuthAreaName() {
        return authAreaName;
    }

    public void setAuthAreaName(String authAreaName) {
        this.authAreaName = authAreaName;
    }
}


