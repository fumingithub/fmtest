package net.jqsoft.common.util;

import org.apache.log4j.MDC;
import org.zcframework.metrics.client.log4j.MDCDto;

import java.util.Map;

/**
 * MDC 工具 方便引用MDC
 *
 * @author jinliang
 * @create 2016-12-07 10:30:36
 **/
public class MDCS {

    /** web应用名称 */
    public static final String APP = "jqis-web";

    /** 以下是日志记录模块编码 */
    public static final String USER = "SYS_USER"; //------------用户管理
    public static final String ROLE = "SYS_ROLE"; //------------角色管理
    public static final String RESOURCES = "SYS_RESOURCES"; //--资源管理
    public static final String DICT = "SYS_DICT"; //------------字典管理
    public static final String NEWS = "SYS_NEWS"; //------------通知公告
    public static final String AREA = "AREA_DICT"; //-----------行政区划管理
    public static final String ORG = "SYS_ORG"; //--------------组织机构管理
    public static final String HCI = "HEALTH_CARD_INFO"; //-----居民健康卡信息采集
    public static final String HAB = "HEALTH_ARCHIVE_BASE"; //--采集农合数据
    public static final String NAREA = "NCMS_AREA_DICT"; //-----农合行政区划

    /**
     *
     * @param module 系统模块名称
     * @return org.zcframework.metrics.client.log4j.MDCDto
     * @author jinliang
     * @create 2016-12-07 11:07:26
     **/
    public static MDCDto getMDCDto(String module) {
        MDCDto mdcdto = new MDCDto();
        mdcdto.setApplication(APP);
        mdcdto.setModule(module);
        return mdcdto;
    }

    /**
     *
     * @param module 系统模块名称
     * @param clientip 客户端ip地址
     * @param opUser 操作用户
     * @return org.zcframework.metrics.client.log4j.MDCDto
     * @author jinliang
     * @create 2016-12-07 11:07:26
     **/
    public static MDCDto getMDCDto(String module, String clientip, String opUser) {
        MDCDto mdcdto = new MDCDto();
        mdcdto.setApplication(APP);
        mdcdto.setModule(module);
        mdcdto.setClientip(clientip);
        mdcdto.setOpUser(opUser);
        return mdcdto;
    }

    /**
     *
     * @param module 系统模块名称
     * @param clientip 客户端ip地址
     * @param opUser 操作用户
     * @param undo 修改前的数据
     * @param done 修改后的数据
     * @return org.zcframework.metrics.client.log4j.MDCDto
     * @author jinliang
     * @create 2016-12-07 11:07:26
     **/
    public static MDCDto getMDCDto(String module, String clientip, String opUser, Object undo, Object done) {
        MDCDto mdcdto = new MDCDto();
        mdcdto.setApplication(APP);
        mdcdto.setModule(module);
        mdcdto.setClientip(clientip);
        mdcdto.setOpUser(opUser);
        mdcdto.setUndo(undo);
        mdcdto.setDone(done);
        return mdcdto;
    }

    /**
     * 清空MDC
     * @return void
     * @author jinliang
     * @create 2016-12-07 11:09:14
     **/
    public static void clear() {
        Map map = MDC.getContext();
        if (map != null) {
            map.clear();
        }
    }

}