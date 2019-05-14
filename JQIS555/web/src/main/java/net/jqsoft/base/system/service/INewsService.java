/**
 * Copyright (c) 2013-2015 jqsoft.net
 */
package net.jqsoft.base.system.service;

import java.util.List;

import net.jqsoft.base.system.domain.News;

import org.zcframework.core.service.IBaseEntityService;

/**
 * @author: zuoc
 * @date: 2015/5/25
 * @desc: 统一权限配置管理 - 新闻信息管理Service
 */
public interface INewsService extends IBaseEntityService<News>{
  
  /**
   * 删除
   * @param id  VIS_NEWS表主键
   */
  public void deleteVisNews(String id);
  
  /**
   * 发布新闻
   * @param id  VIS_NEWS表主键
   */
  public void releaseNews(String id);

  /**
	 * 查询最新的5篇文章
	 * @author yinzh
	 * @param
	*/
  public List<News> getAllFirst5News();

}
