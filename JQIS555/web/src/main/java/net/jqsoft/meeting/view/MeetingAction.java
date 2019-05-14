/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.meeting.view;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.view.support.Result;

import net.jqsoft.base.system.domain.User;
import net.jqsoft.base.system.service.IUserService;
import net.jqsoft.common.action.MyEntityAction;
import net.jqsoft.common.exception.ManagerException;
import net.jqsoft.common.util.CurrentUserUtil;
import net.jqsoft.meeting.domain.Meeting;
import net.jqsoft.meeting.service.IMeetingService;

/**
 * @author: jucj
 * @date: 2017/1/16
 * @desc 会议室管理Action
 */
public class MeetingAction extends MyEntityAction<Meeting, IMeetingService> {

	private static final long serialVersionUID = -2865040630611707684L;

	/** 功能区分types:add-新增;edit-编辑;view-查看 */
	private String types;

	@Autowired
	private IMeetingService meetingService;

	@Autowired
	private IUserService userService;

	@Override
	protected IMeetingService getEntityManager() {
		return meetingService;
	}

	/**
	 * 跳转到 会议室维护页面
	 * @return java.lang.String
	 * @author jucj
	 * @create 2017-1-18 13:59:40
	 **/
	public String edit() {
		types = this.getHttpServletRequest().getParameter("types");
		super.edit();
		/** 获取当前用户的ID */
		String userId = CurrentUserUtil.getCurrentUser().getId().toString();
		User user = null;
		if (StringUtils.isNotBlank(userId)) {
			user = this.userService.get(userId);
		}
		return INPUT;
	}

	/**
	 * 会议室增加or修改
	 * @return java.lang.String
	 * @author jucj
	 * @create 2017-1-19 11:35:40
	 */
	public String save() {
		try {
			// 对会议室进行唯一性验证
			int nameAndLocation = this.meetingService.getNameAndLocation(entity.getName(), entity.getLocation(),entity.getId());
			    
			if (nameAndLocation > 0) {
					result = new Result(false, "会议室名称或位置冲突！");
					return RESULT;
				}
			// 保存或修改
			int res = this.meetingService.saveOrUpdateMeeting(entity);
			if (res == 1) {
				String forward = getHttpServletRequest().getRequestURI();
				String queryString = getHttpServletRequest().getQueryString();
				if (queryString != null && !queryString.equals(""))
					forward = forward + "?" + queryString + "&id=" + entity.getId();
				else
					forward = forward + "?id=" + entity.getId();
				result = new Result(true, "保存会议室维护信息成功", forward, false);
			} else {
				result = new Result(true, "更新会议室维护信息成功");
			}
		} catch (ManagerException ex) {
			result = new Result(false, ex.getErrMsg(), "", false);
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(false, "会议室信息保存异常！", "", false);
		} finally {
			// 4、返回提示信息到页面
			return RESULT;
		}
	}

	/**
	 * 查看会议厅
	 * @return java.lang.String
	 * @author jucj
	 * @create 2017-1-19 11:35:40
	 */
	public String show() {
		String id = this.getHttpServletRequest().getParameter("id");
		if (id != null && StringUtils.isNotEmpty(id)) {
			Meeting meeting = meetingService.get(id);
			if (meeting != null) {
				String meetingId = meeting.getId();
				if (meetingId != null && StringUtils.isNotEmpty(meetingId)) {
					entity = meetingService.get(meetingId);
					return VIEW;
				}
			}
		}
		return RESULT;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}
}
