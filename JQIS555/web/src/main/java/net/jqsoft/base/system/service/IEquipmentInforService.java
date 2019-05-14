/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.service;


import net.jqsoft.base.system.domain.EquipmentInfor;
import net.jqsoft.base.system.domain.Project;

import org.zcframework.core.service.IBaseEntityService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * IEquipmentInforService
 * @author wangtao@jqsoft.net
 * @version 1.0
 */
public interface IEquipmentInforService extends IBaseEntityService<EquipmentInfor> {
	/**
     * 保存服务器信息
     * @param entity 服务器实体对象
     * @author wangtao
     * @createTime 2017-10-17
    */
    public int saveOrUpdateEquipment(EquipmentInfor entity);
    /**
	 * 删除服务器信息
	 * @author wangtao
	 * @date: 2017-10-25
	 */
	public void deleteEquipInfo(String id);
	/**
	 * 删除服务器byProjId信息
	 * @author wangtao
	 * @date: 2017-10-25
	 */
	public void deleteEquipInfoByProjId(String id);
	/**
	 * 根据服务器名获得实体
	 * @author wangtao
	 * @date: 2017-10-25
	 */
	List<EquipmentInfor> getByServerIP(String ServerIp);
	/**
	 * 根据项目Id获取服务器表中的项目ID
	 * @author wangtao
	 * @date: 2017-10-25
	 */
	List<EquipmentInfor> getProjectIdById(String projectId);

	/**
	 * 根据服务器名获取服务器实体
	 * @author wangtao
	 * @date: 2018-03-23
	 */
	List<EquipmentInfor> getServerByServerName(String serverName);

}
