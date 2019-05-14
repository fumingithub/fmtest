package net.jqsoft.base.elevatorfm.service;

import com.fr.web.core.A.T;
import net.jqsoft.base.elevatorfm.domain.TroubleDeclare;
import net.jqsoft.base.system.domain.User;
import org.zcframework.core.service.IBaseEntityService;

import javax.swing.text.html.parser.Entity;
import java.util.List;

/**
 * 电梯故障申报service接口层
 */
public interface ITroubleDeclareService extends IBaseEntityService<TroubleDeclare>{

    /**（修改或新增时）保存故障申报信息
     * @author fumin
     * @create 2018/12/17
     * @Description:
     * entity: 表单填写的数据实体类
     * types: add-新增 edit-修改
     **/
    public int TroubleDeclareSave(TroubleDeclare entity,String types);

   /**逻辑删除故障申报信息
     * @author fumin
     * @create 2018/12/19
     * @Description: 将其对应数据的删除标识设为1
     */
   public boolean localDeleteTroubleDeclare(TroubleDeclare entity);

    /**
     * 物业确认（故障申报）信息保存
     * @author: fumin
     * @date: 2018/12/21
     * @description:
     * @return:
     */
    public int confirmTroubleDeclare(TroubleDeclare entity);

    /**
     * 改变故障状态
     * @author: fumin
     * @date: 2018/12/25
     * @description: troubleStatus 1-已提交 2-维修中 3-待确认 4-已完成
     * @return:
     */
    public int updateTroubleStatus(String id, String troubleStatus);

    /**
     * 获得当前登录用户名
     * @author: fumin
     * @date: 2018/12/25
     * @description:
     * @return: String
     */
    public String getLoginer ();


    /**
     * 获得技师用户列表
     * @author: fumin
     * @date: 2018/12/29
     * @description:
     * @return: String
     */
    public List<User> technicianUserList();

}
