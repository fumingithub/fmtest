package net.jqsoft.meeting.service.impl;

import net.jqsoft.meeting.dao.UseMeetingDao;
import net.jqsoft.meeting.domain.UseMeeting;
import net.jqsoft.meeting.service.IUseMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zcframework.core.dao.IEntityDao;
import org.zcframework.core.service.BaseEntityService;

/**
 *
 * @author sijf
 * @createTime 2017/1/17
 *
 */
@Service
public class UseMeetingService extends BaseEntityService<UseMeeting> implements IUseMeetingService{

    @Autowired
    private UseMeetingDao useMeetingDao;

    @Override
    protected IEntityDao<UseMeeting> getDao() {
        return useMeetingDao;
    }

}
