package net.jqsoft.meeting.view;

import net.jqsoft.base.system.domain.User;
import net.jqsoft.base.system.service.impl.UserService;
import net.jqsoft.common.util.CurrentUserUtil;
import net.jqsoft.meeting.domain.Meeting;
import net.jqsoft.meeting.domain.UseMeeting;
import net.jqsoft.meeting.service.IUseMeetingService;
import net.jqsoft.meeting.service.impl.MeetingService;
import net.jqsoft.meeting.service.impl.UseMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.view.support.Result;
import org.zcframework.core.view.support.entity.BaseEntityAction;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author sijf
 * @createTime 2017/1/17
 */
public class UseMeetingAction extends BaseEntityAction<UseMeeting,IUseMeetingService> {

    @Autowired
    private UseMeetingService useMeetingService;
    @Autowired
    private MeetingService meetingService;
    @Autowired
    private UserService userService;
    @Override
    protected IUseMeetingService getEntityManager() {
        return useMeetingService;
    }

    public String execute() {
        return super.list();
    }

    public String view(){
        return super.list();
    }

    /**
     * 借用会议室
     * @author sijf
     * @creatTime 2017/1/18
     * @return
     * */
    public String useMeeting(){
        String id= entity.getId();
        Meeting meeting = meetingService.get(id);
        if(meeting.getInUse().equals("1")){
            meeting.setInUse("0");
            String userId = CurrentUserUtil.getCurrentUser().getId().toString();
            User user = userService.get(userId);
            meeting.setUserName(user.getUserName());
            meetingService.update(meeting);
            result = new Result(true,"借用成功！");
        }else{
            result = new Result(false,"借用失败，请借用其他空闲会议室！");
        }
        return RESULT;
    }

    /**
     * 归还会议室
     * @author sijf
     * @creatTime 2017/1/19
     * @return
     * */
    public String returnMeeting() throws ParseException {
        String id= entity.getId();
        Meeting meeting = meetingService.get(id);
        String userId = CurrentUserUtil.getCurrentUser().getId().toString();
        User user = userService.get(userId);
        if(meeting.getUserName().equals(user.getUserName())){
            if(meeting.getInUse().equals("0")){
                meeting.setInUse("1");
                meeting.setUserName(" ");
                String roomId = meeting.getId();
                String useMeetingid = UUID.randomUUID().toString();
                Timestamp time = new Timestamp(System.currentTimeMillis());
                String nowTime = time.toString();
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                Date date=sdf.parse(nowTime);
                UseMeeting useMeeting = new UseMeeting();
                useMeeting.setRoomId(roomId);
                useMeeting.setId(useMeetingid);
                useMeeting.setUserId(userId);
                useMeeting.setUseDate(date);
                meetingService.update(meeting);
                useMeetingService.create(useMeeting);
                result = new Result(true,"归还成功！");
            }
        }else{
            result = new Result(false, "您没有借用此会议室，无法归还！");
        }
        return RESULT;
    }

}
