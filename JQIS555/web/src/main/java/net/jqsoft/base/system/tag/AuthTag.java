/**
 * Copyright (c) 2013-2015 jqsoft.net
 */
package net.jqsoft.base.system.tag;

import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.ConfigAttribute;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.GrantedAuthority;
import org.zcframework.security.SpringSecurityUtils;
import org.zcframework.security.cache.IResourceCacheManager;
import org.zcframework.security.object.Loginer;

/**
 * @author: jetyou@foxmail.com
 * @date: 2015/6/4 0004-13:30
 * @desc
 */
public class AuthTag implements TemplateDirectiveModel {
	
    @Autowired
    private IResourceCacheManager urlResourceCacheManager;


    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {

        Object parameter = params.get("name");
        if (parameter == null) return;
        String name = parameter.toString();
        if ("".equals(name)) return;
        ConfigAttributeDefinition config = (ConfigAttributeDefinition) this.urlResourceCacheManager.get(name);
        if(config==null) return;
        Loginer authentication = SpringSecurityUtils.getCurrentUser();
        int result = vote(authentication, config);
        if (result == 1)
            env.setVariable("hasPermission", ObjectWrapper.DEFAULT_WRAPPER.wrap(true));
        else
            env.setVariable("hasPermission", ObjectWrapper.DEFAULT_WRAPPER.wrap(false));
        if (body != null)
            body.render(env.getOut());
    }
    public int vote(Loginer authentication, ConfigAttributeDefinition config) {
        byte result = 0;
        Iterator iter = config.getConfigAttributes().iterator();
        GrantedAuthority[] authorities = this.extractAuthorities(authentication);

        while (true) {
            ConfigAttribute attribute;
            do {
                if (!iter.hasNext()) {
                    return result;
                }

                attribute = (ConfigAttribute) iter.next();
            } while (!this.supports(attribute));

            result = -1;

            for (int i = 0; i < authorities.length; ++i) {
                if (attribute.getAttribute().equals(authorities[i].getAuthority())) {
                    return 1;
                }
            }
        }
    }

    GrantedAuthority[] extractAuthorities(Loginer authentication) {
        return authentication.getAuthorities();
    }

    public boolean supports(ConfigAttribute attribute) {
        return attribute.getAttribute() != null;
    }

}
