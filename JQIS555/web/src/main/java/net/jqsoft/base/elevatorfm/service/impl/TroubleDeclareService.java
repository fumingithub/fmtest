package net.jqsoft.base.elevatorfm.service.impl;

import com.google.common.collect.Maps;
import net.jqsoft.base.elevatorfm.dao.TroubleDeclareDao;
import net.jqsoft.base.elevatorfm.domain.TroubleDeclare;
import net.jqsoft.base.elevatorfm.service.ITroubleDeclareService;
import net.jqsoft.base.system.domain.User;
import net.jqsoft.common.exception.ManagerException;
import net.jqsoft.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zcframework.core.dao.IEntityDao;
import org.zcframework.core.service.BaseEntityService;
import org.zcframework.security.SpringSecurityUtils;
import org.zcframework.security.model.Role;
import org.zcframework.security.object.Loginer;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: Administrator
 * @date: 2018/12/17
 * @description:
 */
@Service
public class TroubleDeclareService extends BaseEntityService<TroubleDeclare> implements ITroubleDeclareService {
    @Autowired
    private TroubleDeclareDao troubleDeclareDao;

    @Override
    protected IEntityDao<TroubleDeclare> getDao(){
        return troubleDeclareDao;
    }

    /**（修改或新增时）保存故障申报信息
     * @author fumin
     * @create 2018/12/17
     * @Description:
     * entity: 表单填写的数据实体类
     * types: add-新增 edit-修改
     **/
    @Override
    public int TroubleDeclareSave(TroubleDeclare entity,String types){
        try{
            Loginer loginer = SpringSecurityUtils.getCurrentUser();
            //用于控制成功后的提示信息类型
            int res = 0;
            //如果是新增，删除标识为0，自动生成新的唯一ID,增加新建人信息并保存
            if ("add".equals(types)){
                entity.setId(CommonUtil.getUUID());
                entity.setIsDel("0");
                entity.setCreator(loginer.getId().toString());
                entity.setCreatorName(SpringSecurityUtils.getCurrentUserName());
                entity.setCreatorTime(new Date());
                this.troubleDeclareDao.TroubleDeclareAdd(entity);
                res = 1;
            }
            //如果是编辑，增加更新人、新建时间并保存表单信息
            else {
                entity.setCreator(loginer.getId().toString());
                entity.setUpdatorName(SpringSecurityUtils.getCurrentUserName());
                entity.setUpdatorTime(new Date());
                this.troubleDeclareDao.TroubleDeclareEdit(entity);
                res = 2;
            }
            return res;
        }catch(Exception e){
            e.printStackTrace();
            throw new ManagerException("100200", new String[]{"" +
                    "故障申报信息保存异常！（BIZ）"});
        }finally {
        }
    }

    /**逻辑删除故障申报信息
     * @author fumin
     * @create 2018/12/19
     * @Description: 根据id将其对应数据的删除标识设为1
     */
    @Override
    public boolean localDeleteTroubleDeclare(TroubleDeclare entity){
        //用于删除成功后的提示信息
        boolean flag;
        try {
            entity.setIsDel("1");
            entity.setDeleterName(SpringSecurityUtils.getCurrentUserName());
            entity.setDeleterTime(new Date());
            this.troubleDeclareDao.localDeleteTroubleDeclare(entity);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    /**
     * 保存物业确认（故障申报）信息
     * @author: fumin
     * @date: 2018/12/21
     * @description:
     * @return:
     */
    @Override
    public int confirmTroubleDeclare(TroubleDeclare entity){
        //用于保存后的信息提示
        int flag;
        try {
            //将故障状态更改为维修中，并保存表单信息
            entity.setTroubleStatus("2");
            this.troubleDeclareDao.confirmTroubleDeclare(entity);
            flag = 1;
        }catch (Exception e){
            e.printStackTrace();
            flag = 0;
        }
        return flag;
    }

    /**
     * 改变故障状态
     * @author: fumin
     * @date: 2018/12/25
     * @description: troubleStatus 1-已提交 2-维修中 3-待确认 4-已完成
     * @return:
     */
    @Override
    public int updateTroubleStatus(String id, String troubleStatus){
        Map<String ,Object> param = Maps.newHashMap();
        param.put("id",id);
        param.put("troubleStatus",troubleStatus);
        return this.troubleDeclareDao.updateTroubleStatus(param);
    }
    /** 获得当前登录角色编码
     * @author: fumin
     * @date: 2018/12/25
     * @description:
     * @return: String当前登录角色编码
     */
    @Override
    public String getLoginer(){
        String myPermission = "";
        //获得当前登录用户
        Loginer loginer = SpringSecurityUtils.getCurrentUser();
        //获得当前用户角色列表,并遍历
        List<Role> roleList = loginer.getRoles();
        for (Role role : roleList){
            //物业权限
            if ("wyfm".equals(role.getRoleName())){
                myPermission = "wyfm";
                break;
            }
            //技师权限
            if ("jsfm".equals(role.getRoleName())){
                myPermission = "jsfm";
                break;
            }
            //业主权限
            if ("yezhu_fumin".equals(role.getRoleName())){
                myPermission = "yezhu_fumin";
                break;
            }
        }
        return myPermission;
    }

    /**
     * 获得技师用户列表
     * @author: fumin
     * @date: 2018/12/29
     * @description:
     * @return: String
     */
    @Override
    public List<User> technicianUserList(){
        return this.troubleDeclareDao.getTechnicianUserList();
    }

}
