package net.jqsoft.base.elevatorfm.action;

import net.jqsoft.base.elevatorfm.domain.Elevator;
import net.jqsoft.base.elevatorfm.domain.MaintenanceRecord;
import net.jqsoft.base.elevatorfm.domain.TroubleDeclare;
import net.jqsoft.base.elevatorfm.service.IMaintenanceRecordService;
import net.jqsoft.base.elevatorfm.service.impl.ElevatorService;
import net.jqsoft.base.elevatorfm.service.impl.TroubleDeclareService;
import net.jqsoft.base.system.domain.User;
import net.jqsoft.common.action.MyEntityAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.view.support.Result;
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
public class MaintenanceRecordAction extends MyEntityAction<MaintenanceRecord,IMaintenanceRecordService>{
    /** 电梯实体类 **/
    private Elevator elevator;
    private ElevatorService elevatorService;

    /**故障申报实体类**/
    private TroubleDeclare troubleDeclareEntity;
    private TroubleDeclareService troubleDeclareService;

    /** 页面操作类型：add-新增 edit-编辑 view-查看**/
    private String types;

    /**获得当前时间**/
    Date localDate = new Date();

    /** 电梯维保状态：1-维保中 2-已完成 **/
    private String confirmMaintenanceStatus;

    /** 电梯故障状态：1-已提交 2-维保中 3-待确认 4-已完成 **/
    private  String troubleStatus;

    /**技师用户集合List**/
    private List<User> technicianUserList;

    /** 当前登录角色：wyfm-物业权限 jsfm-技师权限 yezhu_fumin-业主权限**/
    private String myPermission;

    /** 当前登录用户**/
    private String loginUserName = SpringSecurityUtils.getCurrentUserName();

    @Autowired
    private IMaintenanceRecordService maintenanceRecordService;

    @Override
    protected IMaintenanceRecordService getEntityManager(){
        return maintenanceRecordService;
    }

    @Override
    public String list(){
        //根据前台传入id获得对应电梯信息实体类,和故障申报实体类
        String elevatorId = this.getHttpServletRequest().getParameter("id");
        elevator = this.elevatorService.get(elevatorId);
        //获取前端传来的所有数据，并用map保存
        Map<String,Object> param = getActionContext().getParameters();
        //遍历map，拿出值，去空格
        for (Map.Entry entry : param.entrySet()){
            if (entry.getValue() instanceof String[]){
                String[] vals =(String[]) entry.getValue();
                entry.setValue(new String[]{vals[0].toString().trim()});
            }
        }
        entity.setElevatorId(elevatorId);
        param.put("elevatorId",new String[]{entity.getElevatorId()});
        //权限验证，获得当前登录用户
        myPermission = this.troubleDeclareService.getLoginer();
        return super.list();
    }

    /**  维保记录：--新增，编辑或查看
     * @author: fumin
     * @date: 2018/12/24
     * @description: 根据types的值判断操作状态 add-新增 edit-编辑 view-查看
     * @return: RESULT 返回提示信息到页面
     */
    public String maintenanceRecord(){
        //flag用于判断是否有异常
        int flag = 0;
        //用于区分不同页面
        String viewType = "maintenanceRecord_view";
        try{
            //获得前台选中数据的id和按钮操作类型
            String id = this.getHttpServletRequest().getParameter("id");
            types = this.getHttpServletRequest().getParameter("types");
            if ("add".equals(types)){
            }else {
                entity = this.maintenanceRecordService.get(id);
                //判断数据状态是否已经被改变
                if(entity == null){
                } else {
                    //只能编辑自己新增的数据
                    if (("edit".equals(types)) && (!loginUserName.equals(entity.getCreatorName()))) {
                        result = new Result(false, "该数据不是您新增的，您不能编辑！");
                        flag = 1;
                    } else {
                        if ("view".equals(types)) {
                            viewType = "maintenanceRecord_confirm";
                        }
                        confirmMaintenanceStatus = entity.getMaintenanceStatus();
                    }
                }
            }
            //获得技师用户列表
            technicianUserList = this.troubleDeclareService.technicianUserList();
        }catch (Exception e){
            flag =1;
            e.printStackTrace();
            result = new Result(false,"维保记录查询失败");
        }
        if (flag == 0){
            return viewType;
        }else {
            return RESULT;
        }
    }

