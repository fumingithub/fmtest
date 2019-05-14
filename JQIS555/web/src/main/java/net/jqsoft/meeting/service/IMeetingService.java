/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.meeting.service;

import java.util.List;
import java.util.Map;

import org.zcframework.core.service.IBaseEntityService;

import net.jqsoft.common.exception.ManagerException;
import net.jqsoft.meeting.domain.Meeting;

/**
 * IMeetingService
 * @author jucj
 * @version 1.0
 */
public interface IMeetingService extends IBaseEntityService<Meeting>{

	/**
	 * 保存、编辑
	 * @author jucj
	 * @date: 2017/1/16 
	 */
	public int  saveOrUpdateMeeting(Meeting entity);
	
	/**
	 * 获取前台页面的名称和位置
	 * @author jucjz
	 * @date: 2017/1/16 
	 */
	public int getNameAndLocation(String name, String location,String id);
}