package net.jqsoft.base.system.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import net.jqsoft.phimp.util.JsonPluginsUtil;

public class JsonTag implements TemplateDirectiveModel {

	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		Object o = params.get("entityObject");
		try {
			Class.forName(o.toString()).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jsonData = JsonPluginsUtil.beanToJson(o);
		if(body != null) {
			Writer out = env.getOut();
			out.write(jsonData);
			body.render(env.getOut());
		}
	}
}