    /**  维保记录：--新增或编辑，查看保存
     * @author: fumin
     * @date: 2018/12/24
     * @description: 根据types的值判断操作状态 add-新增 edit-编辑 view-查看
     * @return: RESULT 返回提示信息到页面
     */
    public String maintenanceRecordSave(){
        try {
            //获得当前维保表的id,按钮操作类型，当前登录角色
            types = this.getHttpServletRequest().getParameter("types");
            String id = this.getHttpServletRequest().getParameter("id");
            myPermission = this.getHttpServletRequest().getParameter("myPermission");
            //如果是技师新增，从电梯信息表中获得维保记录所需要的列数据，维保状态为维保中
            if ("add".equals(types)){
                elevator= this.elevatorService.get(id);
                entity.setElevatorId(id);
                entity.setCreatorTime(localDate);
                entity.setMaintenanceStatus("1");
            }else {
                //判断数据状态是否已经被改变
                boolean judgeChanges = this.judgeChanges(entity.getId(), entity);
                if (judgeChanges) {
                    return RESULT;
                }
            }
            //保存表单信息，用nis的值，区分新增、编辑成功后的提示信息
            int nis = this.maintenanceRecordService.technicianMaintenanceSave(entity, types);
            if (nis == 2) {
                result = new Result(true, "技师维保信息新增成功!");

                //更新电梯状态
                elevator = this.elevatorService.get(id);
                boolean updateStatus = this.elevatorService.updateElevatorStatusById(elevator.getId());
                if (updateStatus) {
                } else {
                    result = new Result(false, "更新电梯状态失败!");
                }
            } else {
                result = new Result(true, "技师维保信息修改成功!");
            }
        }catch (Exception e){
            e.printStackTrace();
            result = new Result(false,"技师维保信息保存失败!");
        }
        return RESULT;
    }

