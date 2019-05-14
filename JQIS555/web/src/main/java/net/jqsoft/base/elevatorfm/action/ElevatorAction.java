package net.jqsoft.base.elevatorfm.action;

import com.google.common.collect.Maps;
import net.jqsoft.common.action.MyEntityAction;
import net.jqsoft.base.elevatorfm.domain.Elevator;
import net.jqsoft.base.elevatorfm.service.IElevatorService;
import net.jqsoft.common.exception.ManagerException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.view.support.Result;

import java.util.Map;

/**
 * 电梯信息管理action层
 * @author: fumin
 * @date: 2018/12/5
 * @description:
 */
public class ElevatorAction extends MyEntityAction<Elevator,IElevatorService>{

    @Autowired
    private IElevatorService elevatorService;

    @Override
    protected IElevatorService getEntityManager() {
        return elevatorService;
    }

    /** 默认调用方法 **/
    @Override
    public String list() {
        return super.list();
    }

    /** 操作类型：add-新增 edit-编辑 view-查看**/
    private String types;

    /**电梯信息新增,修改
     * @author: fumin
     * @date: 2018/12/6
     * @description: 根据types的值判断操作状态 add-新增 edit-编辑 view-查看
     * @return: RESULT 返回提示信息到页面
     */
    public String updateElevator() {
        //用于判断是否存在异常
        boolean flag = true;
        try {
            //获得前台选中数据的id和按钮操作类型
            String id = this.getHttpServletRequest().getParameter("id");
            String types = this.getHttpServletRequest().getParameter("types");
            //如果是新增 id为null 直接跳转至新增页面；如果是编辑或查看 id不为空 通过id获得所选中数据实体类
            if ("add".equals(types)) {
            } else {
                entity = this.elevatorService.get(id);
            }
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
            result = new Result(false, "查询会议室信息异常！（ACTION）", "", false);
        }
            if (flag) {
                return "elevator_view";
            } else {
                return RESULT;
            }
    }
    /**电梯信息保存
     * @author: fumin
     * @date: 2018/12/7
     * @description: 根据types的值判断操作状态 types：add-新增 edit-编辑
     * @return: RESULT 返回提示信息到页面
     */
    public String elevatorSave(){
        try{
            //获得当前操作状态
            String types = this.getHttpServletRequest().getParameter("types");
                //----唯一性验证
                //1.获得当前表单中的电梯code和id,新增时id为null
                // 2.如果是新增，根据code查找数据库中是否已经存在，返回值为已存在的数量
                // 3.如果是编辑，根据当前表单id查找数据库中对应实体类数据
                String elevatorCode = entity.getElevatorCode();
                String villageName = entity.getVillageName();
                String floor = entity.getFloor();
                String elevatorNumber = entity.getElevatorNumber();
                String id = this.getHttpServletRequest().getParameter("id");

                Map<String, Object> param = Maps.newHashMap();
                param.put("villageName", entity.getVillageName());
                param.put("floor", entity.getFloor());
                param.put("elevatorNumber", entity.getElevatorNumber());
                if ("add".equals(types)) {
                    //判断当前的地方是否已存在相同电梯号的电梯
                    if (this.elevatorService.getInfoByArea(param) > 0) {
                        result = new Result(false, villageName + "的" + floor + "楼已存在" + elevatorNumber + " 号电梯！");
                        return RESULT;
                    }
                    if (this.elevatorService.getInfoByCode(elevatorCode) > 0) {
                        result = new Result(false, "当前电梯编码已存在！");
                        return "_result";
                    }
                } else {
                    //判断数据状态是否已经被删除
                    boolean isDelOrNot = this.isDelOrNot(entity.getId());
                    if (isDelOrNot){
                        return RESULT;
                    }else {
                        Elevator newElevator = this.elevatorService.getInfoById(id);
                        //判断当前的地方是否已存在相同电梯号的电梯
                        if (villageName.equals(newElevator.getVillageName()) && floor.equals(newElevator.getFloor()) && elevatorNumber.equals(newElevator.getElevatorNumber())) {
                        } else {
                            if (this.elevatorService.getInfoByArea(param) > 0) {
                                result = new Result(false, villageName + "的" + floor + "楼已存在" + elevatorNumber + " 号电梯！");
                                return RESULT;
                            }
                        }
                        //判断当前表单中的code是否改动，
                        // 未改动直接保存，已经改动，再根据code查找数据库中是否已经存在，返回值为已存在的数量
                        if (elevatorCode.equals(newElevator.getElevatorCode())) {
                        } else if (this.elevatorService.getInfoByCode(elevatorCode) > 0) {
                            result = new Result(false, "当前电梯编码已存在！");
                            return RESULT;
                        }
                    }
                }

                //对表单信息进行保存，并根据成功后的返回值弹出对应提示信息,1-新增 2-编辑
                int res = this.elevatorService.saveOrUpdateElevator(entity, types);
                if (res == 1) {
                    result = new Result(true, "新增电梯信息成功");
                } else {
                    result = new Result(true, "修改电梯信息成功");
                }

        }catch (Exception e){
            e.printStackTrace();
            result = new Result(false,"保存会议室信息异常！");
        }finally {
            return RESULT;
        }
    }

    /**逻辑删除电梯信息
     * @author: fumin
     * @date: 2018/12/7
     * @description: 根据id将其对应数据的删除标识设为1
     * @return: RESULT 返回提示信息到页面
     */
    public String logicalDelete(){
        try{
            //获得需要删除数据的id
            String id = this.getHttpServletRequest().getParameter("id");
            entity = this.elevatorService.get(id);

            //判断是否已经被删除
            boolean isDelOrNot = this.isDelOrNot(id);
            if (isDelOrNot){

            }else {

                //将其对应数据的删除标识isDel设为1
                boolean flag = this.elevatorService.logicalDeleteElevator(entity);
                if (flag) {
                    result = new Result(true, "删除电梯信息成功");
                } else {
                    result = new Result(false, "删除电梯信息失败");
                }
            }
        }catch (Exception exx){
            exx.printStackTrace();
            throw new ManagerException("100200", new String[]{"电梯信息删除异常！（BIZ）"});
        }
        return RESULT;
    }

    /**
     *  判断是否已经被删除
     */
    public boolean isDelOrNot(String id){
        boolean isDelOrNot = false;

        Elevator newElevator = this.elevatorService.get(id);
        if ("1".equals(newElevator.getIsDel())){
            isDelOrNot = true;
            result = new Result(false,"当前数据已被删除，请刷新页面！");
        }
        return isDelOrNot;
    }

    public IElevatorService getElevatorService() {
        return elevatorService;
    }

    public void setElevatorService(IElevatorService elevatorService) {
        this.elevatorService = elevatorService;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }
}
