/**
 * Copyright (c) 2013-2015 jqsoft.net
 */
package net.jqsoft.base.system.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zcframework.core.dao.IEntityDao;
import org.zcframework.core.service.BaseEntityService;
import org.zcframework.core.utils.date.UtilDateTime;
import org.zcframework.security.SpringSecurityUtils;
import org.zcframework.security.object.Loginer;

import net.jqsoft.base.system.dao.NoticeDao;
import net.jqsoft.base.system.domain.Notice;
import net.jqsoft.base.system.service.INoticeService;
import net.jqsoft.common.exception.ManagerException;

/**
 * 公告管理service层
 * 
 * @author guoxx
 * @createTime 2017/1/17
 */
@Service
public class NoticeService extends BaseEntityService<Notice> implements INoticeService {

	@Autowired
	private NoticeDao noticeDao;

	@Override
	protected IEntityDao<Notice> getDao() {
		return noticeDao;
	}

	@Override
	public int saveOrUpdate(Notice entity) throws ManagerException {

		// 返回值不同判断新增操作还是删除操作
		int result = 0;
		try {

			// 获取当前登陆者信息
			Loginer loginer = SpringSecurityUtils.getCurrentUser();

			// status为1时发布状态
			if (entity != null && entity.getStatus().equals("1")) {
				entity.setReleasePerson(loginer.getUsername());
				entity.setReleaseTime(UtilDateTime.nowDate());
			}

			// 存在ID是编辑，否则是新增
			if (StringUtils.isNotEmpty(entity.getId())) {
				entity.setIsTop("0");
				entity.setUpdator(loginer.getId().toString());
				entity.setUpdatorName(loginer.getUsername());
				entity.setUpdateTime(UtilDateTime.nowDate());
				noticeDao.update(entity);

				result = 2;
			} else {
				entity.setIsTop("0");
				entity.setCreator(loginer.getId().toString());
				entity.setCreatorName(loginer.getUsername());
				entity.setCreateTime(UtilDateTime.nowDate());

				// 自动生成ID
				entity.setId(UUID.randomUUID().toString());
				noticeDao.create(entity);

				result = 1;
			}
			return result;

		} catch (ManagerException ex) {
			throw ex;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ManagerException("100200", new String[] { "公告信息保存异常！（BIZ）" });
		} finally {

		}
	}

	@Override
	public List<Notice> getNoticeListTop5() {
		return this.noticeDao.find("Notice.getNoticeListTop5", (Object) null);
	}

	@Override
	public int releaseNotice(Notice entity) throws ManagerException {

		// 标识发布情况
		int res = 0;
		try {
			Loginer loginer = SpringSecurityUtils.getCurrentUser();
			entity.setStatus("1");
			entity.setReleasePerson(entity.getCreatorName());
			entity.setReleaseTime(UtilDateTime.nowDate());
			entity.setUpdator(loginer.getId().toString());
			entity.setUpdatorName(loginer.getUsername());
			entity.setUpdateTime(UtilDateTime.nowDate());
			if (null != entity) {
				noticeDao.update(entity);
				res = 2;
			}
		} catch (Exception e) {
			throw new ManagerException(e);
		}
		return res;
	}

	@Override
	public int cancelRelease(Notice entity) throws ManagerException {
		// 标识撤销发布情况
		int res = 0;
		try {
			Loginer loginer = SpringSecurityUtils.getCurrentUser();
			entity.setUpdator(loginer.getId().toString());
			entity.setUpdatorName(loginer.getUsername());
			entity.setUpdateTime(UtilDateTime.nowDate());
			entity.setStatus("0");
			entity.setIsTop("0");
			entity.setReleaseTime(null);
			entity.setReleasePerson(null);
			if (null != entity.getId()) {
				noticeDao.removeById(entity.getId());
				noticeDao.create(entity);
				res = 2;
			}
		} catch (Exception e) {
			throw new ManagerException(e);
		}
		return res;
	}

}
