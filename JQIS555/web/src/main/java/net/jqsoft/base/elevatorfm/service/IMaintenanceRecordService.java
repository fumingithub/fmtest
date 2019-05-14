package net.jqsoft.base.elevatorfm.service;

import net.jqsoft.base.elevatorfm.domain.MaintenanceRecord;
import org.zcframework.core.service.IBaseEntityService;

/**
 * 电梯维保service接口层
 */
public interface IMaintenanceRecordService extends IBaseEntityService<MaintenanceRecord>{

    /**保存技师维保信息
     * @author fumin
     * @create 2018/12/24
     * @Description:
     * entity: 表单填写的数据实体类
     * types: add-新增 edit-修改
     **/
    public int technicianMaintenanceSave(MaintenanceRecord entity,String types);

    /**逻辑删除维保记录信息
     * @author fumin
     * @create 2018/12/25
     * @Description: 根据id将其对应数据的删除标识设为1
     */
        public boolean logicalDeleteElevator(MaintenanceRecord entity);

    /**物业确认-维保记录信息
     * @author fumin
     * @create 2018/12/26
     * @Description:
     */
    public boolean confirmMaintenanceRecord(MaintenanceRecord entity);


    /**
     * 根据elevatorTroubleId获得维保实体
     * @author: fumin
     * @date: 2018/12/27
     * @description: elevatorTroubleId为实体外键
     * @return: MaintenanceRecord
     */
    public MaintenanceRecord getMaintenanceRecord(String elevatorTroubleId);
}
