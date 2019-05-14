/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.view;

import net.jqsoft.base.system.domain.Application;
import net.jqsoft.base.system.domain.AreaManagement;
import net.jqsoft.base.system.domain.EquipmentInfor;
import net.jqsoft.base.system.service.IApplicationService;
import net.jqsoft.base.system.service.IAreaManagementService;
import net.jqsoft.base.system.service.IEquipmentInforService;
import net.jqsoft.common.action.MyEntityAction;
import net.jqsoft.common.exception.ManagerException;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.view.support.Result;

/**
 * ApplicationAction
 * @author wangtao@jqsoft.net
 * @version 1.0
 */
public class ApplicationAction extends MyEntityAction<Application, IApplicationService> {
	private String types;  //types:edit--编辑   view--查看
	@Autowired
    private IApplicationService applicationService;
    @Autowired
    private IEquipmentInforService equipmentInforService;
    @Override
    protected IApplicationService getEntityManager() {
        return applicationService;
    }
    /**
     * 跳转到新增或编辑页面
     * @return java.lang.String
     * @author wangtao
     * @create 2017-10-19
     **/
    public String edit() {
        Map<String, Object> params = getActionContext().getParameters();
        //String[] arr = (String[]) params.get("codeCode");
        String[] idarrappli = (String[]) params.get("id");
        String[] appliId = (String[]) params.get("applicationId");
        String[] typesarrappli = (String[]) params.get("types");
        if (null != appliId && appliId.length > 0 && StringUtils.isNotEmpty(appliId[0])) {
        	EquipmentInfor equipmentInfor = equipmentInforService.get(appliId[0]);
        	entity.setEquipmentId(equipmentInfor.getId());
        }
        if(typesarrappli!=null){
           types = typesarrappli[0];            
        }
        if(!(idarrappli != null && idarrappli.length > 0)){
            return super.edit();
        }
//        Map<String, Object> idMap = new HashMap<String, Object>();
//        idMap.put("id",((String[])params.get("id"))[0]);
        //idMap.put("projectId",entity.getProjectId());
        entity = applicationService.get(idarrappli[0]);
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
				Application application = this.applicationService.get(entity.getId());
				if ("0".equals(application.getIsDel())) {
					this.applicationService.deleteAppliationInfo(entity.getId());
					result = new Result(true,"删除应用信息成功!");
				} 				
			} 			
		} catch (Exception e) {
			e.printStackTrace();
			result = new Result(false, getText("删除应用信息失败!"));
		}
		return RESULT;		
	}
    /**
	 * 数据库增加or修改保存
	 * @return java.lang.String
	 * @author wangtao
	 * @create 2017-10-19
	 */
	public String save() {
		try {
			int res = this.applicationService.saveOrUpdateApplication(entity);
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
