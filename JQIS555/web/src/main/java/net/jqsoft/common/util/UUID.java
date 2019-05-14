/**
 * Copyright (c) 2006-2016 jqsoft.net
 */
package net.jqsoft.common.util;

import org.zcframework.core.utils.date.UtilDateTime;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sunwm@jqsoft.net
 * @datetime 2016-07-18  11:28
 * @desc
 * @see
 */
public class UUID {

    private static final String length = "%06d";//Format格式，预留的位数6
    private static final String machine = "00";
    private static Map<String, Integer> uuid = new ConcurrentHashMap<String, Integer>();
    /**
     * 获取XX位UUID值
     * @param module 模块名称
     * @return
     */
    public  static synchronized String generate(String module) {
        Integer id = uuid.get(module);
        if (id == null)
            id = 0;
        else
            id++;
        uuid.put(module, id);
        //10位到秒的int+机器码+6位的format值
        return UtilDateTime.getCurrDateInt() + machine + String.format(length, id);
    }
    public static void main(String args[]) {
        System.out.println(generate("MEDICAL"));
        System.out.println(generate("MEDICAL"));
        System.out.println(generate("MEDICAL"));
    }

}
