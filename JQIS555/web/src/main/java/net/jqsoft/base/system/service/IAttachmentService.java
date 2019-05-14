/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.service;


import org.zcframework.core.service.IBaseEntityService;

import net.jqsoft.base.system.domain.Attachment;
import net.jqsoft.common.exception.ManagerException;

/**
 * IAttachmentService
 * @author xiaohf@jqsoft.net
 * @version 1.0
 */
public interface IAttachmentService extends IBaseEntityService<Attachment> {

	/**
	 * 删除附件记录，并删除附件文件
	 * @author			xiaohf
	 * @createDate		2016年12月16日上午9:56:44                                                                 
	 * @param attachment
	 * @throws ManagerException
	 */
	public void delete(Attachment attachment) throws ManagerException;

	/**
	 * @desc 根据md5查询文件是否存在
	 */
	public Attachment getFileByMd5(String md5);

}
