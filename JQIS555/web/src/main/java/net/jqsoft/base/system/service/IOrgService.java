/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.base.system.service;

import net.jqsoft.base.system.domain.Org;
import org.zcframework.core.service.IBaseEntityService;

import java.util.List;

/**
 * 部门管理Service接口层
 * @author xuhaigang
 * @createTime 2016-04-25 17:30
 */
public interface IOrgService extends IBaseEntityService<Org> {

    /**
     * 根据code获得组织机构
     * @param code 部门编码
     * @param id   部门id
     * @return Org 单个部门对象
     * @author xuhaigang
     * @createTime 2017-01-17 13:32:58
     */
    public Org getUnitByCode(String code, String id);

    /**
     * 根据用户名id删除
     * @param insId   部门id
     * @param updator 修改人id
     * @author xuhaigang
     * @createTime 2017-01-17 13:42:58
     */
    public void deleteById(String insId, String updator);

    /**
     * 根据父id查询递归所有子机构
     * @param parentId 父级部门id
     * @param areaId   区域id
     * @return List<Org> 部门List集合
     * @author xuhaigang
     * @createTime 2017-01-17 13:52:58
     */
    public List<Org> getUnitByParentId(String parentId, String areaId);

    /**
     * 根据父id查询所有子机构
     * @param parentId 父级部门id
     * @return List<Org> 部门List集合
     * @author xuhaigang
     * @createTime 2017-01-17 14:02:42
     */
    public List<Org> getUnitByPid(String parentId);
}
