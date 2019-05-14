package net.jqsoft.common.util;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.log4j.Priority;
import org.zcframework.metrics.client.log4j.MDCDto;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 业务操作日志工具类
 *
 * @author jinliang
 * @create 2016-12-07 11:10:14
 **/
public class LogUtilsBiz {
    /** 日志级别 */
    public static String DEBUG = "Debug";
    public static String INFO = "Info";
    public static String WARN = "Warn";
    public static String ERROR = "Error";

    /** 操作类型 */
    public static String ADD = "增加";
    public static String MOD = "修改";
    public static String DEL = "删除";


    /**
     * 首选此方法
     *
     * @param module  模块
     * @param opt     操作类型
     * @param logger  日志类
     * @param message 结果描述
     * @param areaId  地区Id（可为空）
     * @param desc    描述性内容
     * @param year    年份（可为空）
     * @param searchMark 搜索标记（查看日志时）
     * @param request http请求
     */
    public static void log(String module, String opt, Logger logger, String message, String areaId, String desc,
                           String year, String searchMark, HttpServletRequest request) {
        Map mapTags = new HashMap();
        mapTags.put("Desc", desc);
        mapTags.put("Year", year);
        mapTags.put("searchMark", searchMark);
        log(module, opt, logger, message, areaId, mapTags, null, request);
    }

    /**
     * 首选此方法
     *
     * @param module  模块
     * @param opt     操作类型
     * @param logger  日志类
     * @param message 结果描述
     * @param areaId  地区Id（可为空）
     * @param desc    描述性内容
     * @param year    年份（可为空）
     * @param searchMark 搜索标记（查看日志时）
     * @param method  当前所在方法名
     * @param request http请求
     */
    public static void log(String module, String opt, Logger logger, String message, String areaId, String desc,
                           String year, String searchMark, String method, HttpServletRequest request) {
        Map mapTags = new HashMap();
        mapTags.put("Desc", desc);
        mapTags.put("Year", year);
        mapTags.put("searchMark", searchMark);
        mapTags.put("method", method);
        log(module, opt, logger, message, areaId, mapTags, null, request);
    }

    /**
     * @param module  模块
     * @param opt     操作类型
     * @param logger  日志类
     * @param message 结果描述
     * @param areaId  地区Id（可为空）
     * @param mapTags 自主添加列字段
     * @param undo    修改前数据/操作数据
     * @param request http请求
     */
    public static void log(String module, String opt, Logger logger, String message, String areaId,
                           Map<String, String> mapTags, Object undo, HttpServletRequest request) {
        log(null, module, opt, logger, message, areaId, mapTags, undo, null, request);
    }

    /**
     * @param module  模块
     * @param opt     操作类型
     * @param logger  日志类
     * @param message 结果描述
     * @param areaId  地区Id（可为空）
     * @param mapTags 自主添加列字段
     * @param undo    修改前数据
     * @param done    修改后数据
     * @param request http请求
     */
    public static void log(String module, String opt, Logger logger, String message, String areaId,
                           Map<String, String> mapTags, Object undo, Object done, HttpServletRequest request) {
        log(null, module, opt, logger, message, areaId, mapTags, undo, done, request);
    }

    /**
     * @param logLevel 日志级别
     * @param module   模块
     * @param opt      操作类型
     * @param logger   日志类
     * @param message  结果描述
     * @param areaId   地区Id（可为空）
     * @param mapTags  自主添加列字段
     * @param undo     修改前数据
     * @param done     修改后数据
     * @param request  http请求
     */
    public static void log(String logLevel, String module, String opt, Logger logger, String message, String areaId,
                           Map<String, String> mapTags, Object undo, Object done, HttpServletRequest request) {
        if (null == mapTags) {
            mapTags = new HashMap<String, String>();
        }
        mapTags.put("AreaId", areaId); // 地区Id
        mapTags.put("Opt", opt); // 增删改操作
        if (null == logLevel)
            logLevel = INFO;

        if (logLevel.equals(LogUtilsBiz.DEBUG) && logger.isDebugEnabled()) {
            mapTags.put("level", "DEBUG");
            log_Debug(module, logger, message, mapTags, undo, done, request);
        } else if (logLevel.equals(LogUtilsBiz.INFO) && logger.isInfoEnabled()) {
            mapTags.put("level", "INFO");
            log_Info(module, logger, message, mapTags, undo, done, request);
        } else if (logLevel.equals(LogUtilsBiz.WARN) && logger.isEnabledFor(Priority.WARN)) {
            mapTags.put("level", "WARN");
            log_Warn(module, logger, message, mapTags, undo, done, request);
        } else if (logLevel.equals(LogUtilsBiz.ERROR) && logger.isEnabledFor(Priority.ERROR)) {
            mapTags.put("level", "ERROR");
            log_Error(module, logger, message, mapTags, undo, done, request);
        }
        MDCS.clear();
    }

