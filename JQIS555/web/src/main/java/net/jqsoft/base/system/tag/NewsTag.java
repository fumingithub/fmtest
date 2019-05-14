/**
 * Copyright (c) 2013-2015 jqsoft.net
 */
package net.jqsoft.base.system.tag;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.jqsoft.base.system.domain.News;
import net.jqsoft.base.system.service.INewsService;

import org.springframework.beans.factory.annotation.Autowired;

import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

 /**
 * @author: yinzh
 * @date: 2015-6-25
 * @desc: action
 */
public class NewsTag implements TemplateDirectiveModel {
	
	@Autowired
    private INewsService newsService;
	
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		List<News> newsList = this.newsService.getAllFirst5News();
		env.setVariable("newsList",ObjectWrapper.DEFAULT_WRAPPER.wrap(newsList));
		if(body !=null){
			body.render(env.getOut());
		}
    }
}
