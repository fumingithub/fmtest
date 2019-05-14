package net.jqsoft.base.meetingFm.service.impl;

import com.google.common.collect.Maps;
import net.jqsoft.base.meetingFm.dao.MeetingFmDao;
import net.jqsoft.base.meetingFm.domain.MeetingFm;
import net.jqsoft.base.meetingFm.service.IMeetingFmService;
import net.jqsoft.common.exception.ManagerException;
import net.jqsoft.common.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zcframework.core.dao.IEntityDao;
import org.zcframework.core.service.BaseEntityService;

import java.util.List;
import java.util.Map;

/**
 * @author: Administrator
 * @date: 2018/11/19
 * @description:
 */
@Service
public class MeetingFmService extends BaseEntityService<MeetingFm> implements IMeetingFmService {
    @Autowired
    private MeetingFmDao meetingFmDao;

    @Override
    protected IEntityDao<MeetingFm> getDao() {
        return meetingFmDao;
    }

    /**
     * 判断新增或修改
     * @Author: fumin
     * @Date: 2018/11/21
     * @return int 1-新增 2-修改
     **/
    @Override
    public int saveOrUpdateMeetingFm(MeetingFm entity,String types) {
       int flag = 0;
        try {
            if ("edit".equals(types)) {
                this.meetingFmDao.updateMeetingFm(entity);
               flag = 2;
            }
            else {
                entity.setId(CommonUtil.getUUID());
                this.meetingFmDao.saveNewMeetingFm(entity);
               flag = 1;
            }
            return flag;
        } catch (ManagerException ex) {
            throw ex;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ManagerException("100200", new String[]{"会议室信息保存异常！（BIZ）"});
        } finally {

        }
    }
    /**
     *更改会议室使用状态
     * @Author: fumin
     * @Date: 2018/11/21
     */
    @Override
    public int updateMeetingStatusById(String status, String id) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("status",status);
        param.put("id",id);
        return  this.meetingFmDao.updateMeetingStatusById(param);
    }

    /**
     * 启用禁用方法
     * @Author: fumin
     * @Date: 2018/11/21
     */
    @Override
    public int updateMeetingEnableById(String enable, String id ,String disableReason) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("enable",enable);
        param.put("id",id);
        param.put("disableReason",disableReason);
        return  this.meetingFmDao.updateMeetingEnableById(param);
    }

    /**
     * 根据name获取一条数据
     * @return MeetingFm
     * @author: fumin
     * @date: 2018/11/27
     **/
    @Override
    public MeetingFm getInfoByMeetingName(String name){
        return this.meetingFmDao.getInfoByMeetingName(name);
    }

}
