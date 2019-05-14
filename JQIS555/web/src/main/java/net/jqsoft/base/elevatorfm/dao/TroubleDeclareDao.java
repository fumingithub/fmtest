package net.jqsoft.base.elevatorfm.dao;

import net.jqsoft.base.elevatorfm.domain.TroubleDeclare;
import net.jqsoft.base.system.domain.User;
import org.springframework.stereotype.Repository;
import org.zcframework.core.dao.entity.IBatisEntityDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**故障申报Dao层
 * @author: Administrator
 * @date: 2018/12/17
 * @description:
 */
@Repository
public class TroubleDeclareDao extends IBatisEntityDao<TroubleDeclare> {
    /**
     * 新增故障申报信息
     * @author: fumin
     * @date: 2018/12/17
     */
    public Object TroubleDeclareAdd(TroubleDeclare entity){
        return this.insert("TroubleDeclare.insert", entity);
    }
    /**
     * 编辑故障申报信息
     * @author: fumin
     * @date: 2018/12/17
     */
    public Object TroubleDeclareEdit(TroubleDeclare entity){
        return this.update("TroubleDeclare.update",entity);
    }

    /**
     * 逻辑删除故障申报信息
     * @author: fumin
     * @date: 2018/12/19
     */
    public int localDeleteTroubleDeclare(TroubleDeclare entity){
        return this.update("TroubleDeclare.localDeleteTroubleDeclare",entity);
    }

    /**
     * 物业确认故障申报信息
     * @author: fumin
     * @date: 2018/12/20
     */
    public Object confirmTroubleDeclare(TroubleDeclare entity){
        return this.update("TroubleDeclare.confirmTroubleDeclare",entity);
    }

    /**
     * 改变故障状态
     * @author: fumin
     * @date: 2018/12/25
     * @description: troubleStatus 1-已提交 2-维修中 3-待确认 4-已完成
     * @return:
     */
    public int updateTroubleStatus(Map<String, Object> parame){
        return this.update("TroubleDeclare.updateTroubleStatus",parame);
    }

    /**
     * 获得技师用户list集合
     * @author: fumin
     * @date: 2018/12/25
     * @description: troubleStatus 1-已提交 2-维修中 3-待确认 4-已完成
     * @return:*/
    public List<User> getTechnicianUserList(){
        return this.getSqlMapClientTemplate().queryForList("TroubleDeclare.getTechnicianUserList");
    }
}
