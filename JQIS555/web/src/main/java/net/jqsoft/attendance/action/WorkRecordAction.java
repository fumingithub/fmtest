/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.attendance.action;

import net.jqsoft.attendance.domain.WorkRecord;
import net.jqsoft.attendance.service.IWorkRecordService;
import net.jqsoft.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.view.support.Result;
import org.zcframework.core.view.support.entity.BaseEntityAction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mapp
 * @createTime 2017-01-16
 */
public class WorkRecordAction extends BaseEntityAction<WorkRecord,IWorkRecordService> {

    @Autowired
    private IWorkRecordService workRecordService;

    /**上班开始时间*/
    private static final String WORK_START_TIME = CommonUtil.getProperties("config.properties", "work_start_time");

    /**上班结束时间*/
    private static final String WORK_END_TIME = CommonUtil.getProperties("config.properties", "work_end_time");
    /**格式化时间*/
    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

    /**上班开始时间，上班结束时间 类型Date 用于时间的比较*/
    Date workStartTime = null;
    Date workEndTime = null;

    /**用于区别统计所有人加班时间与个人加班时间界面显示*/
    private String flag;


    /**
     * 重写查询方法，默认查询当天的签到记录
     * @author    mapp
     * @createTime 2017-01-17
     * @param
    */
    public String list(){
        Map<String, Object> params = getActionContext().getParameters();
        // 默认查询当天的签到记录
        if(params.get("starttime") == null){
            params.put("starttime",new Object[]{format1.format(new Date())});
        }
        if(params.get("endtime") == null){
            params.put("endtime",new Object[]{format1.format(new Date())});
        }
        return  super.list();
    }

    /**
     * 签到
     * @author  mapp
     * @createTime 2017-01-17
     * @exception ParseException 时间格式转换异常
    */
    public String signInCard() throws ParseException {
        // String转Date
        workStartTime = format.parse(WORK_START_TIME);
        workEndTime = format.parse(WORK_START_TIME);
        // 两个时间相同时 才可以进行签到操作。 也就是只能对今天的签到记录进行操作
        if(validTimeIsEqual()){
            entity = this.workRecordService.get(entity.getId());
            // 签到逻辑判断
            int n = this.workRecordService.aidantJudgeSignIn(entity, workStartTime);
            if(n == 1){
                result = new Result(false,"重复签到!");
            }else if(n == 2){
                result = new Result(false,"签到异常，请到前台处理!");
            }else if(n == 3){
                result = new Result(true,"签到成功");
            }else {
                result = new Result(false,"签到异常，请到前台处理!");
            }
        }else {
            result = new Result(false,"非法操作!");
        }
        return RESULT;
    }
    
    /**
     * 签退
     * @author    mapp
     * @createTime 2017-01-17
     * @exception ParseException 时间格式转换异常
    */
    public String signOutCard() throws ParseException {
        // String转Date
        workStartTime = format.parse(WORK_START_TIME);
        workEndTime = format.parse(WORK_END_TIME);
        // 两个时间相同时 才可以进行签退操作。 也就是只能对今天的签退记录进行操作
        if(validTimeIsEqual()){
            entity = this.workRecordService.get(entity.getId());
            // 签退逻辑判断
            int n = this.workRecordService.aidantJudgeSignOut(entity, workEndTime);
            if(n == 1){
                result = new Result(true,"签退成功!");
            }else if(n == 2){
                result = new Result(false,"您已签退!");
            }else if(n == 3){
                result = new Result(false,"签退异常，请到前台处理");
            }else {
                result = new Result(false,"签退异常，请到前台处理");
            }
        }else {
            result = new Result(false,"非法操作!");
        }
        return RESULT;
    }

    /**
     * 重置人员的状态 改为默认值07 未签到
     * @author   mapp
     * @createTime 2017-01-17
    */
    public String resert(){
        // 两个时间相同时 才可以进行重置操作。 也就是只能对今天的记录进行重置
        if(validTimeIsEqual()){
            List<WorkRecord> workRecords = this.workRecordService.getAllByCurrentTime(new Date());
            int n = this.workRecordService.aidantResert(workRecords);
            if(n == 1){
                result = new Result(true,"重置成功!");
            } else {
                result = new Result(false,"一天只能重置一次，如有问题，请找工程师!");
            }
        }else {
            result = new Result(false,"非法操作");
        }
        return RESULT;
    }

    /**
     * 获取某人所有的加班时间
     * @author   mapp
     * @createTime 2017-01-17
    */
    public String getUserCount(){
        entitys = this.workRecordService.getUserCountTime(entity);
        return VIEW;
    }
    /**
     * 统计所有人加班总时长
     * @author      mapp
     * @createTime 2017-01-17
    */
    public String countOverTime(){
        entitys = this.workRecordService.getAllCountTime();
        return VIEW;
    }

    /**
     * 判断前台传来的时间 是否与系统时间相同
     * @author      mapp
     * @createTime 2017-01-18
     * @return  boolean
    */
    public boolean validTimeIsEqual(){
        // 当前系统时间
        String currentTime = format1.format(new Date());
        // 前台传来的实际操作界面的时间
        String starttime = getHttpServletRequest().getParameter("starttime");
        if(starttime.equals(currentTime)){
            return true;
        }
        return false;
    }

    /**
     * 统计功能 统计今天之前所有员工的状态
     * @author      mapp
     * @createTime 2017-01-19
    */
    public String resertStatus(){
        List<WorkRecord> list = this.workRecordService.getResertStatus(new Date());
        this.workRecordService.aidantgetResertStatus(list);
        result = new Result(true,"统计成功!");
        return RESULT;
    }

    @Override
    protected IWorkRecordService getEntityManager() {
        return workRecordService;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

}
