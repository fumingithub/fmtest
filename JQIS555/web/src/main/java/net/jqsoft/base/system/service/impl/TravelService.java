package net.jqsoft.base.system.service.impl;

import com.google.common.collect.Maps;
import net.jqsoft.base.system.dao.TravelDao;
import net.jqsoft.base.system.domain.Travel;
import net.jqsoft.base.system.service.ITravelService;
import net.jqsoft.common.exception.ManagerException;
import net.jqsoft.common.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zcframework.core.dao.IEntityDao;
import org.zcframework.core.service.BaseEntityService;

import java.util.List;
import java.util.Map;

/**
 * @author: fm
 * @date: 2018/11/6
 * @description:
 */
@Service
public class TravelService extends BaseEntityService<Travel> implements ITravelService {
    @Autowired
    private TravelDao travelDao;

    @Override
    protected IEntityDao<Travel> getDao() { return travelDao; }

    @Override
    public int saveOrUpdateTravel(Travel entity) {
        int result = 0; // 新增或修改
        try {
            if (StringUtils.isNotEmpty(entity.getId()) && !"undefined".equals(entity.getId())) { // 修改
                this.travelDao.update(entity);
                result = 2;
            } else { // 新增
                entity.setId(CommonUtil.getUUID());
                this.travelDao.create(entity);
                result = 1;
            }
            return result;
        } catch (ManagerException ex) {
            throw ex;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ManagerException("100200", new String[]{"景点信息保存异常！（BIZ）"});
        } finally {

        }
    }


    @Override
    public Travel getInfoByCode(String code) {
        return this.travelDao.findUniqueBy("Travel.getInfoByCode", "code", code);
    }

    @Override
    public List<Travel> getAll() {
        return this.travelDao.getSqlMapClientTemplate().queryForList("Travel.getAllTravel");

    }

}
