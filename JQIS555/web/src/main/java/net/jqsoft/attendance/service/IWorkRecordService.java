/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.attendance.service;

import net.jqsoft.attendance.domain.Leave;
import net.jqsoft.attendance.domain.WorkRecord;
import org.zcframework.core.service.IBaseEntityService;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author mapp 
 * @createTime 2017-01-16
 */
public interface IWorkRecordService extends IBaseEntityService<WorkRecord> {

    /**
     * 查询所有人的工作记录
     * @author      mapp
     * @createTime 2017-01-17
     * @return 工作记录集合
    */
    List<WorkRecord> getAllWordRecord();
    
    /**
     * 插入人员工作记录
     * @author      mapp
     * @createTime 2017-01-17
     * @param workRecord 人员记录实体
    */
    void insertWorkRecords(WorkRecord workRecord);
    
    /**
     * 获取所有人员的加班总时间
     * @author      mapp
     * @createTime
     * @return  查询所有人的工作记录
    */
    List<WorkRecord> getAllCountTime();
    
    /**
     * 获取当天的人员签到记录
     * @author      mapp
     * @createTime 2017-01-18
     * @param currentTime 当前时间
     * @return  所有人的工作记录
    */
    List<WorkRecord> getAllByCurrentTime(Date currentTime);
    
    /**
     * 获取个人的所有加班信息
     * @author      mapp
     * @createTime 2017-01-18
     * @param workRecord
     * @return  个人加班记录集合
    */
    List<WorkRecord> getUserCountTime(WorkRecord workRecord);
    
    /**
     * 签到的逻辑判断
     * @author      mapp
     * @createTime 2017-01-18
     * @param workRecord 工作记录实体
     * @param workStartTime 公司上班时间
     */
    public Integer aidantJudgeSignIn(WorkRecord workRecord, Date workStartTime);

    /**
     * 签退的逻辑判断
     * @author      mapp
     * @createTime 2017-01-18
     * @param workRecord 工作记录实体
     * @param workEndTime 公司下班时间
     */
    public Integer aidantJudgeSignOut(WorkRecord workRecord, Date workEndTime);

    /**
     * 获取除今天以外没统计过状态的人员 信息
     * @author      mapp
     * @createTime 2017-01-19
     * @param currentTime 当天时间
    */
    List<WorkRecord> getResertStatus(Date currentTime);

    /**
     * 重置当天的人员的状态 改为默认值07 未签到
     * @author      mapp
     * @createTime 2017-01-19
     * @param list 工作记录集合
     * @return  int
    */
    Integer aidantResert(List<WorkRecord> list);
    
    /**
     * 统计所有人的状态
     * @author      mapp
     * @createTime 2017-01-19
     * @param list 工作记录实体集合
    */
    public Integer aidantgetResertStatus(List<WorkRecord> list);
    
    /**
     * 获取 用户当日及当日以后的请假信息
     * 根据用户的 请假结束时间判断，如果结束时间大于等于今天，则有请假记录
     * @author      mapp
     * @createTime 2017-01-20
     * @param userId 用户id
     * @param currentTime 当日时间
     * @return  请假集合
    */
    List<Leave> getUserLeave(String userId,Date currentTime);
}
