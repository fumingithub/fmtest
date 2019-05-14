package net.jqsoft.base.system.action;

import net.jqsoft.base.system.domain.Travel;
import net.jqsoft.base.system.service.ITravelService;
import net.jqsoft.common.action.MyEntityAction;
import net.jqsoft.common.exception.ManagerException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.view.support.Result;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: fm
 * @date: 2018/11/5
 * @description:
 */
public class TravelAction extends MyEntityAction<Travel,ITravelService>{
    @Autowired
    private ITravelService travelService;

    @Override
    protected ITravelService getEntityManager() {
        return travelService;
    }

    /** 景点List集合 */
    private List<Travel> travelList;

    /** 所在省会实体对象 */
    private Travel parent;

    /** 操作类型：edit-编辑，view-查看, add-新增 */
    private String types;

    /**
     * 默认调用方法
     * @return java.lang.String
     * @author fm
     **/
    public String list() {

        return super.list();

    }

    /**
     * 重写编辑和新增方法，获取父组织机构
     **/
    public String edit() {

        boolean flag = true;

        try {
            String id = this.getHttpServletRequest().getParameter("id");
            types = this.getHttpServletRequest().getParameter("types");

            if ("add".equals(types)) { // 新增

                if (StringUtils.isNotEmpty(id)) {
                    parent = this.travelService.get(id);
                }
            }
            if ("edit".equals(types)) { // 编辑（列表）

                entity = this.travelService.get(id);

                Travel travel = null;
                if (StringUtils.isNotEmpty(entity.getCode())) {
                    travel = this.travelService.get(entity.getCode());
                }
                if (null != travel) {
                    entity.setProvinceName(travel.getName());
                }
            }
            if ("view".equals(types)){
                entity = this.travelService.get(id);
            }

        } catch (ManagerException ex) {
            flag = false;
            result = new Result(false, ex.getErrMsg(), "", false);
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
            result = new Result(false, "查询景点信息异常！（ACTION）", "", false);
        } finally {
            if (flag) {
                return "dialog";
            } else {
                return "_result";
            }
        }
    }

    /**
     * 新增/修改 景点信息
     **/
    public String save() {

        try {
            types = this.getHttpServletRequest().getParameter("types");
            // 1、对景点编码进行唯一性验
                if (null != entity && StringUtils.isNotEmpty(entity.getCode())) {
                    String code = entity.getCode();
                    Travel newTravel = this.travelService.getInfoByCode(code);//根据code获取旅游实体类
                    if ( newTravel != null ) {
                        result = new Result(false, "该景点编码已经存在！");
                        return "_result";
                    }
                }


            // 2、进行新增/修改操作
            int res = this.travelService.saveOrUpdateTravel(entity);
            if (res == 1) { //新增
                String forward = getHttpServletRequest().getRequestURI();
                String queryString = getHttpServletRequest().getQueryString();
                if (StringUtils.isNotEmpty(queryString)) {
                    forward = forward + "?" + queryString + "&id=" + entity.getId();
                } else {
                    forward = forward + "?id=" + entity.getId();
                }
                result = new Result(true, "保存景点信息成功！", forward, false);
            } else { //修改
                result = new Result(true, "更新景点信息成功！");
            }
        } catch (ManagerException ex) {
            result = new Result(false, ex.getErrMsg(), "", false);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "保存景点信息异常！（ACTION）", "", false);
        } finally {
            // 3、返回提示信息到页面
            return "_result";
        }
    }

    /**
     * 组织机构删除
     * @return java.lang.String
     **/
    public String delete() {
                travelService.removeById(entity.getId());
                result = new Result(true, "删除成功！", "", false);

        return "_result";
    }


    public ITravelService getTravelService() {
        return travelService;
    }

    public void setTravelService(ITravelService travelService) {
        this.travelService = travelService;
    }

    public List<Travel> getTravelList() {
        return travelList;
    }

    public void setTravelList(List<Travel> travelList) {
        this.travelList = travelList;
    }

    public Travel getParent() {
        return parent;
    }

    public void setParent(Travel parent) {
        this.parent = parent;
    }


    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }


}
