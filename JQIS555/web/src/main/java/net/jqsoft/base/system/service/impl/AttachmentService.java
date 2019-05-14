/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zcframework.core.dao.IEntityDao;
import org.zcframework.core.service.BaseEntityService;

import net.jqsoft.base.system.dao.AttachmentDao;
import net.jqsoft.base.system.domain.Attachment;
import net.jqsoft.base.system.service.IAttachmentService;
import net.jqsoft.common.exception.ManagerException;
import net.jqsoft.common.util.CommonUtil;

/**
 * AttachmentService
 * @author xiaohf@jqsoft.net
 * @version 1.0
 */
@Service
public class AttachmentService extends BaseEntityService<Attachment> implements IAttachmentService{

	@Autowired
	private AttachmentDao attachmentDao;

	@Override
    protected IEntityDao<Attachment> getDao() {
        return attachmentDao;
    }

	@Override
	public void delete(Attachment attachment) throws ManagerException {
		try {
			
			// 删除数据记录
			attachmentDao.removeById(attachment.getId());
			
			// 删除对应的文件
			File file = new File(attachment.getPath());
			// 删除文件
			if(file.exists()){
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ManagerException("删除附件出错："+e.getMessage());
		}
		
		
	}
	/**
	 * @desc 根据md5查询文件是否存在
	 */
	public Attachment getFileByMd5(String md5){
		String sqlId ="Attachment.selectByMd5";
		List<Attachment> fileList = attachmentDao.find(sqlId, md5);
		if(!CommonUtil.isNullOrEmpty(fileList)){
			return fileList.get(0);
		}
		return null;
	}

	

	
}