    /**
     * debug
     *
     * @param module  模块
     * @param logger  日志类
     * @param message 描述信息
     * @param mapTags 自定义添加的信息
     * @param undo    修改前的数据
     * @param done    修改后的数据
     * @param request http请求
     * @return void
     * @author jinliang
     * @create 2016-12-07 11:23:32
     **/
    public static void log_Debug(String module, Logger logger, String message, Map<String, String> mapTags, Object undo,
                                 Object done, HttpServletRequest request) {
        makeDto(module, mapTags, undo, done, request);
        logger.debug(message);
    }

    /**
     * info
     *
     * @param module  模块
     * @param logger  日志类
     * @param message 描述信息
     * @param mapTags 自定义添加的日志信息
     * @param undo    修改前的数据
     * @param done    修改后的数据
     * @param request http请求
     * @return void
     * @author jinliang
     * @create 2016-12-07 11:26:05
     **/
    public static void log_Info(String module, Logger logger, String message, Map<String, String> mapTags, Object undo,
                                Object done, HttpServletRequest request) {
        makeDto(module, mapTags, undo, done, request);
        logger.info(message);
    }

    /**
     * warn
     *
     * @param module  模块
     * @param logger  日志类
     * @param message 描述信息
     * @param mapTags 自定义添加的日志信息
     * @param undo    修改前的数据
     * @param done    修改后的数据
     * @param request http请求
     * @return void
     * @author jinliang
     * @create 2016-12-07 11:53:03
     **/
    public static void log_Warn(String module, Logger logger, String message, Map<String, String> mapTags, Object undo,
                                Object done, HttpServletRequest request) {
        makeDto(module, mapTags, undo, done, request);
        logger.warn(message);
    }

    /**
     * error
     *
     * @param module  模块
     * @param logger  日志类
     * @param message 描述信息
     * @param mapTags 自定义添加的日志信息
     * @param undo    修改前的数据
     * @param done    修改后的数据
     * @param request http请求
     * @return void
     * @author jinliang
     * @create 2016-12-07 11:54:38
     **/
    public static void log_Error(String module, Logger logger, String message, Map<String, String> mapTags, Object undo,
                                 Object done, HttpServletRequest request) {
        makeDto(module, mapTags, undo, done, request);
        logger.error(message);
    }

    /**
     * set MDCDto
     *
     * @param module  模块
     * @param mapTags 自定义添加的日志信息
     * @param undo    修改前的数据
     * @param done    修改后的数据
     * @param request http请求
     * @return void
     * @author jinliang
     * @create 2016-12-07 11:55:14
     **/
    public static void makeDto(String module, Map<String, String> mapTags, Object undo, Object done,
                               HttpServletRequest request) {
        MDCDto dto = MDCS.getMDCDto(module);
        dto.setOpUser(CurrentUserUtil.getCurrentUser().getUsername());
        String loginIp = CommonUtil.getRemoteHost(request);
        if ("0:0:0:0:0:0:0:1".equals(loginIp)) {
            loginIp = "127.0.0.1";
        }
        dto.setClientip(loginIp);
        if (null != undo) {
            dto.setUndo(undo);
        }
        if (null != done) {
            dto.setDone(done);
        }
        if (null != mapTags && !mapTags.isEmpty()) {
            Set<String> tagNames = mapTags.keySet();
            for (String tagName : tagNames) {
                if (null != mapTags.get(tagName)) {
                    dto.addTags(tagName, mapTags.get(tagName).toString());
                }
            }
        }
        MDC.put("mdcdto", dto);
    }
}