    /**  维保记录：--逻辑删除
     * @author: fumin
     * @date: 2018/12/25
     * @description:
     * @return: RESULT 返回提示信息到页面
     */
    public String logicalDelMaintenanceRecord(){
        try {
            //获得当前维保表的id
            String id = this.getHttpServletRequest().getParameter("id");
            entity = this.maintenanceRecordService.get(id);

            //判断是否已经被删除
            boolean judgeChanges = this.judgeChanges(id,entity);
            if (judgeChanges){
            }else {
                //逻辑删除维保记录
                boolean flag = this.maintenanceRecordService.logicalDeleteElevator(entity);
                if (flag) {
                    result = new Result(true, "维保记录删除成功!");

                    //删除维保中的维保记录后，故障申报的故障状态回退为“维保中”
                    String elevatorTroubleId = entity.getElevatorTroubleId();
                    this.troubleDeclareService.updateTroubleStatus(elevatorTroubleId, "2");

                    //更新电梯状态
                    boolean updateStatus = this.elevatorService.updateElevatorStatusById(entity.getElevatorId());
                    if (updateStatus) {
                    } else {
                        result = new Result(false, "更新电梯状态失败!");
                    }
                } else {
                    result = new Result(false, "维保记录删除失败!");
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            result = new Result(false, "维保记录删除失败!");
        }
        return RESULT;
    }

    /**物业确认(维保记录)信息
     * @author: fumin
     * @date: 2018/12/26
     * @description:
     * @return: 跳转至物业确认页面
     */
    public String confirmMaintenanceRecord(){
        try {
            //获得数据id和需要确认数据的维保状态
            String id = this.getHttpServletRequest().getParameter("id");
            //根据id获得对应维保记录信息实体类
            entity = this.maintenanceRecordService.get(id);
            //获得维保状态 0-维保中 1-已完成
            confirmMaintenanceStatus = entity.getMaintenanceStatus();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "maintenanceRecord_confirm";
    }

    /**
     * 物业确认（维保记录）信息保存
     * @author: fumin
     * @date: 2018/12/26
     * @description:
     * @return:
     */
    public String confirmMaintenanceRecordSave(){
        try {
            //判断数据状态是否已经被改变
            boolean judgeChanges = this.judgeChanges(entity.getId(), entity);
            if (judgeChanges) {
            }else {
                //保存当前表单信息 flag-true保存成功 false保存失败
                boolean flag = this.maintenanceRecordService.confirmMaintenanceRecord(entity);
                if (flag) {
                    result = new Result(true, "维保记录信息确认成功！");

                    //获得当前维保信息对应故障申报实体
                    if (!"".equals(entity.getElevatorTroubleId())) {
                        troubleDeclareEntity = this.getTroubleDeclareService().get(entity.getElevatorTroubleId());
                        //更改故障申报状态为已完成
                        this.troubleDeclareService.updateTroubleStatus(troubleDeclareEntity.getId(), "4");
                    }
                    //更新电梯状态
                    entity = this.maintenanceRecordService.get(entity.getId());
                    boolean updateStatus = this.elevatorService.updateElevatorStatusById(entity.getElevatorId());
                    if (updateStatus) {
                    } else {
                        result = new Result(false, "更新电梯状态失败!");
                    }
                } else {
                    result = new Result(false, "维保记录信息确认失败!");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            result = new Result(false,"维保记录信息确认失败！");
        }
        return RESULT;
    }


    /**多浏览器操作**/
    public boolean judgeChanges(String id, MaintenanceRecord maintenanceRecord){
        boolean judgeChanges = false;
        MaintenanceRecord newMaintenanceRecord = this.maintenanceRecordService.get(id);
        //是否删除判断
        if ("1".equals(newMaintenanceRecord.getIsDel())){
            judgeChanges = true;
            result = new Result(false,"当前数据已被删除，请刷新页面！");
        }else {
            //是否已经被改变状态
            if (newMaintenanceRecord.getMaintenanceStatus().equals(maintenanceRecord.getMaintenanceStatus())) {
            } else {
                judgeChanges = true;
                result = new Result(false, "当前数据状态已被改变，请刷新页面！");
            }
        }
        return judgeChanges;
    }

    //----------------------get set----------------

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public Elevator getElevator() {
        return elevator;
    }

    public void setElevator(Elevator elevator) {
        this.elevator = elevator;
    }

    public ElevatorService getElevatorService() {
        return elevatorService;
    }

    public void setElevatorService(ElevatorService elevatorService) {
        this.elevatorService = elevatorService;
    }

    public IMaintenanceRecordService getMaintenanceRecordService() {
        return maintenanceRecordService;
    }

    public void setMaintenanceRecordService(IMaintenanceRecordService maintenanceRecordService) {
        this.maintenanceRecordService = maintenanceRecordService;
    }
    public TroubleDeclare getTroubleDeclareEntity() {
        return troubleDeclareEntity;
    }

    public void setTroubleDeclareEntity(TroubleDeclare troubleDeclareEntity) {
        this.troubleDeclareEntity = troubleDeclareEntity;
    }

    public TroubleDeclareService getTroubleDeclareService() {
        return troubleDeclareService;
    }

    public void setTroubleDeclareService(TroubleDeclareService troubleDeclareService) {
        this.troubleDeclareService = troubleDeclareService;
    }

    public String getConfirmMaintenanceStatus() {
        return confirmMaintenanceStatus;
    }

    public void setConfirmMaintenanceStatus(String confirmMaintenanceStatus) {
        this.confirmMaintenanceStatus = confirmMaintenanceStatus;
    }

    public String getMyPermission() {
        return myPermission;
    }

    public void setMyPermission(String myPermission) {
        this.myPermission = myPermission;
    }

    public Date getLocalDate() {
        return localDate;
    }

    public void setLocalDate(Date localDate) {
        this.localDate = localDate;
    }

    public String getTroubleStatus() {
        return troubleStatus;
    }

    public void setTroubleStatus(String troubleStatus) {
        this.troubleStatus = troubleStatus;
    }

    public List<User> getTechnicianUserList() {
        return technicianUserList;
    }

    public void setTechnicianUserList(List<User> technicianUserList) {
        this.technicianUserList = technicianUserList;
    }

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }
}
