/**
 * Copyright (c) 2006-2016 jqsoft.net
 */

package net.jqsoft.meeting.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zcframework.core.dao.IEntityDao;
import org.zcframework.core.service.BaseEntityService;
import org.zcframework.core.utils.date.UtilDateTime;
import org.zcframework.security.SpringSecurityUtils;
import org.zcframework.security.object.Loginer;

import net.jqsoft.common.exception.ManagerException;
import net.jqsoft.common.util.CommonUtil;
import net.jqsoft.meeting.dao.MeetingDao;
import net.jqsoft.meeting.domain.Meeting;
import net.jqsoft.meeting.service.IMeetingService;

import java.sql.Timestamp;

/**
 * MeetingService
 * @author jucj
 * @version 1.0
 */
@Service
public class MeetingService extends BaseEntityService<Meeting> implements IMeetingService{
	
	@Autowired
	private MeetingDao meetingDao;

	@Override
	protected IEntityDao<Meeting> getDao() {
		// TODO Auto-generated method stub
		return meetingDao;
	}
	
	/**
     * 保存修改用户
     * @author jucj
     * @create 2017-1-20 10:25:40
     */
    public int saveOrUpdateMeeting(Meeting entity) {
        int result = 0;
        Loginer loginer = (Loginer)SpringSecurityUtils.getCurrentUser();
        if (StringUtils.isNotEmpty(entity.getId())) {
        	entity.setUpdator(loginer.getId().toString());
            entity.setUpdatorName(SpringSecurityUtils.getCurrentUserName());
            //entity.setUpdateTime(UtilDateTime.nowDate());
            Timestamp time = new Timestamp(System.currentTimeMillis());
            entity.setUpdateTime(time);
            this.meetingDao.update(entity);
            result = 2;
        } else {
        	entity.setCreator(loginer.getId().toString());
            entity.setCreateTime(UtilDateTime.nowDate());
            entity.setCreatorName(SpringSecurityUtils.getCurrentUserName());
            entity.setId(CommonUtil.getUUID());
            this.meetingDao.create(entity);
            result = 1;
            
        }
        return result;
    }
    
    /**
     * @return 返回会议室名称和位置的重复条数
     * @author jucj
     * @create 2017-1-20 10:25:40
     */
	@Override
	public int getNameAndLocation(String name,String location,String id){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
    	param.put("name", name);
    	param.put("location", location);
		return this.meetingDao.findUniqueBy("Meeting.getNameAndLocation",param);
	}

}