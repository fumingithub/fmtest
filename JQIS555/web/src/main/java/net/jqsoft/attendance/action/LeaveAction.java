package net.jqsoft.attendance.action;

import net.jqsoft.attendance.domain.Leave;
import net.jqsoft.attendance.service.ILeaveService;
import net.jqsoft.base.system.domain.User;
import net.jqsoft.base.system.service.impl.UserService;
import net.jqsoft.common.action.MyEntityAction;
import net.jqsoft.common.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.view.support.Result;
import org.zcframework.core.view.support.entity.BaseEntityAction;
import org.zcframework.security.SpringSecurityUtils;
import org.zcframework.security.object.Loginer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 请假管理Action层
 * @author: liujun
 * @createTime: 2017-01-16
 */
public class LeaveAction extends MyEntityAction<Leave,ILeaveService> {

    @Autowired
    private ILeaveService leaveService;

    @Autowired
    private UserService userService;

    @Override
    protected ILeaveService getEntityManager(){return leaveService;}

    /** 标识编辑、新增 */
    private boolean edit;     

    /** 查看标识 */
    private boolean view = false;

    /** ajax从前端传过来 */
    String delItems;

    /**
     * 业务方法描述 分页
     * @return  java.lang.String
     * @author   liujun
     * @createTime 2017-01-17
     */
    public String list(){
        return super.list();
    }

    /**
     * 业务方法描述 保存或修改或查看跳转
     * @return  java.lang.String
     * @author    liujun
     * @createTime 2017-01-17
     */
    public String toSaveAndEdit(){

        if( view ){
            //查看
            entity= leaveService.get(entity.getId());
            return  INPUT;
        }
        if( StringUtils.isNotEmpty(entity.getId()) && !view ){
            //修改跳转
            entity= leaveService.get(entity.getId());
            edit=true;
        }else{
            //新增跳转
            edit=false;
        }
        return  INPUT;
    }
    /**
     * 业务方法描述 删除请假信息
     * @return  java.lang.String
     * @author    liujun
     * @createTime 2017-01-17
     */
    public String delete() {
        String[] item = delItems.split(",");
        for (int i = 0; i < item.length; i++) {
            leaveService.removeById(item[i]);
        }
        result = new Result(true,"删除成功！");
        return RESULT;
    }
    /**
     * 业务方法描述 保存或修改请假信息
     * @return  java.lang.String
     * @author   liujun
     * @createTime 2017-01-17
     */
    public String saveAndEdit(){

        if(entity.getId()!=null &&!"".equals(entity.getId())){
            Loginer loginer = SpringSecurityUtils.getCurrentUser();
            User user = userService.getUserById(loginer.getId().toString());
            //更新请假天数
            entity.setLeaveDays(daysBetween(entity.getLeaveStartTime(), entity.getLeaveEndTime()));
            //根据当前登陆人更新人的ID
            if(null!=user){
                entity.setUpdator(user.getId());
            }
            //更新人的名字
            entity.setUpdatorName(SpringSecurityUtils.getCurrentUserName());
            //更新时间
            entity.setUpdateTime(CommonUtil.getCurrentTime());
            leaveService.update(entity);
            result = new Result(true,"更新成功！");
        }else{
            entity.setId(CommonUtil.getUUID());
            Loginer loginer = SpringSecurityUtils.getCurrentUser();
            User user = userService.getUserById(loginer.getId().toString());
            //新增user_id
            if(null!=user){
                entity.setUserId(user.getId());
            }
            //请假天数
            entity.setLeaveDays(daysBetween(entity.getLeaveStartTime(), entity.getLeaveEndTime()));
            //创建人的id
            entity.setCreator(user.getId());
            //创建人的名字
            entity.setCreatorName(SpringSecurityUtils.getCurrentUserName());
            //创建时间
            entity.setCreateTime(CommonUtil.getCurrentTime());
            leaveService.create(entity);
            result = new Result(true, "保存成功！");
        }
        return RESULT;
    }

    /**
     * 公共业务方法描述 计算日期差
     * @return  java.lang.Double
     * @author    liujun
     * @createTime 2017-01-18
     */
    private Double daysBetween(Date start,Date end){

        double between_days=0.0;
        try
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(start);
            long time1 = cal.getTimeInMillis();
            cal.setTime(end);
            long time2 = cal.getTimeInMillis();
            between_days=(time2-time1)/(1000*3600*24)+1.0;
        } catch(Exception e){
            e.printStackTrace();
        }
        return between_days;
    }
    // =====================set and get=============

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public boolean isView() {
        return view;
    }

    public void setView(boolean view) {
        this.view = view;
    }

    public String getDelItems() {
        return delItems;
    }

    public void setDelItems(String delItems) {
        this.delItems = delItems;
    }

}
