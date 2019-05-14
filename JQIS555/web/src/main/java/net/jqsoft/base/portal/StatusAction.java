/**
 * Copyright (c) 2013-2015 jqsoft.net
 */
package net.jqsoft.base.portal;

import org.springframework.security.ui.savedrequest.SavedRequest;
import org.zcframework.core.view.support.BaseAction;

/**
 * @author: jetyou@foxmail.com
 * @date: 2015/5/21 0021-9:44
 * @desc
 */
public class StatusAction extends BaseAction {

    public String R500() {
        return "500";
    }

    public String R404() {
        return "404";
    }

    public String R301() {
        return "301";
    }

    public String R302() {
        return "302";
    }

    public String R200() {

        SavedRequest request = (SavedRequest) getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST_KEY");
        if (request != null && request.getParameterMap() != null) {
            String[] tabid = (String[]) request.getParameterMap().get("_tabid");
            if (tabid != null&&tabid.length>0)
                getHttpServletRequest().setAttribute("tabid", tabid[0]);
        }
        return "200";
    }

    public String R0() {
        return "0";
    }
}
