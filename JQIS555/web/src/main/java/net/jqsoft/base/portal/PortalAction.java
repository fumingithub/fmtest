/**
 * Copyright (c) 2013-2015 jqsoft.net
 */
package net.jqsoft.base.portal;

import java.util.ArrayList;
import java.util.List;

import net.jqsoft.base.system.domain.News;
import net.jqsoft.base.system.domain.Notice;
import net.jqsoft.base.system.domain.User;
import net.jqsoft.base.system.service.INewsService;
import net.jqsoft.base.system.service.INoticeService;
import net.jqsoft.base.system.service.IUserService;

import net.jqsoft.base.system.service.impl.ResourceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.logger.ILogger;
import org.zcframework.core.logger.LoggerFactory;
import org.zcframework.core.view.support.BaseAction;
import org.zcframework.security.SpringSecurityUtils;

/**
 * @author: jetyou@foxmail.com
 * @date: 2015/4/30 0030-14:37 
 * @desc： portal页面
 */
public class PortalAction extends BaseAction {

	public static final String ADMIN = "admin";
	public static final String HOME = "home";
	public static final String LOGOUT = "logout";
	public static final String NEWS = "news";
	public static final String QUICK = "quick";
	public static final String NOTICE = "notice";
	public static final String FORGET = "forget";
	public static final String RESET = "reset";

	@Autowired
	private INoticeService noticeService;
	@Autowired
	private IUserService userService;
	private String tokenId;
	private String isLock;// 链接是否失效标志
	private List<Notice> noticeList = new ArrayList<Notice>();
	private User user;

	/**
	 * 插件安装页面
	 * 
	 * @return
	 */
	public String activeX() {
		return "activeX";
	}

	public String execute() {
		return admin();
	}

	/**
	 * 后台管理页面
	 * 
	 * @return
	 */
	public String admin() {
		return ADMIN;
	}

	/**
	 * 修改HOME页面的公告信息显示
	 * 
	 * @author guoxx
	 * @createDate 2017年1月18日上午11:27:04
	 * @return
	 */
	public String home() {
		// 通知公告前5条
		noticeList = noticeService.getNoticeListTop5();
		// 用户信息
		List<User> userlist = this.userService.getUserByUserName(SpringSecurityUtils.getCurrentUserName());
		if (userlist.size() > 0)
			user = userlist.get(0);
		return HOME;
	}

	/**
	 * login页面
	 * 
	 * @return
	 */
	public String login() {
		return LOGIN;
	}

	/**
	 * logout页面，可以进行一些session清理
	 * 
	 * @return
	 */
	public String logout() {
		return LOGOUT;
	}

	/**
	 * 新闻页面
	 * 
	 * @return
	 */
	public String news() {
		return NEWS;
	}

	/**
	 *
	 * @return
	 */
	public String notice() {

		return NOTICE;
	}

	/**
	 *
	 * @return
	 */

	public String quick() {

		return QUICK;
	}

	/**
	 * @author Taodd
	 * 
	 * @desc 忘记密码
	 */
	public String forgetPassword() {
		return FORGET;
	}

	/**
	 * @author Taodd
	 * 
	 * @desc 忘记密码
	 */
	public String resetPassword() {
		if (null != getSession().getServletContext().getAttribute(tokenId)) {
			isLock = "0";
		}
		return RESET;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getIsLock() {
		return isLock;
	}

	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}

	public List<Notice> getNoticeList() {
		return noticeList;
	}

	public void setNoticeList(List<Notice> noticeList) {
		this.noticeList = noticeList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
