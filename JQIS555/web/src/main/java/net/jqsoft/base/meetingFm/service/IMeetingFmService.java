package net.jqsoft.base.meetingFm.service;

import net.jqsoft.base.meetingFm.domain.MeetingFm;
import org.zcframework.core.service.IBaseEntityService;


/**
 * 会议室管理service接口
 * @author: fumin
 * @date: 2018/11/19
 */
public interface IMeetingFmService extends IBaseEntityService<MeetingFm> {
    /**
     * 修改或新增
     * @Description:
     * @author fumin
     * @create 2016-11-09 11:39:06
     **/
    public int saveOrUpdateMeetingFm(MeetingFm entity,String types);


    /**
     * @Description: 更改会议室使用状态
     * @Author: fumin
     * @Date: 2018/11/21
     */
    public int updateMeetingStatusById(String status, String id);

    /**
     * @Description: 更改会议室启用禁用
     * @Author: fumin
     * @Date: 2018/11/21
     */
    public int updateMeetingEnableById(String enable,String id,String disableReason);

    /**
     * 根据name获取一条数据
     * @return MeetingFm
     * @author: fumin
     * @date: 2018/11/27
     **/
    public MeetingFm getInfoByMeetingName(String name);

}
