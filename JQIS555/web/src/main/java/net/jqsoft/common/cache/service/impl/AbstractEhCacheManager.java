/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.common.cache.service.impl;

import net.jqsoft.common.cache.service.IEhCacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * EhCache缓存抽象类
 *
 * @author jinliang
 * @create 2016-12-21 下午 4:24
 **/
public abstract class AbstractEhCacheManager implements IEhCacheManager {

    /** EhCache缓存对象 */
    private Ehcache cache;

    @Override
    public List<String> getKeys() {
        return cache.getKeys();
    }

    @Override
    public List getAll() {
        List<String> keys = getKeys();
        List values = new ArrayList();
        for (String key : keys) {
            Object value = this.get(key);
            values.add(value);
        }
        return values;
    }

    @Override
    public void put(String key, Object value) {
        Element element = new Element(key, value);
        cache.put(element);
    }

    @Override
    public void put(String key, List<Object> value) {
        Element element = new Element(key, value);
        cache.put(element);
    }

    @Override
    public void removeAll() {
        cache.removeAll();
    }

    @Override
    public void remove(String key) {
        cache.remove(key);
    }

    @Override
    public Object get(String key) {
        Element element = cache.get(key);
        if (element == null)
            return null;
        return element.getValue();
    }

    @Override
    public List<Object> getList(String key) {
        Element element = cache.get(key);
        if (element == null)
            return null;
        return (List<Object>) element.getObjectValue();
    }

    @Override
    public void destroy() {
        cache.dispose();
    }

    @Override
    public void refresh(String key, Object value) {
        remove(key);
        put(key, value);
    }

    @Override
    public void refreshList(String key, List<Object> value) {
        remove(key);
        put(key, value);
    }

    @Override
    public void refreshAll() {
        removeAll();
        init();
    }

    public void setCache(Ehcache cache) {
        this.cache = cache;
    }

    public Ehcache getCache() {
        return cache;
    }

}
