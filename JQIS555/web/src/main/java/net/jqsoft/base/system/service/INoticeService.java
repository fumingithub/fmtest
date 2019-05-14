/**
 * Copyright (c) 2013-2015 jqsoft.net
 */
package net.jqsoft.base.system.service;

import java.util.List;

import org.zcframework.core.service.IBaseEntityService;

import net.jqsoft.base.system.domain.Notice;
import net.jqsoft.common.exception.ManagerException;

/**
 * 公告管理Service接口
 * 
 * @author guoxx
 * @createTime 2017年1月17日上午9:28:14
 */
public interface INoticeService extends IBaseEntityService<Notice> {

	/**
	 * 保存编辑或新增结果
	 * 
	 * @author guoxx
	 * @createTime 2017年1月18日上午10:41:24
	 * @param entity新增或编辑的一条公告数据
	 * @return int 1新增 2编辑
	 */
	public int saveOrUpdate(Notice entity) throws ManagerException;;

	/**
	 * 获取最新的5条公告
	 * 
	 * @author guoxx
	 * @createDate 2017年1月18日上午11:28:14
	 * @return list
	 */
	public List<Notice> getNoticeListTop5();

	/**
	 * 发布公告
	 * 
	 * @author guoxx
	 * @createDate 2017年1月18日下午4:21:10
	 * @param entity一条公告数据
	 */
	public int releaseNotice(Notice entity) throws ManagerException;;

	/**
	 * 撤销已发布公告
	 * 
	 * @author guoxx
	 * @createDate 2017年1月18日下午4:42:58
	 * @param entity一条公告数据
	 */
	public int cancelRelease(Notice entity) throws ManagerException;;

}
