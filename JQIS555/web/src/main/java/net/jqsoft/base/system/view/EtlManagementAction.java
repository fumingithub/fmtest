/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.view;

import net.jqsoft.base.system.domain.EquipmentInfor;
import net.jqsoft.base.system.domain.EtlManagement;
import net.jqsoft.base.system.service.IEquipmentInforService;
import net.jqsoft.base.system.service.IEtlManagementService;
import net.jqsoft.common.action.MyEntityAction;
import net.jqsoft.common.exception.ManagerException;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.core.view.support.Result;


/**
 * EtlManagementAction
 * @author wangtao@jqsoft.net
 * @version 1.0
 */
public class EtlManagementAction extends MyEntityAction<EtlManagement, IEtlManagementService> {
	//types:edit--编辑   view--查看
	private String types;  
	@Autowired
    private IEtlManagementService etlManagementService;
	@Autowired
	private IEquipmentInforService equipmentInforService;
    @Override
    protected IEtlManagementService getEntityManager() {
        return etlManagementService;
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
        String[] idarrEtl = (String[]) params.get("id");
        String[] EtlId = (String[]) params.get("etlId");
        String[] typesEtl = (String[]) params.get("types");
        if (null != EtlId && EtlId.length > 0 && StringUtils.isNotEmpty(EtlId[0])) {
        	EquipmentInfor equipmentInfor = equipmentInforService.get(EtlId[0]);
        	entity.setEquipmentId(equipmentInfor.getId());
        }
        if(typesEtl!=null){
           types = typesEtl[0];            
        }
        if(!(idarrEtl != null && idarrEtl.length > 0)){
            return super.edit();
        }
//        Map<String, Object> idMap = new HashMap<String, Object>();
//        idMap.put("id",((String[])params.get("id"))[0]);
        //idMap.put("projectId",entity.getProjectId());
        entity = etlManagementService.get(idarrEtl[0]);
        return INPUT;
    }
    /**
	 * 数据库增加or修改保存
	 * @return java.lang.String
	 * @author wangtao
	 * @create 2017-10-19
	 */
	public String save() {
		try {
			int res = this.etlManagementService.saveOrUpdateEtl(entity);
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
