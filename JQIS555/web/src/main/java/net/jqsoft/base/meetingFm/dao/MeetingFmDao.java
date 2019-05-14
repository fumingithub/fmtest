package net.jqsoft.base.meetingFm.dao;

import net.jqsoft.base.meetingFm.domain.MeetingFm;
import org.springframework.stereotype.Repository;
import org.zcframework.core.dao.entity.IBatisEntityDao;
import java.util.Map;

/**
 * Dao层
 * @author: fumin
 * @date: 2018/11/19
 */
@Repository
public class MeetingFmDao extends IBatisEntityDao<MeetingFm> {

    /**
     * 保存会议室新增
     * @author: fumin
     * @date: 2018/11/21
     */
    public Object saveNewMeetingFm(MeetingFm entity) {
        return this.insert("MeetingFm.insert",entity);
    }
    /**
     * 保存会议室编辑
     * @author: fumin
     * @date: 2018/11/21
     */
    public Object updateMeetingFm(MeetingFm entity) {
        return this.update("MeetingFm.update",entity);
    }

    /**
     * 会议室归还和申请
     * @author: fumin
     * @date: 2018/11/19
     */
    public int updateMeetingStatusById(Map<String, Object> param ) {
        return  this.update("MeetingFm.updateMeetingStatusById",param);
    }

    /**
     * 会议室启用和禁用
     * @author: fumin
     * @date: 2018/11/21
     */
    public int updateMeetingEnableById(Map<String, Object> param) {
        return  this.update("MeetingFm.updateMeetingEnableById",param);
    }

    /**
     * 根据会议室名称name获取一条数据
     * @return MeetingFm
     * @author: fumin
     * @date: 2018/11/27
     **/
    public MeetingFm getInfoByMeetingName(String name){
        return  this.findUniqueBy("MeetingFm.getInfoByMeetingName", "name", name);
    }
}
