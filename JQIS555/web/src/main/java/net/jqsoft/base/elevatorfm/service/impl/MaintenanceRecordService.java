package net.jqsoft.base.elevatorfm.service.impl;

import com.fr.third.org.apache.poi.util.StringUtil;
import net.jqsoft.base.elevatorfm.dao.MaintenanceRecordDao;
import net.jqsoft.base.elevatorfm.domain.MaintenanceRecord;
import net.jqsoft.base.elevatorfm.service.IMaintenanceRecordService;
import net.jqsoft.common.exception.ManagerException;
import net.jqsoft.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zcframework.core.dao.IEntityDao;
import org.zcframework.core.service.BaseEntityService;
import org.zcframework.security.SpringSecurityUtils;
import org.zcframework.security.object.Loginer;

import java.util.Date;

/**
 * @author: fumin
 * @date: 2018/12/17
 * @description:
 */
@Service
public class MaintenanceRecordService extends BaseEntityService<MaintenanceRecord> implements IMaintenanceRecordService{
    @Autowired
    private MaintenanceRecordDao maintenanceRecordDao;

    @Override
    protected IEntityDao<MaintenanceRecord> getDao(){
        return maintenanceRecordDao;
    }

    /**保存技师填写的维保信息
     * @author fumin
     * @create 2018/12/24
     * @Description:
     * entity: 表单填写的数据实体类
     * types: add-新增 edit-修改
     **/
    @Override
    public int  technicianMaintenanceSave(MaintenanceRecord entity,String types){
        int nis = 0;
        try {
            Loginer loginer = SpringSecurityUtils.getCurrentUser();
            //编辑
            if ("edit".equals(types)) {
                entity.setUpdatorName(SpringSecurityUtils.getCurrentUserName());
                entity.setUpdatorTime(new Date());
                this.maintenanceRecordDao.update(entity);
                nis = 1;
            }
            //新增、技师维保
            else {
                //自动生成新的唯一ID，和创建人信息并保存
                entity.setId(CommonUtil.getUUID());
                entity.setIsDel("0");
                entity.setCreator(loginer.getId().toString());
                entity.setCreatorName(SpringSecurityUtils.getCurrentUserName());
                entity.setCreatorTime(new Date());
                this.maintenanceRecordDao.maintenanceAdd(entity);
                nis = 2;
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ManagerException("100200", new String[]{"维保信息保存异常！（BIZ）"});
        }
      return nis;
    }

    /**逻辑删除维保记录信息
     * @author fumin
     * @create 2018/12/25
     * @Description: 根据id将其对应数据的删除标识设为1
     */
    @Override
    public boolean logicalDeleteElevator(MaintenanceRecord entity){
        //用于删除成功后的提示信息
        boolean flag;
        try {
            entity.setIsDel("1");
            entity.setDeleterName(SpringSecurityUtils.getCurrentUserName());
            entity.setDeleterTime(new Date());
            this.maintenanceRecordDao.logicalDelMaintenanceRecord(entity);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    /**物业确认-维保记录信息
     * @author fumin
     * @create 2018/12/26
     * @Description: 根据id将其对应数据的删除标识设为1
     */
   @Override
    public boolean confirmMaintenanceRecord(MaintenanceRecord entity){
       //用于保存成功后的提示信息
       boolean flag = true;
       try {
           //维保状态更改为已完成，并保存表单信息
           entity.setMaintenanceStatus("2");
           entity.setConfirmorName(SpringSecurityUtils.getCurrentUserName());
           entity.setConfirmorTime(new Date());
           this.maintenanceRecordDao.confirmMaintenanceRecord(entity);
       }catch (Exception e){
           flag = false;
           e.printStackTrace();
       }
        return flag;
    }

    /**
     * 根据elevatorTroubleId获得维保实体
     * @author: fumin
     * @date: 2018/12/27
     * @description: elevatorTroubleId为实体外键
     * @return: MaintenanceRecord
     */
    @Override
    public MaintenanceRecord getMaintenanceRecord(String elevatorTroubleId){
        return this.maintenanceRecordDao.getMaintenanceRecord(elevatorTroubleId);
    }
}
