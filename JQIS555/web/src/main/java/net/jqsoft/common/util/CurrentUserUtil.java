package net.jqsoft.common.util;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zcframework.security.SpringSecurityUtils;
import org.zcframework.security.model.Department;
import org.zcframework.security.object.Loginer;
/**
 * 获取登录用户信息的工具类
 * @author			xiaohf
 * @createDate		2016年11月29日 下午2:31:19
 */
public class CurrentUserUtil {

	private static Log log = LogFactory.getLog(CurrentUserUtil.class);

	/**
	 * 获取当前用户所属地区a
	 * @param isCounty 是否获取当前用户所在县
	 */
	public static String getSysUserAreaI(boolean isCounty){
		Loginer loginer = SpringSecurityUtils.getCurrentUser();
		String areaId = loginer.getAreaCode();
		if(areaId == null){
			return null;
		}
		if (isCounty){
			areaId = areaId.substring(0,6);
		}
		return areaId;
	}

	/**
	 * 获取当前用户所在机构
	 * @author			xiaohf
	 * @createDate		2016年11月29日下午2:30:26                                                                 
	 * @return
	 */
	public static String getOrgId(){
		Loginer loginer = SpringSecurityUtils.getCurrentUser();
		String deptId = "-1";
		if (loginer != null) {
        	//找出当前登录用户拥有几个部门
            List<Department> depts = loginer.getDepartments();
            //仅获取第一个部门
            if(depts.size() > 0){
            	deptId = depts.get(0).getId();
            }
            
        }
		return deptId;
	}

	/**
	 * 获取当前用户所在机构名称
	 * @author			jinliang
	 * @createDate		2016年12月05日上午09:15:26
	 * @return
	 */
	public static String getOrgName() {
		Loginer loginer = SpringSecurityUtils.getCurrentUser();
		String deptName = "-1";
		if (null != loginer) {
			// 找出当前登录用户拥有几个部门
			List<Department> depts = loginer.getDepartments();
			// 仅获取第一个部门
			if(depts.size() > 0) {
				deptName = depts.get(0).getName();
			}

		}
		return deptName;
	}

	/**
     * 判断是否为管理员
     */
    public static boolean isadmin() {
    	Loginer loginer = SpringSecurityUtils.getCurrentUser();
        if (loginer != null) {
        	//找出当前登录用户拥有几个角色
            List<org.zcframework.security.model.Role> roles = loginer.getRoles();
	    	for (org.zcframework.security.model.Role r:roles) {
	    		if(SystemConstants.SYSADMIN.equals(r.getRoleName()))
	    		{
	    			return true;
	    		}
			}
        }
    	return false;
    }

    /**
     * 
     * @author			xiaohf
     * @createDate		2016年12月1日下午6:38:21                                                                 
     * @return
     */
	public static Loginer getCurrentUser() {
		Loginer loginer = SpringSecurityUtils.getCurrentUser();
		return loginer;
	}
}


