/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.attendance.service.imp;

import net.jqsoft.attendance.dao.WorkRecordDao;
import net.jqsoft.attendance.domain.Leave;
import net.jqsoft.attendance.domain.WorkRecord;
import net.jqsoft.attendance.service.IWorkRecordService;
import net.jqsoft.common.exception.ManagerException;
import net.jqsoft.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zcframework.core.dao.IEntityDao;
import org.zcframework.core.service.BaseEntityService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author mapp
 * @createTime 2017-01-16
 */
@Service
public class WorkRecordService extends BaseEntityService<WorkRecord> implements IWorkRecordService {

    @Autowired
    private WorkRecordDao workRecordDao;
    /**
     * 格式化时间
     */
    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

    @Override
    protected IEntityDao<WorkRecord> getDao() {
        return workRecordDao;
    }


    /**
     * 查询所有人的工作记录
     *
     * @return 工作记录集合
     * @author mapp
     * @createTime 2017-01-17
     */
    @Override
    public List<WorkRecord> getAllWordRecord() {
        return this.workRecordDao.getAll();
    }

    /**
     * 插入人员工作记录
     *
     * @param workRecord
     * @author mapp
     * @createTime 2017-01-17
     */
    @Override
    public void insertWorkRecords(WorkRecord workRecord) {
        try{
            this.workRecordDao.insert(getEntityName() + ".insert", workRecord);
        }catch (Exception e){
            throw new ManagerException("操作失败!");
        }
    }

    /**
     * 获取所有人员的加班总时间
     * @return 查询所有人的工作记录
     * @author mapp
     * @createTime
     */
    @Override
    public List<WorkRecord> getAllCountTime() {
        return this.workRecordDao.getAll(getEntityName() + ".getAllCountTime");
    }

    /**
     * 获取当天的人员签到记录
     * @param currentTime 当前时间
     * @return 所有人的工作记录
     * @author mapp
     * @createTime 2017-01-18
     */
    @Override
    public List<WorkRecord> getAllByCurrentTime(Date currentTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("currentTime", currentTime);
        return this.workRecordDao.find(getEntityName() + ".getAllByCurrentTime", map);
    }

    /**
     * 获取个人的所有加班信息
     * @param workRecord
     * @return 个人加班记录集合
     * @author mapp
     * @createTime 2017-01-18
     */
    @Override
    public List<WorkRecord> getUserCountTime(WorkRecord workRecord) {
        return this.workRecordDao.find(getEntityName() + ".getUserCountTime", workRecord);
    }

