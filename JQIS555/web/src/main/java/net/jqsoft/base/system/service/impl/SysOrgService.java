/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.base.system.service.impl;

import com.google.common.collect.Maps;
import net.jqsoft.common.cache.service.IEhCacheManager;
import net.jqsoft.common.exception.ManagerException;
import net.jqsoft.common.util.CommonUtil;
import net.jqsoft.base.system.dao.SysOrgDao;
import net.jqsoft.base.system.domain.SysOrg;
import net.jqsoft.base.system.service.ISysOrgService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zcframework.core.dao.IEntityDao;
import org.zcframework.core.service.BaseEntityService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SysOrgService
 * @author jinliang@jqsoft.net
 * @version 1.0
 */
@Service
public class SysOrgService extends BaseEntityService<SysOrg> implements ISysOrgService {

    @Autowired
    private SysOrgDao sysOrgDao;

    @Override
    protected IEntityDao<SysOrg> getDao() { return sysOrgDao; }

    @Override
    public int saveOrUpdateSysOrg(SysOrg entity) throws ManagerException {
        int result = 0; // 影响行数
        try {
            if (StringUtils.isNotEmpty(entity.getId()) && !"undefined".equals(entity.getId())) { // 修改
                this.sysOrgDao.update(entity);
                result = 2;
            } else { // 新增
                entity.setId(CommonUtil.getUUID());
                this.sysOrgDao.create(entity);
                result = 1;
            }
            return result;
        } catch (ManagerException ex) {
            throw ex;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ManagerException("100200", new String[]{"组织机构信息保存异常！（BIZ）"});
        } finally {

        }
    }

    @Override
    public List<SysOrg> querySysOrgList(String parentId) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("parentId", parentId);
        return this.sysOrgDao.getSqlMapClientTemplate().queryForList("SysOrg.getAll", param);
    }

    @Override
    public List<SysOrg> querySysOrgListByParentId(String parentId, String sysOrgName, String transferOrgId, String filterOrgId) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("parentId", parentId);
        param.put("sysOrgName", sysOrgName);
        param.put("transferOrgId", transferOrgId);
        param.put("filterOrgId", filterOrgId);
        return this.sysOrgDao.find("SysOrg.getSysOrgListByParentId", param);
    }

    @Override
    public List<SysOrg> hasSonSysOrgById(String id) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("id", id);
        return this.sysOrgDao.getSqlMapClientTemplate().queryForList("SysOrg.hasSonSysOrgById", param);
    }

    @Override
    public int updateStatusById(String status, String id) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("status", status);
        param.put("id", id);
        return this.sysOrgDao.update("SysOrg.updateStatusById", param);
    }

    @Override
    public SysOrg getInfoByCode(String code) {
        return  this.sysOrgDao.findUniqueBy("SysOrg.getInfoByCode", "code", code);
    }
    
    @Override
	public void updateAuthAreaCode(String transferId, String selected) {
		 try {
			    Map<String, Object> param = Maps.newHashMap();
		        param.put("id", transferId);
		        param.put("authAreaCode", selected);
		        this.sysOrgDao.update("SysOrg.updateAuthAreaCodeById", param);
		    } catch (ManagerException ex) {
	            throw ex;
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new ManagerException("100200", new String[]{"组织机构授权异常！（BIZ）"});
	        } finally {

	        }
		 }

	@Override
	public String queryByAuthAreaCode(String authAreaCode) {
		return (String) this.sysOrgDao.getSqlMapClientTemplate().queryForObject("SysOrg.queryByAuthAreaCode", authAreaCode);
	}

	@Override
	public List<SysOrg> getOrgPath(String id) throws ManagerException {
		List<SysOrg> orgList = new ArrayList<SysOrg>();
		try {
			orgList = this.sysOrgDao.findBy("SysOrg.getOrgPath", "id", id);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
		return orgList;
	}

	@Override
	public void cancelAuth(String id) throws ManagerException {

		try {
			if(StringUtils.isNotBlank(id) ){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("id",id);
				sysOrgDao.update("SysOrg.cancelAuth", params);
			} else {
				throw new ManagerException("缺少必要的输入条件：id");
			}
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}

    @Override
    public List<SysOrg> getAll() {
        return this.sysOrgDao.getSqlMapClientTemplate().queryForList("SysOrg.getAllSysOrg");
    }
}
