package net.jqsoft.attendance.service.imp;

import net.jqsoft.attendance.dao.LeaveDao;
import net.jqsoft.attendance.domain.Leave;
import net.jqsoft.attendance.service.ILeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zcframework.core.dao.IEntityDao;
import org.zcframework.core.service.BaseEntityService;
/**
 * 请假管理Service层
 * @author: liujun
 * @createTime: 2017-01-16
 */
@Service
public class LeaveService  extends BaseEntityService<Leave> implements ILeaveService {

    @Autowired
    private LeaveDao leaveDao;
    @Override
    protected IEntityDao<Leave> getDao(){return leaveDao;}
}

