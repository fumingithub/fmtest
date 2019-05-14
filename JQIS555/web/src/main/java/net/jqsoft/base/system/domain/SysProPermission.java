/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.base.system.domain;

import org.zcframework.core.domain.support.Entity;
import org.apache.commons.lang3.StringUtils;

/**
* @author 
* @datetime 2018-1-18
* @desc
* @see
*/
public class SysProPermission extends Entity<String> {


	/**  */
    private String  projectId;

	/**  */
    private String  userId;
	
	/**  */
    private String  serverId;
    //------------------GETTER & SETTER--------------------
    public String  getProjectId(){
        return projectId;
    }
    public void setProjectId(String projectId){
        this.projectId=projectId;
    }
    public String  getUserId(){
        return userId;
    }
    public void setUserId(String userId){
        this.userId=userId;
    }
    public String  getServerId(){
        return serverId;
    }
    public void setServerId(String serverId){
        this.serverId=serverId;
    }
    
    /**
	 * 记录日志-新增
	 * @return
	 */
	public String newString() {
		StringBuffer sb = new StringBuffer("");
			sb.append("：").append(this.getProjectId());
			sb.append("：").append(this.getUserId());
			sb.append("：").append(this.getServerId());
		return sb.toString();
	}
	
	/**
	 * 记录日志-修改
	 * @param SysProPermission 原始未更改前
	 * @return
	 */
	public String compareDiff(SysProPermission SysProPermission){
		StringBuffer sb = new StringBuffer("");
			if(!StringUtils.equals(this.getProjectId(),SysProPermission.getProjectId())) {
				sb.append("，").append("：").append(SysProPermission.getProjectId()).append(" 修改为：").append(this.getProjectId());
			}
			if(!StringUtils.equals(this.getUserId(),SysProPermission.getUserId())) {
				sb.append("，").append("：").append(SysProPermission.getUserId()).append(" 修改为：").append(this.getUserId());
			}
			if(!StringUtils.equals(this.getServerId(),SysProPermission.getServerId())) {
				sb.append("，").append("：").append(SysProPermission.getServerId()).append(" 修改为：").append(this.getServerId());
			}
		return sb.toString();
	}
	
}


