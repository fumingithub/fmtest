package net.jqsoft.base.system.service;

import net.jqsoft.base.system.domain.Travel;
import org.zcframework.core.service.IBaseEntityService;

import java.util.List;

public interface ITravelService extends IBaseEntityService<Travel> {
    /**
     * 保存景点信息
     * @param entity 景点实体类
     * @return int 影响行数
     * @author fm
     **/
    public int saveOrUpdateTravel(Travel entity);


    /**
     * 根据景点编码查询一条记录
     * @param code 组织机构代码
     * @return <net.jqsoft.base.system.domain.Travel>
     **/
    public Travel getInfoByCode(String code);

    /**
     * 获取所有记录集合
     * @return java.util.List<net.jqsoft.base.system.domain.Travel>
     * @author fm
     **/
    public List<Travel> getAll();

}
