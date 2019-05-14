/**
 * Copyright (c) 2013-2015 jqsoft.net
 */
package net.jqsoft.base.system.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import net.jqsoft.base.system.domain.SysOrg;
import net.jqsoft.base.system.service.ISysOrgService;

/**
 * 组织机构标签
 * @author			xiaohf
 * @createDate		2016年11月30日 上午11:14:13
 */
public class PCOrgTag implements TemplateDirectiveModel {
	
	@Autowired
    private ISysOrgService sysOrgService;
	
	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		Object id = params.get("id");
		//定义组织机构路径
		String orgPath = "";
		
		if (id != null && StringUtils.isNotBlank(id.toString())) {
			List<SysOrg> orgList = sysOrgService.getOrgPath(id.toString());
			Collections.reverse(orgList);
			for (int i = 0; i < orgList.size(); i++) {
				SysOrg org = orgList.get(i);
				if(i == 0){
					orgPath = org.getName();
				}else{
					orgPath = orgPath + " " + org.getName();
				}
			}

			Writer out = env.getOut();
			out.write(orgPath.trim());
			if (body != null) {
				body.render(out);
			}
		}
	}
	
}
