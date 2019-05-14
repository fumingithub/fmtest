package net.jqsoft.base.system.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import net.jqsoft.common.util.CommonUtil;

 /**
 * @author: yinzh
 * @date: 2015-6-30
 * @desc: action
 */
public class TokenTag implements TemplateDirectiveModel{
	
	 public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		 String uuid = CommonUtil.getUUID();
		 ServletActionContext.getRequest().getSession().setAttribute("token_value", uuid);
		 Writer out = env.getOut();
		 if(body !=null){
			 out.write("<input type=\"hidden\" value=\""+uuid+"\" name=\"token_value\"/>");
			 body.render(out);
		 }
	 }
}
