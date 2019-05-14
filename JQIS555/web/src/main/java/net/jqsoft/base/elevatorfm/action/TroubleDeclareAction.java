package net.jqsoft.base.elevatorfm.action;

import net.jqsoft.base.elevatorfm.domain.Elevator;
import net.jqsoft.base.elevatorfm.domain.MaintenanceRecord;
import net.jqsoft.base.elevatorfm.domain.TroubleDeclare;
import net.jqsoft.base.elevatorfm.service.ITroubleDeclareService;
import net.jqsoft.base.elevatorfm.service.impl.ElevatorService;
import net.jqsoft.base.elevatorfm.service.impl.MaintenanceRecordService;
import net.jqsoft.base.system.domain.User;
import net.jqsoft.base.system.service.impl.UserService;
import org.zcframework.security.model.Role;
import net.jqsoft.common.action.MyEntityAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.view.support.Result;
import org.zcframework.security.SpringSecurityUtils;
import org.zcframework.security.object.Loginer;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**电梯故障申报action
 * @author: Administrator
 * @date: 2018/12/17
 * @description:
 */
public class TroubleDeclareAction extends MyEntityAction<TroubleDeclare,ITroubleDeclareService>{
    /** 电梯实体类 **/
    private Elevator elevator;
    private ElevatorService elevatorService;

    /**维保记录实体类**/
    private MaintenanceRecord maintenanceRecord;
    private MaintenanceRecordService maintenanceRecordService;

    /**维保记录实体类**/
    private User user;
    private UserService userService;

    /** 页面操作类型：add-新增 edit-编辑 view-查看**/
    private String types;

    /** 电梯故障状态：1-已提交 2-维修中 3-待确认 4-已完成 **/
    private String confirmTroubleStatus;

    /** 维保状态：1-维保中 2-已完成 **/
    private String confirmMaintenanceStatus;

    /** 当前登录角色：wyfm-物业权限 jsfm-技师权限 yezhu_fumin-业主权限**/
    private String myPermission;

    /** 当前登录用户名**/
    private String loginUserName = SpringSecurityUtils.getCurrentUserName();

    /**获得当前时间**/
    private Date  localDate = new Date();

    /**技师用户集合List**/
    private List<User> technicianUserList;

    /** 当前故障申报信息创建人**/
    private String troubleCreator;

    @Autowired
    private ITroubleDeclareService troubleDeclareService;

    @Override
    protected ITroubleDeclareService getEntityManager(){
        return troubleDeclareService;
    }

    @Override
    public String list() {
        //根据前台传入id获得对应电梯信息实体类
        String elevatorId = this.getHttpServletRequest().getParameter("id");
        elevator= this.elevatorService.get(elevatorId);
        //获取前端传来的所有数据，并用map保存
        Map<String, Object> params = getActionContext().getParameters();
        //遍历map，拿出值，去空格
        for (Map.Entry entry : params.entrySet()) {
            if (entry.getValue() instanceof String[]) {
                String[] vals = (String[]) entry.getValue();
                entry.setValue(new String[]{vals[0].toString().trim()});
            }
        }
        entity.setElevatorId(elevatorId);
        params.put("elevatorId",new String[]{entity.getElevatorId()});

        //获得当前登录用户
        myPermission = this.troubleDeclareService.getLoginer();
        return super.list();
    }

