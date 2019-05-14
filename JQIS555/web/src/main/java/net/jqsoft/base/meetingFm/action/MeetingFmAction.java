package net.jqsoft.base.meetingFm.action;

import net.jqsoft.base.meetingFm.domain.MeetingFm;
import net.jqsoft.base.meetingFm.service.IMeetingFmService;
import net.jqsoft.common.action.MyEntityAction;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.view.support.Result;
import java.util.List;

/**
 * @author: fumin
 * @date: 2018/11/19
 * @description:
 */
public class MeetingFmAction extends MyEntityAction<MeetingFm, IMeetingFmService> {
    @Autowired
    private IMeetingFmService meetingFmService;

    @Override
    protected IMeetingFmService getEntityManager() {
        return meetingFmService;
    }

    /** List集合 */
    private List<MeetingFm> meetingFmList;

    /** 会议室实体类对象 **/
    private MeetingFm meetingFm;

    /** 操作类型：edit-编辑，view-查看 */
    private String types;

    /** 默认调用方法 **/
    @Override
    public String list() {
        return super.list();
    }

    /**
     * 编辑和新增
     * @author fumin
     * @create 2018/11/18
     * @description: types：add-新增 edit-编辑 view-查看
     *  如果是新增 id为null 直接跳转至新增页面
     *  如果是编辑或查看 id不为空 通过id获得所选中数据实体类
     **/
    public String editMeetingFm() {
        boolean flags = true;
        try{
            String id = this.getHttpServletRequest().getParameter("id");
            String types = this.getHttpServletRequest().getParameter("types");
            if ("add".equals(types)){
            }else{
                entity = this.meetingFmService.get(id);
            }
        }
        catch (Exception e) {
            flags = false;
            e.printStackTrace();
            result = new Result(false, "查询会议室信息异常！（ACTION）", "", false);
        } finally {
            if (flags){
                return "dialog";
            }else {
                return "_result";
            }
        }
    }
    /**
     * 会议室保存
     * @author fumin
     * @create 2018/11/18
     * @description: res为saveOrUpdateMeetingFm返回值 1-新增 2-修改
     * @return: _result 返回提示信息到页面
     **/
    public String meetingSave () {
        try {
            String id = this.getHttpServletRequest().getParameter("id");
            String types = this.getHttpServletRequest().getParameter("types");
            //新增时id为null
            String sid = entity.getId();
            String name = entity.getName();
            MeetingFm meetingFm = this.meetingFmService.getInfoByMeetingName(name);
            if (meetingFm != null) {
                result = new Result(false, "会议室名称已存在!");
                return "_result";
            }

            int res = this.meetingFmService.saveOrUpdateMeetingFm(entity, types);
            if (res == 1) {
                result = new Result(true, "保存会议室信息成功！");
            } else {
                result = new Result(true, "修改会议室信息成功！");
            }
        }catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "保存会议室信息异常！（ACTION）", "", false);
        } finally {
            return "_result";
        }
    }

    /**
     * 会议室删除
     * @author fumin
     * @create 2018/11/18
     * @description: 根据id删除会议室信息
     * @return: _result 返回提示信息到页面
     **/
    public String deleteMeetingFm() {
        meetingFmService.removeById(entity.getId());
        result = new Result(true, "删除成功！", "", false);
        return "_result";
    }

    /**
     * 会议室申请和归还
     * @author fumin
     * @create 2018/11/19
     * @description:  status 会议室状态 1-使用 2-空闲
     * @return: _result 返回提示信息到页面
     */
    public String meetingApplyOrReturn(){
        try {
            //获得前台传入id
            String meetingId = this.getHttpServletRequest().getParameter("id");
            if (StringUtils.isNotEmpty(meetingId)){
                //根据id获得会议室的实体类
                meetingFm = this.meetingFmService.get(meetingId);
                //status=1,当前状态为使用中
                if("1".equals(meetingFm.getStatus())){
                    this.meetingFmService.updateMeetingStatusById("2",meetingId);
                    result = new Result(true, "归还会议室成功！");
                }
                //status=2.当前状态为归还
                if ("2".equals(meetingFm.getStatus())){
                    this.meetingFmService.updateMeetingStatusById("1",meetingId);
                    result = new Result(true, "申请会议室成功！");
                }
            }
            else {
                result = new Result(false,"申请会议室失败！");
            }
        }catch (Exception e){
            e.printStackTrace();
            result = new Result(false, "申请/归还会议室信息异常！（ACTION）", "", false);
        }finally {
            return "_result";
        }
    }

    /** 会议室启用、禁用
     * @author fumin
     * @create 2018/11/19
     * @description: enable是否启用 0-禁用 1-启用
     * @return: _result 返回提示信息到页面
     **/
    public String enable0rDisable(){
        try {
            String id = this.getHttpServletRequest().getParameter("id");
            String enable = this.getHttpServletRequest().getParameter("enable");
                if (StringUtils.isNotEmpty(id)){
                    meetingFm = this.meetingFmService.get(id);
                    if("0".equals(enable)){
                        //此时会议室状态为禁用，更改enable 重置disableReason为空
                         this.meetingFmService.updateMeetingEnableById("1",id," ");
                         result =new Result(true,"会议室启用成功！");
                    }else if ("1".equals(enable)){
                        //此时会议室状态为启用
                        return "disableReason";
                    }
                }else {
                    result = new Result(false,"启用/禁用会议室失败！");
                }
        }catch(Exception e){
            e.printStackTrace();
            result = new Result(false, "启用/禁用会议室信息异常！（ACTION）", "", false);
        } finally {
        }
        return "_result";
    }

    /**
     * 会议室禁用原因保存
     * @author fumin
     * @create 2018/11/21
     * @description: enable是否启用 0-禁用 1-启用
     *                 disableReason-禁用原因
     * @return: _result 返回提示信息到页面
     */
    public String meetingDisableReasonSave(){
        String id = this.getHttpServletRequest().getParameter("id");
        String disableReason = this.getHttpServletRequest().getParameter("disableReason");
        try {
            this.meetingFmService.updateMeetingEnableById("0",id,disableReason);
            result =new Result(true,"会议室禁用成功！");
        }catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "启用/禁用会议室信息异常！（ACTION）", "", false);
        } finally {
            return "_result";
        }
    }

    public IMeetingFmService getMeetingFmService() {
        return meetingFmService;
    }

    public void setMeetingFmService(IMeetingFmService meetingFmService) {
        this.meetingFmService = meetingFmService;
    }

    public List<MeetingFm> getMeetingFmList() {
        return meetingFmList;
    }

    public void setMeetingFmList(List<MeetingFm> meetingFmList) {
        this.meetingFmList = meetingFmList;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }
}
