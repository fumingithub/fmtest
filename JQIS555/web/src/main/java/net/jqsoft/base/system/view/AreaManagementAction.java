/**
. * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.view;

import net.jqsoft.base.system.domain.AreaManagement;
import net.jqsoft.base.system.domain.DatabaseInfoManagement;
import net.jqsoft.base.system.domain.EquipmentInfor;
import net.jqsoft.base.system.service.IAreaManagementService;
import net.jqsoft.base.system.service.IDatabaseInfoManagementService;
import net.jqsoft.base.system.service.IEquipmentInforService;
import net.jqsoft.common.action.MyEntityAction;
import net.jqsoft.common.exception.ManagerException;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.view.support.Result;
import org.zcframework.core.view.support.entity.BaseEntityAction;

/**
 * AreaManagementAction
 * @author wangtao@jqsoft.net
 * @version 1.0
 */
public class AreaManagementAction extends MyEntityAction<AreaManagement, IAreaManagementService> {
	private String types;  //types:edit--编辑   view--查看
	@Autowired
    private IAreaManagementService areaManagementService;
    @Autowired
    private IEquipmentInforService equipmentInforService;
    @Override
    protected IAreaManagementService getEntityManager() {
        return areaManagementService;
    }
    
    /**
     * 查询数据（列表页）
     * @return java.lang.String
     * @author wangtao
     * @create 2017-10-23
     **/
    public String list() {
		String domianId = this.getHttpServletRequest().getParameter("id");
		EquipmentInfor equipmentInfor=equipmentInforService.get(domianId);
		String DomainType=equipmentInfor.getType();
		if("02".equals(DomainType)){
			Map<String,Object> params = getActionContext().getParameters();
            params.put("domianId", new Object[]{domianId});
            super.list();      
           return SUCCESS;	
		}
		else{
			result = new Result(false, "不是前置机服务器不能进行域管理！");		
		}
		return RESULT;
        
    }
    
    /**
     * 跳转到编辑页面
     * @return java.lang.String
     * @author wangtao
     * @create 2017-10-19
     **/
    public String edit() {
        Map<String, Object> params = getActionContext().getParameters();
        //String[] arr = (String[]) params.get("codeCode");
        String[] idarrdommain = (String[]) params.get("id");
        String[] domainId = (String[]) params.get("domain");
        String[] typesarrdomain = (String[]) params.get("types");
        if (null != domainId && domainId.length > 0 && StringUtils.isNotEmpty(domainId[0])) {
        	EquipmentInfor equipmentInfor = equipmentInforService.get(domainId[0]);
        	entity.setEquipmentId(equipmentInfor.getId());
        }
        if(typesarrdomain!=null){
           types = typesarrdomain[0];            
        }
        if(!(idarrdommain != null && idarrdommain.length > 0)){
            return super.edit();
        }
//        Map<String, Object> idMap = new HashMap<String, Object>();
//        idMap.put("id",((String[])params.get("id"))[0]);
        //idMap.put("projectId",entity.getProjectId());
        entity = areaManagementService.get(idarrdommain[0]);
        return INPUT;
    }
    
    /**
	 * 数据库信息删除
	 * author  wangtao
	 * @createTime  2017-10-25
	 * @return
	 */
	public String delete(){
		try {
			if (StringUtils.isNotBlank(entity.getId())) {
				AreaManagement areaManagement = this.areaManagementService.get(entity.getId());
				if ("0".equals(areaManagement.getIsDel())) {
					this.areaManagementService.deleteDomainInfo(entity.getId());
					result = new Result(true,"删除域信息成功!");
				} 				
			} 			
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(false, getText("删除域信息失败!"));
		}
		return RESULT;		
	}
	
    /**
	 * 数据库增加or修改
	 * @return java.lang.String
	 * @author wangtao
	 * @create 2017-10-18
	 */
	public String save() {
		try {
			int res = this.areaManagementService.saveOrUpdateArea(entity);
			if (res == 1) {
				String forward = getHttpServletRequest().getRequestURI();
				String queryString = getHttpServletRequest().getQueryString();
				if (queryString != null && !queryString.equals(""))
					forward = forward + "?" + queryString + "&id=" + entity.getId();
				else
					forward = forward + "?id=" + entity.getId();
				result = new Result(true, "保存数据库信息成功", forward, false);
			} else {
				result = new Result(true, "更新数据库信息成功");
			}
		} catch (ManagerException ex) {
			result = new Result(false, ex.getErrMsg(), "", false);
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(false, "数据库信息保存异常！", "", false);
		} finally {
			// 4、返回提示信息到页面
			return RESULT;
		}
	}
    public String getTypes() {
        return types;
    }
    public void setTypes(String types) {
        this.types = types;
    }
}
