package net.jqsoft.base.system.domain;

import java.util.Date;

import org.zcframework.core.domain.support.Entity;

/**
 * 文件表
 * @author liuj
 * 2015/5/12
 */
public class UploadFile extends Entity<String> {
	
	private static final long serialVersionUID = 4892887884419798572L;
	private String fileId;
	private String fileName;
	private Long fileSize;
	private String filePath;
	private String md5;
	/**
	 * 创建者
	 */
    private String  creator;
	/**
	 * 创建时间
	 */
    private Integer  createTime;
	/**
	 * 更新者
	 */
    private String  updator;
	/**
	 * 更新时间
	 */
    private Integer  updateTime;
	private String fileCode; //文件编码,用于页面回显使用,实际表中没有改字段
	private String name;
	private String cardNo;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileCode() {
		return fileCode;
	}
	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Integer getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}
	public Integer getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdator() {
		return updator;
	}
	public void setUpdator(String updator) {
		this.updator = updator;
	}

}