    /**
     * 签到的逻辑判断
     * @author mapp
     * @createTime 2017-01-18
     */
    @Override
    public Integer aidantJudgeSignIn(WorkRecord workRecord, Date workStartTime) {
        int flag = 0;
        // 判断是否 重复签到
        if ("01".equals(workRecord.getWorkStatus()) || "03".equals(workRecord.getWorkStatus())) {
            flag = 1;
            // 这些情况无法正常签到
        } else if ("99".equals(workRecord.getWorkStatus()) || "04".equals(workRecord.getWorkStatus())
                || "05".equals(workRecord.getWorkStatus()) || "06".equals(workRecord.getWorkStatus())
                || "02".equals(workRecord.getWorkStatus())) {
                flag = 2;
        } else {
            Date singInTime = null;
            try {
                singInTime = format.parse(format.format(new Date()));
                if (singInTime.getTime() > workStartTime.getTime()) {
                    // 迟到
                    workRecord.setWorkStatus("03");
                    workRecord.setSignInTime(new Date());
                    try {
                        this.update(workRecord);
                    }catch (Exception e){
                        e.printStackTrace();
                        throw new ManagerException("操作失败!");
                    }
                    flag = 3;
                } else {
                    workRecord.setWorkStatus("01");
                    workRecord.setSignInTime(new Date());
                    try {
                        this.update(workRecord);
                    }catch (Exception e){
                        e.printStackTrace();
                        throw new ManagerException("操作失败!");
                    }
                    flag = 3;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * 签退的逻辑判断
     * @author mapp
     * @createTime 2017-01-18
     */
    @Override
    public Integer aidantJudgeSignOut(WorkRecord workRecord, Date workEndTime) {
        int flag = 0;
        if ("01".equals(workRecord.getWorkStatus()) || "03".equals(workRecord.getWorkStatus()) || "07".equals(workRecord.getWorkStatus())) {
            // 实际签退时间
            Date singOutTime = null;
            try {
                singOutTime = format.parse(format.format(new Date()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (singOutTime.getTime() < workEndTime.getTime()) {
                //早退
                workRecord.setWorkStatus("06");
                workRecord.setSignOutTime(new Date());
                try {
                    this.update(workRecord);
                }catch (Exception e){
                    e.printStackTrace();
                    throw new ManagerException("操作失败!");
                }
                flag = 1;
            } else {
                workRecord.setWorkStatus("02");
                workRecord.setSignOutTime(new Date());
                // 计算每天的实际加班时间 单位分钟
                int realOverTime = counTime(workRecord.getSignOutTime(), workEndTime);
                workRecord.setRealOverTime(realOverTime);
                // 处理后的加班时间 单位 小时
                workRecord.setOverTime(meg((double) realOverTime / 60));
                try {
                    this.update(workRecord);
                }catch (Exception e){
                    e.printStackTrace();
                    throw new ManagerException("操作失败!");
                }
                flag = 1;
            }
        } else if ("02".equals(workRecord.getWorkStatus())) {
            flag = 2;
        } else {
            flag = 3;
        }
        return flag;
    }

    /**
     * 计算加班时间 加班时间按照小时 计算
     * @param singOutTime 签退时间
     * @param workEndTime 公司下班时间
     * @return 真正的加班时间
     * @author mapp
     * @createTime 2017-01-18
     */
    public Integer counTime(Date singOutTime, Date workEndTime) {
        // 计算加班时间
        Calendar c1 = Calendar.getInstance();
        c1.setTime(singOutTime);
        // 签退时间的 时分
        int hour = c1.get(Calendar.HOUR_OF_DAY);
        int minute = c1.get(Calendar.MINUTE);
        int sumMinute = hour * 60 + minute;

        Calendar c2 = Calendar.getInstance();
        c2.setTime(workEndTime);
        // 下班时间的 时分
        int hour1 = c2.get(Calendar.HOUR_OF_DAY);
        int minute1 = c2.get(Calendar.MINUTE);
        int sumMinute1 = hour1 * 60 + minute1;
        int overTime = sumMinute - sumMinute1;
        if (overTime > 0) {
            return overTime;
        } else {
            return 0;
        }
    }

    /**
     * 对小数进行处理，小数位只能为0或者5
     * 规则描述 超过30分钟 算0.5小时 低于30分钟 不算入加班时间
     * @param i 传来的加班时间 浮点型
     * @return 处理过后的加班时间
     * @throws NumberFormatException 数字转换异常
     * @author mapp
     * @createTime 2017-01-19
     */
    public static double meg(double i) {
        double sum = 0.0;
        // 去除多余小数位
        int b = (int) (i * 10);
        // 还原一位小数
        double c = ((double) b / 10.0);
        if ((c * 10) % 5 != 0) {
            try {
                String str = String.valueOf(c);
                // 获取小数点后一位数
                int d = Integer.parseInt(str.substring(str.indexOf(".")+1));
                // 整数位
                String e = str.substring(0,str.indexOf("."));
                // (0-5)算 0
                if (d > 0 && d < 5) {
                    d = 0;
                // [5-9]算5
                }if(d >=5 && d <=9) {
                    d = 5;
                }else {
                    d = 0;
                }
                sum = Double.parseDouble(e + "." + String.valueOf(d));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                throw new ManagerException("数字格式不正确!");
            }
        } else {
            sum = c;
        }
        return sum;
    }

    /**
     * 获取除今天以外没统计过状态的人员 信息
     * @param currentTime 当前时间
     * @author mapp
     * @createTime 2017-01-19
     */
    @Override
    public List<WorkRecord> getResertStatus(Date currentTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("currentTime", currentTime);
        return this.workRecordDao.find(getEntityName() + ".getResertStatus", map);
    }

    /**
     * 统计所有人的状态
     * @param list : 人员记录集合
     * @author mapp
     * @createTime 2017-01-19
     */
    @Override
    public Integer aidantgetResertStatus(List<WorkRecord> list) {
        int flag = 0;
        for (WorkRecord w : list) {
            if ("07".equals(w.getWorkStatus())) {
                // 获取该用户的 所有请假记录
                List<Leave> leaveList = getUserLeave(w.getUserId(),w.getCurrentTime());
                if(leaveList.size() == 0){
                    w.setWorkStatus("04");
                    w.setIsResert("01");
                    try{
                        this.update(w);
                    }catch (Exception e){
                        e.printStackTrace();
                        throw new ManagerException("操作失败!");
                    }
                    flag = 1;
                }else{
                    // 有请假记录
                    w.setWorkStatus("05");
                    w.setIsResert("01");
                    try{
                        this.update(w);
                    }catch (Exception e){
                        e.printStackTrace();
                        throw new ManagerException("操作失败!");
                    }
                    flag = 1;
                }
                // 签到时间 或者签退时间 任意一个为空 算 异常
            }if((w.getSignInTime() == null && w.getSignOutTime() != null)
                    || (w.getSignInTime() != null && w.getSignOutTime() == null)){
                w.setIsResert("01");
                w.setWorkStatus("99");
                try {
                    this.update(w);
                }catch (Exception e){
                    e.printStackTrace();
                    throw new ManagerException("操作失败!");
                }
                flag = 2;
            }else {
                w.setIsResert("01");
                this.update(w);
                flag = 3;
            }
        }
        return flag;
    }

    /**
     * 根据用户 签到记录的 CURRENT_TIME 字段 即该条签到记录的时间 获取请假信息
     * @author      mapp
     * @createTime 2017-01-20
     * @param userId 用户id
     * @param currentTime 当日时间
     * @return  请假集合
     */
    @Override
    public List<Leave> getUserLeave(String userId, Date currentTime) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("userId",userId);
        map.put("currentTime",currentTime);
        return this.workRecordDao.find(getEntityName()+".getUserLeave",map);
    }

    /**
     * 重置当天的人员的状态 改为默认值07 未签到
     * @author      mapp
     * @createTime 2017-01-19
     * @param list 工作记录集合
     * @return  int
     */
    @Override
    public Integer aidantResert(List<WorkRecord> list) {
        if(list.size() == 0 ){
            List<WorkRecord> workRecords = this.getAllWordRecord();
            if(workRecords.size() > 0 ){
                for(WorkRecord e : workRecords){
                    e.setId(CommonUtil.getUUID());
                    e.setWorkStatus("07");
                    e.setIsResert("02");
                    e.setCurrentTime(new Date());
                    try{
                        this.insertWorkRecords(e);
                    }catch (Exception e1){
                        e1.printStackTrace();
                        throw new ManagerException("重置失败!");
                    }
                }
               return 1;
            }
        }
        return 0;
    }
}
