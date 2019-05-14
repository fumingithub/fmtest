package net.jqsoft.base.elevatorfm.service.impl;

import com.fr.web.core.A.I;
import com.google.common.collect.Maps;
import net.jqsoft.base.elevatorfm.dao.ElevatorDao;
import net.jqsoft.base.elevatorfm.domain.Elevator;
import net.jqsoft.base.elevatorfm.service.IElevatorService;
import net.jqsoft.common.exception.ManagerException;
import net.jqsoft.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zcframework.core.dao.IEntityDao;
import org.zcframework.core.service.BaseEntityService;
import org.zcframework.core.view.support.Result;
import org.zcframework.security.SpringSecurityUtils;
import org.zcframework.security.object.Loginer;

import java.util.Date;
import java.util.Map;

/**
 * @author: Administrator
 * @date: 2018/12/5
 * @description:
 */
@Service
public class ElevatorService extends BaseEntityService<Elevator> implements IElevatorService{
    @Autowired
    private ElevatorDao elevatorDao;

    @Override
    protected IEntityDao<Elevator> getDao() {
        return elevatorDao;
    }
    /**
     * 更新保存电梯信息-修改或新增
     * @Description:
     * @author fumin
     * @create 2018/12/7
     * entity: 表单填写的数据实体类
     * type: add-新增 edit-修改
     **/
    @Override
    public int saveOrUpdateElevator(Elevator entity, String types) {
        //用于控制成功后的提示信息类型
        int flag = 0;
        try{
            //获得当前登录信息
            Loginer loginer = SpringSecurityUtils.getCurrentUser();
            //如果是新增，自动生成新的唯一ID并保存
            if ("add".equals(types)){
                entity.setId(CommonUtil.getUUID());
                entity.setStatus("1");
                entity.setIsDel("0");

                entity.setCreator(loginer.getId().toString());
                entity.setCreatorName(SpringSecurityUtils.getCurrentUserName());
                entity.setCreatorTime(new Date());
                this.elevatorDao.saveElevator(entity);
                flag =1;
            }
            //如果是编辑，直接保存表单信息
            else{
                entity.setUpdator(loginer.getId().toString());
                entity.setUpdatorName(SpringSecurityUtils.getCurrentUserName());
                entity.setUpdatorTime(new Date());
                this.elevatorDao.updateElevator(entity);
                flag = 2;
            }
            return flag;
        }catch(Exception e){
            e.printStackTrace();
            throw new ManagerException("100200", new String[]{"电梯信息保存异常！（BIZ）"});
        }finally {
        }
    }

    /**逻辑删除电梯信息
     * @Description:
     * @author fumin
     * @create 2018/12/7
     */
    @Override
    public boolean logicalDeleteElevator(Elevator entity) {
        //用于删除成功后的提示信息
        boolean flag;
        try {
        entity.setIsDel("1");
        entity.setDeleterName(SpringSecurityUtils.getCurrentUserName());
        entity.setDeleterTime(new Date());
        this.elevatorDao.logicalDeleteElevator(entity);

        flag = true;
        }catch (Exception e){
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    /**@author fumin
     * @create 2018/12/7
     * @Description: 获得电梯编码为当前值的数据条数
     */
    @Override
    public int getInfoByCode(String elevatorCode){
        return this.elevatorDao.getInfoByCode(elevatorCode);
    }

    /**@author fumin
     * @create 2018/12/7
     * @Description: 根据id获得一条对应数据
     */
    @Override
    public Elevator getInfoById (String id){
        return this.elevatorDao.getInfoById(id);
    }

    /**@author fumin
     * @create 2018/12/28
     * @Description: 根据id获得对应故障状态数据的数量
     */
    @Override
    public int getTroubleStatus (String id){
        return this.elevatorDao.getTroubleStatus(id);
    }

    /**@author fumin
     * @create 2018/12/28
     * @Description: 根据id获得对应维保状态数据的数量
     */
    @Override
    public int getMaintenanceStatus (String id){
        return this.elevatorDao.getMaintenanceStatus(id);
    }

    /**更新电梯状态
     * @author: fumin
     * @date: 2018/12/7
     * @description: 正常：当前电梯当前没有“维保中”的维保记录，且当前没有“维保中”，“待确认”的故障申报记录。
    故障中：此电梯当前有“维保中”的维保记录，或者当前有“维保中”，“待确认”的故障申报记录。
     * @return:
     */
    @Override
    public boolean updateElevatorStatusById(String elevatorId){
        //用于结果的提示信息
        boolean updateStatus = true;
        try {
            Elevator elevator = this.get(elevatorId);
            //获得故障状态为维保中、待确认的数据数量
            int countTroubleStatus = this.getTroubleStatus(elevatorId);
            //获得维保状态为维保中的数据数量
            int countMaintenanceStatus = this.getMaintenanceStatus(elevatorId);

            //当前电梯当前没有“维保中”的维保记录，且当前没有“维保中”，“待确认”的故障申报记录--status为正常
            if (countTroubleStatus <= 0 && countMaintenanceStatus <= 0){
                elevator.setStatus("1");
            }
            //此电梯当前有“维保中”的维保记录，或者当前有“维保中”，“待确认”的故障申报记录--status为故障中
            else {
                elevator.setStatus("0");
            }
            this.update(elevator);
        }catch (Exception e){
            e.printStackTrace();
            updateStatus = false;
        }
        return updateStatus;
    }

    /**@author fumin
     * @create 2019/1/3
     * @Description: 获得在同一地区同一电梯号的数量
     */
    @Override
    public int getInfoByArea (Map<String, Object> parames){
        return this.elevatorDao.getInfoByArea(parames);
    }
}
