package net.jqsoft.base.system.domain;

import org.zcframework.core.domain.support.Entity;

import java.util.Date;

/**
 * @author: fumin
 * @date: 2018/11/5
 * @description:
 */
public class Travel extends Entity<String>{
    private String name; // 景点名称
    private String code; // 景点编号
    private String level; //景点等级
    private String provinceId; // 所在省会编码
    private String provinceName; // 所在省会名称
    private String describe; // 景点描述
    private Integer status; // 0免费,1收费
    private Date localTime;//添加时间

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getLocalTime() {
        return localTime;
    }

    public void setLocalTime(Date localTime) {
        this.localTime = localTime;
    }
}
