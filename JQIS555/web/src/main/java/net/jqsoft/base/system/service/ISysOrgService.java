/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.service;

import net.jqsoft.base.system.domain.SysOrg;
import net.jqsoft.common.exception.ManagerException;

import org.zcframework.core.service.IBaseEntityService;

import java.util.List;

/**
 * ISysOrgService
 * @author jinliang@jqsoft.net
 * @version 1.0
 */
public interface ISysOrgService extends IBaseEntityService<SysOrg> {

    /**
     * 保存机构信息
     * @param entity 机构实体类
     * @return int 影响行数
     * @author jinliang
     * @create 2016-11-09 11:39:06
     **/
    public int saveOrUpdateSysOrg(SysOrg entity);

    /**
     * 获取所有的机构List集合，用于zTree展示
     * @param parentId 父级机构编码
     * @return java.util.List<net.jqsoft.base.system.domain.SysOrg>
     * @author jinliang
     * @create 2016-11-09 10:05:21
     **/
    public List<SysOrg> querySysOrgList(String parentId);

    /**
     * 根据父级机构编码获取机构List集合，用于zTree搜索
     * @param parentId   父级机构编码
     * @param sysOrgName 机构名称过滤参数
     * @param transferOrgId 需要调动的机构编码
     * @param filterOrgId 根据当前用户所在的机构id过滤组织机构树
     * @return java.util.List<net.jqsoft.base.system.domain.SysOrg>
     * @author jinliang
     * @create 2016-11-09 10:07:18
     **/
    public List<SysOrg> querySysOrgListByParentId(String parentId, String sysOrgName, String transferOrgId, String filterOrgId);

    /**
     * 根据机构编码查询是否含有子集
     * @param id 机构编码
     * @return java.util.List<net.jqsoft.base.system.domain.SysOrg>
     * @author jinliang
     * @create 2016-11-09 10:09:07
     **/
    public List<SysOrg> hasSonSysOrgById(String id);

    /**
     * 根据机构编码更新启用标识（可递归更新所有子集数据）
     * @param status 启用状态 1-启用 0-禁用
     * @param id 机构编码
     * @return int
     * @author jinliang
     * @create 2016-11-09 10:12:16
     **/
    public int updateStatusById(String status, String id);

    /**
     * 根据机构编码查询一条记录
     * @param code 组织机构代码
     * @return <net.jqsoft.base.system.domain.SysOrg>
     * @author jinliang
     * @create 2016-11-29 08:39:16
     **/
    public SysOrg getInfoByCode(String code);
    
    /**
     * 根据组织机构id更新授权农合行政区划编码
     * @param transferId
     * @param selected
     */
	public void updateAuthAreaCode(String transferId, String selected);
	
	/**
	 * 授权区划编码重复性检验
	 * @param authAreaCode
	 * @return
	 */
	public String queryByAuthAreaCode(String authAreaCode);
	
	
	/**
	 * 根据当前组织机构获取组织机构路径 如：大庆市-大同区。。。。。
	 * @author			xiaohf
	 * @createDate		2016年11月30日上午11:30:05                                                                 
	 * @param id 组织机构ID
	 * @return
	 * @throws ManagerException
	 */
	public List<SysOrg> getOrgPath(String id) throws ManagerException;

	/**
	 * 取消机构授权
	 * @author			xiaohf
	 * @createDate		2016年12月9日下午3:45:40                                                                 
	 * @param id
	 * @throws ManagerException
	 */
	public void cancelAuth(String id) throws ManagerException;

	/**
	 * 获取所有记录集合
	 * @return java.util.List<net.jqsoft.base.system.domain.SysOrg>
	 * @author jinliang
	 * @create 2016-12-22 10:20:03
	 **/
	public List<SysOrg> getAll();
}
