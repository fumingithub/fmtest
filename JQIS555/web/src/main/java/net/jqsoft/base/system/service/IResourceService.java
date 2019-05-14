/**
 * Copyright (c) 2013-2015 jqsoft.net
 */
package net.jqsoft.base.system.service;

import com.alibaba.fastjson.JSONArray;
import net.jqsoft.base.system.domain.Resources;
import org.zcframework.core.service.IBaseEntityService;

import java.util.List;

/**
 * @author: zuoc
 * @date: 2015-06-17
 * @desc: 资源管理IService
 */
public interface IResourceService extends IBaseEntityService<Resources> {
	
	/**
     * 根据父id获取子菜单
     * @return
     */
    public List<Resources> queryResourceByPid(String pid);
	
	/**
     * 查询所有资源
     * @return
     */
    public List<Resources> queryAll(String roleId);

    /**
     * 通过角色ID返回全部资源，并在resources标注某个resourcess是否被某个角色拥有，以resources.ischeck=true标注
     * @param rid 角色ID
     * @return
     */

    public List<Resources> queryResourceByRoleId(String rid);

    /**
     * 更新角色权限
     * @param role 角色ID
     * @param resourceId 资源ID
     */
    public void updateAuth(String roleId, String[] resourceId);

    /**
     * 删除资源
     * @param id 资源ID
     * 同时删除对应的资源角色关系
     */
    public void deleteResource(String id);

    /**
     * 返回菜单列表
     * @param uid 用户ID
     * @return
     */
    public JSONArray getMenu(String uid);

    /**
     * 根据资源编码获取菜单
     * add by liuj
     * @return
     */
    public Resources getResourcesByCode(String code,String id);

    /**
     * 根据父级资源获取资源List集合，用于zTree搜索
     * @param parentCode   父级资源
     * @param resourceName 资源名称过滤参数
     * @return java.util.List<net.jqsoft.base.system.domain.Resources>
     * @author jinliang
     * @create 2016-12-27 16:11:18
     **/
    public List<Resources> queryResourceListByParentCode(String parentCode, String resourceName);
}
