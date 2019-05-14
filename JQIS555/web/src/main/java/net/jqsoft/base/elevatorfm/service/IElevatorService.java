package net.jqsoft.base.elevatorfm.service;

import net.jqsoft.base.elevatorfm.domain.Elevator;
import org.zcframework.core.service.IBaseEntityService;

import java.util.Map;

/**
 * 电梯管理service接口
 * @author: fumin
 * @date: 2018/12/5
 */
public interface IElevatorService extends IBaseEntityService<Elevator>{

    /**（修改或新增时）保存电梯信息
     * @author fumin
     * @create 2018/12/7
     * @Description:
     * entity: 表单填写的数据实体类
     * types: add-新增 edit-修改
     **/
    public int saveOrUpdateElevator(Elevator entity,String types);

    /**逻辑删除电梯信息
     * @author fumin
     * @create 2018/12/7
     * @Description: 根据id将其对应数据的删除标识设为1
     */
    public boolean logicalDeleteElevator(Elevator entity);

    /**@author fumin
     * @create 2018/12/7
     * @Description: 获得电梯编码为当前值的数据条数
     */
    public int getInfoByCode(String elevatorCode);

    /**@author fumin
     * @create 2018/12/7
     * @Description: 根据id获得一条对应数据
     */
    public Elevator getInfoById (String id);

    /**@author fumin
     * @create 2018/12/28
     * @Description: 根据id获得对应故障状态数据的数量
     */
    public int getTroubleStatus (String id);

    /**@author fumin
     * @create 2018/12/28
     * @Description: 根据id获得对应维保状态数据的数量
     */
    public int getMaintenanceStatus (String id);


    /**更新电梯状态
     * @author: fumin
     * @date: 2018/12/7
     * @description: 正常：当前电梯当前没有“维保中”的维保记录，且当前没有“维保中”，“待确认”的故障申报记录。
    故障中：此电梯当前有“维保中”的维保记录，或者当前有“维保中”，“待确认”的故障申报记录。
     * @return:
     */
    public boolean updateElevatorStatusById(String elevatorId);

    /**@author fumin
     * @create 2019/1/3
     * @Description: 获得在同一地区同一电梯号的数量
     */
    public int getInfoByArea (Map<String, Object> parames);
}
