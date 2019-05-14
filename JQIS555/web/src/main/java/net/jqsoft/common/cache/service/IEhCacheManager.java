/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.common.cache.service;

import java.util.List;

/**
 * EhCache缓存管理器接口
 * @author jinliang
 * @create 2016-12-22 09:43:30
 **/
public interface IEhCacheManager {

    /**
     * 初始化缓存
     * @return void
     * @author jinliang
     * @create 2016-12-22 09:44:51
     **/
    public void init();

    /**
     * 销毁缓存
     * @return void
     * @author jinliang
     * @create 2016-12-22 09:45:01
     **/
    public void destroy();

    /**
     * 获取所有缓存的key值集合（List<String>）
     * @return List<String>
     * @author jinliang
     * @create 2016-12-22 09:45:11
     **/
    public List<String> getKeys();

    /**
     * 移除所有缓存中的元素
     * @return void
     * @author jinliang
     * @create 2016-12-22 09:45:21
     **/
    public void removeAll();

    /**
     * 移除缓存中指定key的元素
     * @param key 缓存的键
     * @return void
     * @author jinliang
     * @create 2016-12-22 09:45:27
     **/
    public void remove(String key);

    /**
     * 获取指定key的缓存元素（Object）
     * @param key 缓存的键
     * @return Object
     * @author jinliang
     * @create 2016-12-22 09:45:36
     **/
    public Object get(String key);

    /**
     * 获取指定key的缓存元素集合（List<Object>）
     * @param key 缓存的键
     * @return List<Object>
     * @author jinliang
     * @create 2016-12-22 09:45:48
     **/
    public List<Object> getList(String key);

    /**
     * 获取所有缓存中的元素（键值对集合）
     * @return List
     * @author jinliang
     * @create 2016-12-22 09:46:02
     **/
    public List getAll();

    /**
     * 添加键值对缓存元素（key:String-value:Object）
     * @param key   缓存的key
     * @param value 缓存的value（Object）
     * @return void
     * @author jinliang
     * @create 2016-12-22 09:46:15
     **/
    public void put(String key, Object value);

    /**
     * 添加键值对缓存元素（key:String-value:List<Object>）
     * @param key   缓存的key
     * @param value 缓存的value（List<Object>）
     * @return void
     * @author jinliang
     * @create 2016-12-22 09:46:30
     **/
    public void put(String key, List<Object> value);

    /**
     * 刷新缓存（Object）
     * @param key   缓存的键
     * @param value 缓存的value（Object）
     * @return void
     * @author jinliang
     * @create 2016-12-22 10:05:19
     **/
    public void refresh(String key, Object value);

    /**
     * 刷新缓key (List<Object>)
     * @param key   缓存的键
     * @param value 缓存的value（List<Object>）
     * @return void
     * @author jinliang
     * @create 2016-12-22 10:05:19
     **/
    public void refreshList(String key, List<Object> value);

    /**
     * 刷新所有的缓存
     * @return void
     * @author jinliang
     * @create 2016-12-22 10:05:23
     **/
    public void refreshAll();
}