    /** 故障申报：--新增、编辑、查看
     * @author: fumin
     * @date: 2018/12/17
     * @description: 根据types的值判断操作状态 add-新增 edit-编辑 view-查看
     * @return: RESULT 返回提示信息到页面
     */
    public String troubleDeclare(){
        //flag用于判断是否有异常
        int flag = 0;
        //用于控制页面的状态
        String viewType = "troubleDeclare_view";
        try{
            //获得前台传入故障表id 操作状态和用户权限状态
            String id = this.getHttpServletRequest().getParameter("id");
            types = this.getHttpServletRequest().getParameter("types");
            myPermission = this.getHttpServletRequest().getParameter("myPermission");

            //如果是新增 id为null 直接跳转至新增页面；如果是编辑或查看 id不为空 通过id获得所选中数据实体类
            if ("add".equals(types)) {
            } else {
                entity = this.troubleDeclareService.get(id);

                //判断数据状态是否被改变
                boolean judgeChanges = this.judgeChanges(id,entity);
                if (judgeChanges){
                }else {
                    //只能编辑自己新增的数据
                    if ("edit".equals(types) && (!loginUserName.equals(entity.getCreatorName()))) {
                        result = new Result(false, "当前信息不是您新增的，您不能编辑！");
                        flag = 1;
                    } else {
                        if ("view".equals(types)) {
                            String elevatorTroubleId = entity.getId();
                            confirmTroubleStatus = entity.getTroubleStatus();
                            troubleCreator = entity.getCreatorName();
                            //如果是技师维保/物业确认维保信息，获得与之关联的维保记录的实体类
                            if ("3".equals(confirmTroubleStatus) || "4".equals(confirmTroubleStatus)) {
                                maintenanceRecord = this.maintenanceRecordService.getMaintenanceRecord(elevatorTroubleId);
                                //获得维保状态 0-维保中 1-已完成
                                confirmMaintenanceStatus = maintenanceRecord.getMaintenanceStatus();
                            }
                            viewType = "troubleDeclare_confirm";
                        }
                    }
                }
            }
            //获得技师用户列表
            technicianUserList = this.troubleDeclareService.technicianUserList();
        }catch (Exception e){
            flag =1;
            e.printStackTrace();
            result = new Result(false,"故障申报信息查询失败");
        }finally {
            if (flag == 0){
                return viewType;
            }else {
                return RESULT;
            }
        }
    }
    /**故障申报保存
     * @author: fumin
     * @date: 2018/12/17
     * @description: 根据types的值判断操作状态
     * @return: RESULT 返回提示信息到页面
     */
    public String troubleDeclareSave(){
        try{
            //获得当前电梯的id和操作状态 add-新增 edit-编辑
            types = this.getHttpServletRequest().getParameter("types");
            String id = this.getHttpServletRequest().getParameter("id");
            String myPermission = this.getHttpServletRequest().getParameter("myPermission");

            if ("add".equals(types)){
                //从电梯信息表中获得故障申报所需要的列数据
                elevator= this.elevatorService.get(id);
                entity.setElevatorId(id);

                //技师或业主新增，故障状态为已提交；物业新增，故障状态为维保中
                if ("wyfm".equals(myPermission)){
                    entity.setTroubleStatus("2");
                }else {
                    entity.setTroubleStatus("1");
                }
            }else {
                //判断数据状态是否被改变
                boolean judgeChanges = this.judgeChanges(id,entity);
                if (judgeChanges){
                    return RESULT;
                }
            }
            //将当前表单数据保存，并根据返回值，弹出提示信息，1-新增 2-编辑
            int res = this.troubleDeclareService.TroubleDeclareSave(entity, types);
            if (res == 1) {
                result = new Result(true, "故障申报信息新增成功！");

                //更新电梯状态
                boolean updateStatus = this.elevatorService.updateElevatorStatusById(entity.getElevatorId());
                if (updateStatus) {
                } else {
                    result = new Result(false, "电梯状态更新失败！");
                }
            } else {
                result = new Result(true, "故障申报信息修改成功！");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return RESULT;
    }

    /**逻辑删除故障申报信息
     * @author: fumin
     * @date: 2018/12/19
     * @description: 根据id将其删除标识设为1
     * @return: RESULT 返回提示信息到页面
     */
    public String localDeleteTroubleDeclare(){
        try {
            String id = this.getHttpServletRequest().getParameter("id");
            myPermission = this.getHttpServletRequest().getParameter("myPermission");

           //根据id获得对应的故障申报实体类
            entity = this.troubleDeclareService.get(id);

            //判断数据状态是否被改变
            boolean judgeChanges = this.judgeChanges(id,entity);
            if (judgeChanges){
            }else {
                //只有“已提交”状态 或者 物业自己新增的故障申报信息 才能删除
                boolean bang1 = "1".equals(entity.getTroubleStatus());
                boolean bang2 = ("wyfm".equals(myPermission))&&(loginUserName.equals(entity.getCreatorName()))&&("2".equals(entity.getTroubleStatus()));
                if (bang1 || bang2 ){
                    boolean flag = this.troubleDeclareService.localDeleteTroubleDeclare(entity);
                    if (flag) {
                        result = new Result(true, "故障申报信息删除成功！");

                        //更新电梯状态
                        entity = this.troubleDeclareService.get(id);
                        boolean updateStatus = this.elevatorService.updateElevatorStatusById(entity.getElevatorId());
                        if (updateStatus) {
                        } else {
                            result = new Result(false, "电梯状态更新失败！");
                        }
                    }
                } else {
                    result = new Result(false, "当前数据不是您新增的，您无权删除！");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            result = new Result(false,"故障申报信息删除失败！");
        }finally {
        }
        return RESULT;
    }

    /**物业确认-故障申报信息（第一次确认）
     * @author: fumin
     * @date: 2018/12/21
     * @description:
     * @return: 跳转至物业确认页面
     */
    public String confirmTroubleDeclare(){
        //用于判断是否进行确认
        int flag = 0;
        try {
            //获得数据id和需要确认数据的故障状态
            String id = this.getHttpServletRequest().getParameter("id");
            confirmTroubleStatus = entity.getTroubleStatus();
            //根据id获得对应故障申报信息实体类
            entity = this.troubleDeclareService.get(id);
            //判断数据状态是否被改变
            boolean judgeChanges = this.judgeChanges(id,entity);
            if (judgeChanges){
                return RESULT;
            }else {
                //只有“已提交”状态才能确认-故障申报信息
                if ("1".equals(entity.getTroubleStatus())) {
                    //获得技师用户列表
                    technicianUserList = this.troubleDeclareService.technicianUserList();
                    flag = 1;
                } else {
                    flag = 2;
                    result = new Result(false, "只有“已提交”“待确认”状态才能确认！");
                }
            }

        }catch (Exception e){
            flag = 2;
            e.printStackTrace();
        }
         if (flag == 1){
             return "troubleDeclare_confirm";
         }else {
             return RESULT;
        }
    }
    /**
     * 物业确认-故障申报信息（第一次确认）-保存
     * @author: fumin
     * @date: 2018/12/21
     * @description: troubleStatus 1-已提交 2-维修中 3-待确认 4-已完成
     * @return:
     */
    public String confirmTroubleDeclareSave(){
        try {
            //判断数据状态是否被改变
            boolean judgeChanges = this.judgeChanges(entity.getId(),entity);
            if (judgeChanges){
            }else {
                //将故障状态更改为维修中，并保存表单信息
                int flag = this.troubleDeclareService.confirmTroubleDeclare(entity);
                if (flag == 1) {
                    result = new Result(true, "故障申报信息确认成功！");

                    //更新电梯状态
                    boolean updateStatus = this.elevatorService.updateElevatorStatusById(entity.getElevatorId());
                    if (updateStatus) {
                    } else {
                        result = new Result(false, "电梯状态更新失败！");
                    }

                } else {
                    result = new Result(false, "故障申报信息确认失败！");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            result = new Result(false,"故障申报信息确认失败！");
        }finally {
        }
        return RESULT;
    }
    /**
     * 技师维保（故障申报信息）
     * @author: fumin
     * @date: 2018/12/26
     * @description:
     * @return: 跳转至技师维保页面
     */
    public String troubleDeclareMaintenance(){
        //用于判断是否进行确认
        int flag = 0;
        try {
            //获得数据id和需要确认数据的故障状态
            String id = this.getHttpServletRequest().getParameter("id");
            confirmTroubleStatus = entity.getTroubleStatus();
            myPermission = this.getHttpServletRequest().getParameter("myPermission");
            //根据id获得对应故障申报信息实体类
            entity = this.troubleDeclareService.get(id);

            //判断数据状态是否被改变
            boolean judgeChanges = this.judgeChanges(id,entity);
            if (judgeChanges){
            }else {

                //只有故障状态为维保中，技师才能进行维保
                if ("jsfm".equals(myPermission) && "2".equals(entity.getTroubleStatus())) {
                    //获得与之关联维保记录的实体类
                    maintenanceRecord = this.maintenanceRecordService.getMaintenanceRecord(entity.getId());
                    if (maintenanceRecord != null) {
                        //获得当前数据的维保状态 0-维保中 1-已完成
                        confirmMaintenanceStatus = maintenanceRecord.getMaintenanceStatus();
                    }
                    //获得技师用户列表
                    technicianUserList = this.troubleDeclareService.technicianUserList();
                    flag = 1;
                } else {
                    flag = 2;
                    result = new Result(false, "只有“维保中”的数据才能进行该操作！");
                }
            }
        }catch (Exception e){
            flag = 2;
            e.printStackTrace();
        }finally {
            if (flag == 1){
                return "troubleDeclare_confirm";
            }else {
                return RESULT;
            }
        }
    }
    /**
     * 技师维保（故障申报信息）保存
     * @author: fumin
     * @date: 2018/12/26
     * @description:
     * @return:
     */
    public String troubleDeclareMaintenanceSave(){
        try {
            //获得当前故障表的id 和当前登录角色
            myPermission = this.getHttpServletRequest().getParameter("myPermission");
            String  id = entity.getId();
            //判断数据状态是否被改变
            boolean judgeChanges = this.judgeChanges(id,entity);
            if (judgeChanges){
            }else {
                //从故障申报表中拿到维保记录需要的字段，保存至维保记录表
                maintenanceRecord.setElevatorTroubleId(entity.getId());
                maintenanceRecord.setElevatorId(entity.getElevatorId());
                maintenanceRecord.setMaintenanceStatus("1");
                maintenanceRecord.setMaintenanceType("1");

                int nis = this.maintenanceRecordService.technicianMaintenanceSave(maintenanceRecord, "add");
                if (nis != 0) {
                    result = new Result(true, "技师维保信息添加成功!");
                    //更新故障状态为待确认
                    this.troubleDeclareService.updateTroubleStatus(id, "3");

                    //更新电梯状态
                    entity = this.troubleDeclareService.get(id);
                    this.elevatorService.updateElevatorStatusById(entity.getElevatorId());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            result = new Result(false,"技师维保信息保存失败!");
        }
        return RESULT;
    }

    /**物业确认-维保记录信息（第二次确认）
     * @author: fumin
     * @date: 2018/12/21
     * @description:
     * @return: 跳转至物业确认页面
     */
    public String confirmMaintenanceRecord(){
        //用于判断是否进行确认
        int flag = 0;
        try {
            //获得数据id和需要确认数据的故障状态
            String id = this.getHttpServletRequest().getParameter("id");
            confirmTroubleStatus = entity.getTroubleStatus();

            //根据id获得对应故障申报信息实体、维保记录实体
            entity = this.troubleDeclareService.get(id);
            maintenanceRecord = this.maintenanceRecordService.getMaintenanceRecord(entity.getId());


            //判断数据状态是否被改变
            boolean judgeChanges = this.judgeChanges(id,entity);
            if (judgeChanges){
            }else {
                //只有故障状态为待确认 或者 维保状态为维保中，才能确认维保记录信息
                if (("3".equals(entity.getTroubleStatus()) || ("1".equals(maintenanceRecord.getMaintenanceStatus())))) {
                    //获得技师用户列表
                    technicianUserList = this.troubleDeclareService.technicianUserList();
                    flag = 1;
                } else {
                    flag = 2;
                    result = new Result(false, "当前状态不能进行物业确认！");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            flag = 2;
        }
        if (flag ==1){
            return "troubleDeclare_confirm";
        }else {
            return RESULT;
        }

    }
    /**
     * 物业确认-维保记录信息（第二次确认）-保存
     * @author: fumin
     * @date: 2018/12/21
     * @description: troubleStatus 1-已提交 2-维修中 3-待确认 4-已完成
     * @return:
     */
    public String confirmMaintenanceRecordSave(){
        try{
            //获得当前故障表的id
            String myPermission = this.getHttpServletRequest().getParameter("myPermission");
            String  id = entity.getId();
            //判断数据状态是否被改变
            boolean judgeChanges = this.judgeChanges(id,entity);
            if (judgeChanges){
            }else {
                //更改维保状态为已完成
                maintenanceRecord.setMaintenanceStatus("2");
                //调用维保记录新增方法，保存至维保记录表
                int nis = this.maintenanceRecordService.technicianMaintenanceSave(maintenanceRecord, "edit");
                if (nis != 0) {
                    result = new Result(true, "维保记录信息确认成功！");

                    //更改故障状态为已完成
                    this.troubleDeclareService.updateTroubleStatus(id, "4");

                    //更新电梯状态
                    entity = this.troubleDeclareService.get(id);
                    boolean updateStatus = this.elevatorService.updateElevatorStatusById(entity.getElevatorId());
                    if (updateStatus) {
                    } else {
                        result = new Result(false, "电梯状态更新失败！");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            result = new Result(false,"维保记录信息确认失败！");
        }
        return RESULT;
    }

    /****
     * 获得技师列表集合
     * @return
     */
    public String addTechnicianOtherList(){
        technicianUserList = this.troubleDeclareService.technicianUserList();
        return "technicianOtherList";
    }


    /**多浏览器操作**/
    public boolean judgeChanges(String id, TroubleDeclare troubleDeclare){
        boolean judgeChanges = false;
        TroubleDeclare newTroubleDeclare = this.troubleDeclareService.get(id);
        //是否删除判断
        if ("1".equals(newTroubleDeclare.getIsDel())){
            judgeChanges = true;
            result = new Result(false,"当前数据已被删除，请刷新页面！");
        }else {
            //是否已经被改变状态
            if (newTroubleDeclare.getTroubleStatus().equals(troubleDeclare.getTroubleStatus())) {
            } else {
                judgeChanges = true;
                result = new Result(false, "当前数据状态已被改变，请刷新页面！");
            }
        }
        return judgeChanges;
    }

    //-----------------GET OR SET-------------------------------

    public ITroubleDeclareService getTroubleDeclareService() {
        return troubleDeclareService;
    }

    public void setTroubleDeclareService(ITroubleDeclareService troubleDeclareService) {
        this.troubleDeclareService = troubleDeclareService;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getConfirmTroubleStatus() {
        return confirmTroubleStatus;
    }

    public void setConfirmTroubleStatus(String confirmTroubleStatus) {
        this.confirmTroubleStatus = confirmTroubleStatus;
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

    public String getMyPermission() {
        return myPermission;
    }

    public void setMyPermission(String myPermission) {
        this.myPermission = myPermission;
    }

    public MaintenanceRecord getMaintenanceRecord() {
        return maintenanceRecord;
    }

    public void setMaintenanceRecord(MaintenanceRecord maintenanceRecord) {
        this.maintenanceRecord = maintenanceRecord;
    }

    public MaintenanceRecordService getMaintenanceRecordService() {
        return maintenanceRecordService;
    }

    public void setMaintenanceRecordService(MaintenanceRecordService maintenanceRecordService) {
        this.maintenanceRecordService = maintenanceRecordService;
    }

    public Date getLocalDate() {
        return localDate;
    }

    public void setLocalDate(Date localDate) {
        this.localDate = localDate;
    }

    public String getConfirmMaintenanceStatus() {
        return confirmMaintenanceStatus;
    }

    public void setConfirmMaintenanceStatus(String confirmMaintenanceStatus) {
        this.confirmMaintenanceStatus = confirmMaintenanceStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public List<User> getTechnicianUserList() {
        return technicianUserList;
    }

    public void setTechnicianUserList(List<User> technicianUserList) {
        this.technicianUserList = technicianUserList;
    }

    public String getTroubleCreator() {
        return troubleCreator;
    }

    public void setTroubleCreator(String troubleCreator) {
        this.troubleCreator = troubleCreator;
    }

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }
}
