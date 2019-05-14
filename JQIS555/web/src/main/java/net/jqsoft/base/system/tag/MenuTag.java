/**
 * Copyright (c) 2013-2015 jqsoft.net
 */
package net.jqsoft.base.system.tag;

import com.alibaba.fastjson.JSONArray;

import freemarker.core.Environment;
import freemarker.template.*;
import net.jqsoft.base.system.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.zcframework.security.SpringSecurityUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @author: jetyou@foxmail.com
 * @date: 2015/6/19 0019-12:47
 * @desc 菜单FREEMARKER TAG，返回菜单列表
 */
public class MenuTag implements TemplateDirectiveModel {
    @Autowired
    private IResourceService resourceService;

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        JSONArray menu = resourceService.getMenu(SpringSecurityUtils.getCurrentUserName());
        env.setVariable("menu", ObjectWrapper.DEFAULT_WRAPPER.wrap(menu));
        if (body != null) {
            body.render(env.getOut());
        }
    }
}
