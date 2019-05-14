/**
 * Copyright (c) 2013-2015 jqsoft.net
 */
package net.jqsoft.base.system.domain;

import org.zcframework.core.domain.support.Entity;

/**
 * @author: zuoc
 * @date: 2015-06-17
 * @desc: 资源管理实体
 */
public class Resources extends Entity<String> {
	private static final long serialVersionUID = 6475455730401915724L;

	/**
	 * 资源编码
	 */
    private String  code;
	/**
	 * 资源名称
	 */
    private String  display;
	/**
	 * 资源描述
	 */
    private String  description;
	/**
	 * 父资源id
	 */
    private String  parentCode;
    /**
     * 父资源名称
     */
    private String parentName;
	/**
	 * 资源类型^1菜单，0URL，2按钮
	 */
    private String  type;
	/**
	 * 访问地址
	 */
    private String  url;
	/**
	 * 是否启用^0正常1不可用
	 */
    private String  status;
	/**
	 * 排序
	 */
    private Integer  sortby;
	/**
	 * 图标
	 */
    private String  icon;
    /**
     *是否勾选1：勾选 0：未勾选
     */
    private Integer isCheck;
    /**
     *是否有子资源1：有 0：无
     */
    private Integer isHavaChild;

    /** zTree树根节点id */
    private String rootId;

    /** zTree树根节点Name */
    private String rootName;

    public String  getCode(){
        return code;
    }

    public void setCode(String code){
        this.code=code;
    }
    public String  getDisplay(){
        return display;
    }

    public void setDisplay(String display){
        this.display=display;
    }
    public String  getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description=description;
    }
    public String  getParentCode(){
        return parentCode;
    }

    public void setParentCode(String parentCode){
        this.parentCode=parentCode;
    }
    public String  getType(){
        return type;
    }

    public void setType(String type){
        this.type=type;
    }
    public String  getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url=url;
    }
    public String  getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status=status;
    }
    public Integer  getSortby(){
        return sortby;
    }

    public void setSortby(Integer sortby){
        this.sortby=sortby;
    }
    public String  getIcon(){
        return icon;
    }

    public void setIcon(String icon){
        this.icon=icon;
    }

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Integer isCheck) {
		this.isCheck = isCheck;
	}

	public Integer getIsHavaChild() {
		return isHavaChild;
	}

	public void setIsHavaChild(Integer isHavaChild) {
		this.isHavaChild = isHavaChild;
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
}
