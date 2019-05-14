package net.jqsoft.base.elevatorfm.dao;

import net.jqsoft.base.elevatorfm.domain.MaintenanceRecord;
import org.springframework.stereotype.Repository;
import org.zcframework.core.dao.entity.IBatisEntityDao;

import java.util.Map;

/**
 * @author: fumin
 * @date: 2018/12/17
 * @description:
 */
@Repository
public class MaintenanceRecordDao extends IBatisEntityDao<MaintenanceRecord>{
    /**
     * 维保记录-新增保存
     * @author: fumin
     * @date: 2018/12/24
     */
    public Object maintenanceAdd(MaintenanceRecord entity){
        return this.insert("MaintenanceRecord.insert", entity);
    }

    /**
     * 编辑故障申报信息
     * @author: fumin
     * @date: 2018/12/17
     */
    public Object maintenanceEdit(MaintenanceRecord entity){
        return this.update("MaintenanceRecord.update",entity);
    }
    /**
     * 逻辑删除电梯信息
     *  @author: fumin
     * @date: 2018/12/7
     */
    public int logicalDelMaintenanceRecord(MaintenanceRecord entity){
        return this.update("MaintenanceRecord.logicalDelMaintenanceRecord",entity);
    }

    /**物业确认-维保记录信息
     * @author fumin
     * @create 2018/12/26
     * @Description: 根据id将其对应数据的删除标识设为1
     */
    public int confirmMaintenanceRecord(MaintenanceRecord entity){
        return this.update("MaintenanceRecord.confirmMaintenanceRecord",entity);

    }
    /**
     * 根据elevatorTroubleId获得维保实体
     * @author: fumin
     * @date: 2018/12/27
     * @description: elevatorTroubleId为维保表的外键
     * @return:
     */
    public MaintenanceRecord getMaintenanceRecord(String elevatorTroubleId){
        return (MaintenanceRecord)this.getSqlMapClientTemplate().queryForObject("MaintenanceRecord.getMaintenanceRecord",elevatorTroubleId);
    }


}
