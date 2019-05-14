package net.jqsoft.common.filter;

import net.jqsoft.base.system.domain.LoginLog;
import net.jqsoft.base.system.service.ILoginLogService;
import net.jqsoft.common.util.CommonUtil;
import net.jqsoft.common.util.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.zcframework.core.logger.ILogger;
import org.zcframework.core.logger.LoggerFactory;
import org.zcframework.security.filter.AjaxAuthenticationProcessingFilter;
import org.zcframework.security.object.Loginer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: LogAjaxAuthenticationProcessingFilter
 * @Description: 原认证类的基础上增加LOG逻辑
 * @author wangjie
 * @date 2016年12月6日 下午2:00:21
 *
 */ 
public class LogAjaxAuthenticationProcessingFilter extends AjaxAuthenticationProcessingFilter {

    /** 初始化日志器 */
    private ILogger LOGGER = LoggerFactory.getUserLogger(net.jqsoft.common.filter.LogAjaxAuthenticationProcessingFilter.class);

    @Autowired
    private ILoginLogService iLoginLogService;
    
     /**
     * 成功授权后处理
     * @param request
     * @param response
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, authResult);

        // 记录登录时的日志到日志表中
        LoginLog loginLog = new LoginLog();
        Loginer loginer = CurrentUserUtil.getCurrentUser();
        if (null != loginer) {
            loginLog.setUserId(loginer.getId().toString());
            loginLog.setUserName(loginer.getUsername());
            loginLog.setRealName(loginer.getUsername());
            loginLog.setType("Login");
            loginLog.setLoginIp(CommonUtil.getRemoteHost(request));
            loginLog.setLoginTime(new Date());
        }
        this.iLoginLogService.saveOrUpdate(loginLog);
        LOGGER.info(loginer.getUsername() + "（IP:"+ CommonUtil.getRemoteHost(request) + "）用户于"
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "登录了系统！");
    }

    /**
     * 失败授权后处理
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        /**
         * 
         * 增加相应的业务逻辑
         * 
         * 
         * 
         * **/
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
