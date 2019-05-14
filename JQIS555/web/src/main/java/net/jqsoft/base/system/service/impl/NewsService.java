/**
 * Copyright (c) 2013-2015 jqsoft.net
 */
package net.jqsoft.base.system.service.impl;

import net.jqsoft.base.system.dao.NewsDao;
import net.jqsoft.base.system.domain.News;
import net.jqsoft.base.system.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zcframework.core.dao.IEntityDao;
import org.zcframework.core.service.BaseEntityService;
import org.zcframework.core.utils.date.UtilDateTime;
import org.zcframework.security.SpringSecurityUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zuoc
 * @date: 2015/5/25
 * @desc: 统一权限配置管理 - 新闻信息管理Service
 */
@Service
public class NewsService extends BaseEntityService<News> implements INewsService {
	
    @Autowired
    private NewsDao newsDao;
    
	@Override
	protected IEntityDao<News> getDao() {
		return newsDao;
	}

	public void deleteVisNews(String id) {
		Map<String,Object> paramap = new HashMap<String, Object>();
		paramap.put("updator", SpringSecurityUtils.getCurrentUserName());
		paramap.put("updateTime", UtilDateTime.getCurrDateInt());
		paramap.put("id", id);
		newsDao.update(getEntityName() + ".delete", paramap);
	}
	
	/**
	 * 发布新闻
	 * @param id  VIS_NEWS表主键
	 */
	public void releaseNews(String id) {
		Map<String,Object> paramap = new HashMap<String, Object>();
		paramap.put("releasetime", UtilDateTime.getCurrDateInt());
		paramap.put("updator", SpringSecurityUtils.getCurrentUserName());
		paramap.put("updateTime", UtilDateTime.getCurrDateInt());
		paramap.put("id", id);
		newsDao.update(getEntityName() + ".releaseNews", paramap);
	}
	  
	public List<News> getAllFirst5News() {
		return this.newsDao.find("News.getAllFirst5News",(Object)null);
	}

}
