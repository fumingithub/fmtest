package net.jqsoft.base.elevatorfm.dao;

import net.jqsoft.base.elevatorfm.domain.Elevator;
import org.springframework.stereotype.Repository;
import org.zcframework.core.dao.entity.IBatisEntityDao;

import java.util.List;
import java.util.Map;

/**电梯信息管理 Dao 层
 * @author: Administrator
 * @date: 2018/12/5
 * @description:
 */
@Repository
public class ElevatorDao extends IBatisEntityDao<Elevator>{
    /**
     * 新增电梯信息
     * @author: fumin
     * @date: 2018/12/5
     * @Descriabe: entity 电梯实体
     */
    public Object saveElevator(Elevator entity){
        return this.insert("Elevator.insert",entity);
    }

    /**
     * 更新电梯信息
     * @author: fumin
     * @date: 2018/12/5
     */
    public Object updateElevator(Elevator entity){
        return this.update("Elevator.update",entity);
    }

    /**
     * 逻辑删除电梯信息
     * @author: fumin
     * @date: 2018/12/7
     */
    public int logicalDeleteElevator(Elevator entity){
        return this.update("Elevator.logicalDeleteElevator",entity);
    }

    /**@author fumin
     * @create 2018/12/18
     * @Description: 获得电梯编码为当前值的数据条数
     */
    public int getInfoByCode (String elevatorCode){
        return this.findUniqueBy("Elevator.getInfoByCode","elevatorCode",elevatorCode);
    }
    /**@author fumin
     * @create 2018/12/18
     * @Description: 根据id获得一条对应数据
     */
    public Elevator getInfoById (String id){
        return this.findUniqueBy("Elevator.getInfoById","id",id);
    }
    /**@author fumin
     * @create 2018/12/28
     * @Description: 获得故障状态 1-已提交 2-维保中 3-待确认 4-已完成
     */
    public int getTroubleStatus (String id){
        return this.findUniqueBy("Elevator.getTroubleStatus","id",id);
    }
    /**@author fumin
     * @create 2018/12/28
     * @Description: 获得维保状态 0-维保中 1-已完成
     */
    public int getMaintenanceStatus (String id){
        return this.findUniqueBy("Elevator.getMaintenanceStatus","id",id);
    }

    /**@author fumin
     * @create 2019/1/3
     * @Description: 获得在同一地区同一电梯号的数量
     *
     * public int updateTroubleStatus(Map<String, Object> parame){
    return this.update("TroubleDeclare.updateTroubleStatus",parame);
    }
     */
    public int getInfoByArea (Map<String, Object> parames){
        return this.findUniqueBy("Elevator.getInfoByArea",parames);
    }

}
