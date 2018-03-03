package com.jsmsframework.audit.dto;

import com.jsmsframework.audit.entity.JsmsAuditClientGroup;

import java.util.Date;

/**
 * Created by xiongfenglin on 2017/10/31.
 *
 * @author: xiongfenglin
 */
public class JsmsAuditClientGroupDTO extends JsmsAuditClientGroup{
    // 操作者
    private String userName;
    //关键字类别
    private String categoryName;
    // 组别名称
    private String kgroupName;
    // 用户名称
    private String name;
    private String updateTimeStr;

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getKgroupName() {
        return kgroupName;
    }

    public void setKgroupName(String kgroupName) {
        this.kgroupName = kgroupName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
