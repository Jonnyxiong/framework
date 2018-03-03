package com.jsmsframework.audit.dto;

import com.jsmsframework.audit.entity.JsmsAuditKeywordGroup;

import java.util.Date;

/**
 * Created by xiongfenglin on 2017/10/31.
 *
 * @author: xiongfenglin
 */
public class JsmsAuditKeywordGroupDTO extends JsmsAuditKeywordGroup{
    // 操作者
    private String userName;
    //关键字类别
    private String categoryName;
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
}
