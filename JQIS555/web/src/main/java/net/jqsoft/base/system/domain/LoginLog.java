/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.base.system.domain;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.zcframework.core.domain.support.Entity;

/**
* @author 
* @datetime 2016-12-5
* @desc
* @see
*/
public class LoginLog extends Entity<String> {
	
	
	/** 登录用户ID */
    private String  userId;
	
	/** 用户名 */
    private String  userName;
	
	/** 真实姓名 */
    private String  realName;
	
	/** 登录IP */
    private String  loginIp;
	
	/** 登录时间 */
    private Date  loginTime;
	
	/** 类型 */
    private String  type;
    //------------------GETTER & SETTER--------------------
    public String  getUserId(){
        return userId;
    }
    public void setUserId(String userId){
        this.userId=userId;
    }
    public String  getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName=userName;
    }
    public String  getRealName(){
        return realName;
    }
    public void setRealName(String realName){
        this.realName=realName;
    }
    public String  getLoginIp(){
        return loginIp;
    }
    public void setLoginIp(String loginIp){
        this.loginIp=loginIp;
    }
    public Date  getLoginTime(){
        return loginTime;
    }
    public void setLoginTime(Date loginTime){
        this.loginTime=loginTime;
    }
    public String  getType(){
        return type;
    }
    public void setType(String type){
        this.type=type;
    }
    
    /**
	 * 记录日志-新增
	 * @return
	 */
	public String newString() {
		StringBuffer sb = new StringBuffer("");
			sb.append("登录用户ID：").append(this.getUserId());
			sb.append("用户名：").append(this.getUserName());
			sb.append("真实姓名：").append(this.getRealName());
			sb.append("登录IP：").append(this.getLoginIp());
			sb.append("登录时间：").append(this.getLoginTime());
			sb.append("类型：").append(this.getType());
		return sb.toString();
	}
	
	/**
	 * 记录日志-修改
	 * @param LoginLog 原始未更改前
	 * @return
	 */
	public String compareDiff(LoginLog LoginLog){
		StringBuffer sb = new StringBuffer("");
			if(!StringUtils.equals(this.getUserId(),LoginLog.getUserId())) {
				sb.append("，").append("登录用户ID：").append(LoginLog.getUserId()).append(" 修改为：").append(this.getUserId());
			}
			if(!StringUtils.equals(this.getUserName(),LoginLog.getUserName())) {
				sb.append("，").append("用户名：").append(LoginLog.getUserName()).append(" 修改为：").append(this.getUserName());
			}
			if(!StringUtils.equals(this.getRealName(),LoginLog.getRealName())) {
				sb.append("，").append("真实姓名：").append(LoginLog.getRealName()).append(" 修改为：").append(this.getRealName());
			}
			if(!StringUtils.equals(this.getLoginIp(),LoginLog.getLoginIp())) {
				sb.append("，").append("登录IP：").append(LoginLog.getLoginIp()).append(" 修改为：").append(this.getLoginIp());
			}
			if(!StringUtils.equals(this.getType(),LoginLog.getType())) {
				sb.append("，").append("类型：").append(LoginLog.getType()).append(" 修改为：").append(this.getType());
			}
		return sb.toString();
	}
	
}


