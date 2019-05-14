/**
 * Copyright (c) 2013-2015 jqsoft.net
 */
package net.jqsoft.base.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import net.jqsoft.base.system.dao.ResourceDao;
import net.jqsoft.base.system.domain.Resources;
import net.jqsoft.base.system.service.IResourceService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zcframework.core.dao.IEntityDao;
import org.zcframework.core.logger.ILogger;
import org.zcframework.core.logger.LoggerFactory;
import org.zcframework.core.service.BaseEntityService;

import java.util.*;

/**
 * @author: zuoc
 * @date: 2015-06-17
 * @desc: 资源管理Service
 */
@Service
public class ResourceService extends BaseEntityService<Resources> implements IResourceService {

    private ILogger LOGGER= LoggerFactory.getOperationLogger(ResourceService.class);

    @Autowired
    private ResourceDao resourceDao;


    @Override
    protected IEntityDao<Resources> getDao() {
        return resourceDao;
    }
    
    /**
     * 根据父id获取子菜单
     * @return
     */
    public List<Resources> queryResourceByPid(String pid) {
    	return resourceDao.findBy("Resources.queryResourceByPid","pid",pid);
    }
    
    /**
     * 查询所有资源
     * @return
     */
    public List<Resources> queryAll(String roleId) {
    	if(StringUtils.isBlank(roleId))
    		return resourceDao.find("Resources.queryAll",(Object)null);
    	else
    		return resourceDao.findBy("Resources.queryAll","roleId",roleId);
    }

    /**
     * 通过角色ID返回全部资源，并在resources标注某个resources是否被某个角色拥有，以resources.isCheck=true标注
     *
     * @param rid 角色ID
     * @return
     */
    public List<Resources> queryResourceByRoleId(String rid) {
        return resourceDao.find("Resources.getResourcesByRole", rid);
    }

    /**
     * 更新角色权限
     * @param role 角色ID
     * @param resourceId 资源ID数组
     */
    public void updateAuth(String roleId, String[] resourceId) {
        //先删除ROLE的权限
        resourceDao.remove("Resources.deleteAuth",roleId);
        //重新赋权
        Map<String,String> parameters = null;
        for (String r : resourceId) {
            parameters = new HashMap<String,String>();
            parameters.put("roleId",roleId);
            parameters.put("resourceId",r);
            resourceDao.insert("Resources.insertAuth", parameters);
        }       
        LOGGER.info("更新角色{}的权限:{}",roleId, Arrays.toString(resourceId));
    }

    /**
     * 返回菜单列表
     * @param uid
     * @return
     */
    public JSONArray getMenu(String uid) {
        List<Resources> memuList = resourceDao.findBy("Resources.getMenu","user",uid);
        return treeMenuList(memuList, "");
    }

    /**
     * 菜单树形结构：json数组格式
     * @param menuList   菜单集合
     * @param parentCode 父code
     * @return com.alibaba.fastjson.JSONArray
     * @author jinliang
     * @create 2016-10-13 17:17:52
     **/
    private JSONArray treeMenuList(List<Resources> menuList, String parentCode) {
        JSONArray childMenu = new JSONArray();
        for (Resources menu : menuList) {
            if (parentCode.equals(StringUtils.trimToEmpty(menu.getParentCode()))) {
                JSONObject jo = (JSONObject) JSONObject.toJSON(menu);
                JSONArray c_node = treeMenuList(menuList, menu.getId());
                jo.put("children", c_node);
                childMenu.add(jo);
            }
        }
        return childMenu;
    }

    /**
     * 删除资源
     * @param id 资源ID
     * 同时删除对应的资源角色关系
     */
	public void deleteResource(String id) {
		resourceDao.remove("Resources.deleteAuthByResourceId", id);
		resourceDao.remove("Resources.deleteById", id);
	}
	
    /**
     * 根据资源编码获取菜单
     * add by liuj
     * @return
     */
    public Resources getResourcesByCode(String code,String id) {
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("code", code);
    	param.put("id", id);
        return resourceDao.findUniqueBy("Resources.getResourcesByCode",param);
    }

    @Override
    public List<Resources> queryResourceListByParentCode(String parentCode, String resourceName) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("parentCode", parentCode);
        param.put("resourceName", resourceName);
        return this.resourceDao.find("Resources.getResourceListByParentCode", param);
    }
}
