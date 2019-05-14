/**
 * Copyright (c) 2006-2015 jqsoft.net
 */
package net.jqsoft.repository.service;

import java.util.List;
import java.util.Map;

import org.zcframework.core.service.IBaseEntityService;

import net.jqsoft.repository.domain.RepositoryBase;
import net.jqsoft.repository.domain.RepositoryBaseHots;

/**
 * 知识库基本数据接口
 * 
 * @author zhanghui
 * @createDate 2017年2月8日 上午10:30:36
 */
public interface IRepositoryBaseService extends IBaseEntityService<RepositoryBase> {
	/**
	 * 根据ID查看知识库数据
	 * 
	 * @author zhanghui
	 * @createDate 2017年1月18日下午5:04:49
	 * @param id
	 *            知识库数据主键id
	 * @return 知识库数据集合
	 */
	public List<RepositoryBase> queryBaseById(String id);

	/**
	 * 根据id查询访问量
	 * 
	 * @author zhanghui
	 * @createDate 2017年1月19日上午11:50:08
	 * @param id
	 *            知识库数据主键id
	 * @return 查询访问量
	 */
	public int findPvById(String id);

	/**
	 * 更新访问量
	 * 
	 * @author zhanghui
	 * @createDate 2017年1月19日上午11:50:36
	 * @param map
	 */
	public void updatePvById(Map map);

	/**
	 * 插入浏览记录
	 * 
	 * @author zhanghui
	 * @createDate 2017年1月20日上午11:26:27
	 * @param reHots
	 *            知识库热点数据的浏览记录
	 */
	public void insertHotSpot(RepositoryBaseHots reHots);

	/**
	 * 根据用户id查询种类id
	 * 
	 * @author zhanghui
	 * @createDate 2017年1月20日上午11:27:52
	 * @param userId
	 *            用户ID
	 * @return 种类id
	 */
	public String findKindIdByUserId(String userId);

	/**
	 * 根据种类向用户推荐不同文章
	 * 
	 * @author zhanghui
	 * @createDate 2017年1月20日上午11:33:20
	 * @param type
	 *            知识库种类
	 * @return 推荐的知识库数据集合
	 */
	public List<RepositoryBase> queryHotsByType(String type);

}
