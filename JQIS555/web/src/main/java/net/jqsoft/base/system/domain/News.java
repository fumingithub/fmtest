/**
 * Copyright (c) 2013-2015 jqsoft.net
 */
package net.jqsoft.base.system.domain;

import org.zcframework.core.domain.support.Entity;

/**
 * @author: zuoc
 * @date: 2015/5/25
 * @desc: 新闻信息管理实体类
 */
public class News extends Entity<String> {
	
	private static final long serialVersionUID = -1318654259618510964L;

	/**
	 * 标题
	 */
    private String  title;
	/**
	 * 信息类别（字典项）
	 */
    private String  type;
	/**
	 * 作者
	 */
    private String  author;
	/**
	 * 发布状态（0：发布   1：未发布）
	 */
    private String  status;
	/**
	 * 发布时间（处于未发布状态时，不记录时间）
	 */
    private Integer  releasetime;
	/**
	 * 发布者（当前系统登陆人）
	 */
    private String  releaseperson;
	/**
	 * 副标题
	 */
    private String  subtitle;
	/**
	 * 信息内容
	 */
    private String  message;
	/**
	 * 可阅读的行政区域
	 */
    private String  readAreaId;
    /**
	 * 可阅读的行政区域名称
	 */
    private String  areaName;
	/**
	 * 更新者
	 */
    private String  updator;
	/**
	 * 更新时间
	 */
    private Integer  updateTime;

    public String  getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title=title;
    }
    public String  getType(){
        return type;
    }

    public void setType(String type){
        this.type=type;
    }
    public String  getAuthor(){
        return author;
    }

    public void setAuthor(String author){
        this.author=author;
    }
    public Integer  getReleasetime(){
        return releasetime;
    }

    public void setReleasetime(Integer releasetime){
        this.releasetime=releasetime;
    }
    public String  getReleaseperson(){
        return releaseperson;
    }

    public void setReleaseperson(String releaseperson){
        this.releaseperson=releaseperson;
    }
    public String  getSubtitle(){
        return subtitle;
    }

    public void setSubtitle(String subtitle){
        this.subtitle=subtitle;
    }
    public String  getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message=message;
    }
    public String  getReadAreaId(){
        return readAreaId;
    }

    public void setReadAreaId(String readAreaId){
        this.readAreaId=readAreaId;
    }

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}
}
