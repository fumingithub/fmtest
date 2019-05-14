/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.base.system.domain;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.zcframework.core.domain.support.Entity;

/**
* @author 
* @datetime 2016-12-12
* @desc
* @see
*/
public class Attachment extends Entity<String> {

	/** 附件名称,加密前的原文件名 */
    private String  name;  
	
    private String md5;
    
	/** 文件类型 */
    private String  type;
	
	/** 文件大小 */
    private Integer  fileSize;
	
	/** 描述 */
    private String  description;
	
	/** 文件路径 */
    private String  path;
	
	/** 加密后的密码 */
    private String  encodeName;
	
	/** 关连业务代码：HEALTH_CARD-健康卡信息； */
    private String  relateBizCode;
	
	/** 相关业务表单ID */
    private String  relateBizId;
	
	/** 状态 */
    private String  status;
	
	/** 备注 */
    private String  remark;
	
	/** 更新人ID */
    private String  updator;
	
	/** 更新人name */
    private String  updatorName;
	
	/** 更新时间 */
    private Date  updateTime;
	
	/** 创建人ID */
    private String  creator;
	
	/** 创建人name */
    private String  creatorName;
	
	/** 创建时间 */
    private Date  createTime;
    //------------------GETTER & SETTER--------------------
    public String  getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String  getType(){
        return type;
    }
    public void setType(String type){
        this.type=type;
    }
    public Integer  getFileSize(){
        return fileSize;
    }
    public void setFileSize(Integer fileSize){
        this.fileSize=fileSize;
    }
    public String  getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public String  getPath(){
        return path;
    }
    public void setPath(String path){
        this.path=path;
    }
    public String  getEncodeName(){
        return encodeName;
    }
    public void setEncodeName(String encodeName){
        this.encodeName=encodeName;
    }
    public String  getRelateBizCode(){
        return relateBizCode;
    }
    public void setRelateBizCode(String relateBizCode){
        this.relateBizCode=relateBizCode;
    }
    public String  getRelateBizId(){
        return relateBizId;
    }
    public void setRelateBizId(String relateBizId){
        this.relateBizId=relateBizId;
    }
    public String  getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status=status;
    }
    public String  getRemark(){
        return remark;
    }
    public void setRemark(String remark){
        this.remark=remark;
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
    public String  getCreator(){
        return creator;
    }
    public void setCreator(String creator){
        this.creator=creator;
    }
    public String  getCreatorName(){
        return creatorName;
    }
    public void setCreatorName(String creatorName){
        this.creatorName=creatorName;
    }
    public Date  getCreateTime(){
        return createTime;
    }
    public void setCreateTime(Date createTime){
        this.createTime=createTime;
    }
    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
    
    /**
	 * 记录日志-新增
	 * @return
	 */
	public String newString() {
		StringBuffer sb = new StringBuffer("");
			sb.append("附件名称,加密前的原文件名：").append(this.getName());
			sb.append("文件类型：").append(this.getType());
			sb.append("文件大小：").append(this.getFileSize());
			sb.append("描述：").append(this.getDescription());
			sb.append("文件路径：").append(this.getPath());
			sb.append("加密后的密码：").append(this.getEncodeName());
			sb.append("关连业务代码：HEALTH_CARD-健康卡信息；：").append(this.getRelateBizCode());
			sb.append("相关业务表单ID：").append(this.getRelateBizId());
			sb.append("状态：").append(this.getStatus());
			sb.append("备注：").append(this.getRemark());
			sb.append("更新人ID：").append(this.getUpdator());
			sb.append("更新人name：").append(this.getUpdatorName());
			sb.append("更新时间：").append(this.getUpdateTime());
			sb.append("创建人ID：").append(this.getCreator());
			sb.append("创建人name：").append(this.getCreatorName());
			sb.append("创建时间：").append(this.getCreateTime());
		return sb.toString();
	}
	
	/**
	 * 记录日志-修改
	 * @param Attachment 原始未更改前
	 * @return
	 */
	public String compareDiff(Attachment Attachment){
		StringBuffer sb = new StringBuffer("");
			if(!StringUtils.equals(this.getName(),Attachment.getName())) {
				sb.append("，").append("附件名称,加密前的原文件名：").append(Attachment.getName()).append(" 修改为：").append(this.getName());
			}
			if(!StringUtils.equals(this.getType(),Attachment.getType())) {
				sb.append("，").append("文件类型：").append(Attachment.getType()).append(" 修改为：").append(this.getType());
			}
			if(this.getFileSize() != Attachment.getFileSize()) {
				sb.append("，").append("文件大小：").append(Attachment.getFileSize()).append(" 修改为：").append(this.getFileSize());
			}
			if(!StringUtils.equals(this.getDescription(),Attachment.getDescription())) {
				sb.append("，").append("描述：").append(Attachment.getDescription()).append(" 修改为：").append(this.getDescription());
			}
			if(!StringUtils.equals(this.getPath(),Attachment.getPath())) {
				sb.append("，").append("文件路径：").append(Attachment.getPath()).append(" 修改为：").append(this.getPath());
			}
			if(!StringUtils.equals(this.getEncodeName(),Attachment.getEncodeName())) {
				sb.append("，").append("加密后的密码：").append(Attachment.getEncodeName()).append(" 修改为：").append(this.getEncodeName());
			}
			if(!StringUtils.equals(this.getRelateBizCode(),Attachment.getRelateBizCode())) {
				sb.append("，").append("关连业务代码：HEALTH_CARD-健康卡信息；：").append(Attachment.getRelateBizCode()).append(" 修改为：").append(this.getRelateBizCode());
			}
			if(!StringUtils.equals(this.getRelateBizId(),Attachment.getRelateBizId())) {
				sb.append("，").append("相关业务表单ID：").append(Attachment.getRelateBizId()).append(" 修改为：").append(this.getRelateBizId());
			}
			if(!StringUtils.equals(this.getStatus(),Attachment.getStatus())) {
				sb.append("，").append("状态：").append(Attachment.getStatus()).append(" 修改为：").append(this.getStatus());
			}
			if(!StringUtils.equals(this.getRemark(),Attachment.getRemark())) {
				sb.append("，").append("备注：").append(Attachment.getRemark()).append(" 修改为：").append(this.getRemark());
			}
		return sb.toString();
	}
	
}


