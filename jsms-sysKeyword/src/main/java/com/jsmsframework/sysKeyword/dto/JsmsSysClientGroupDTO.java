package com.jsmsframework.sysKeyword.dto;

import com.jsmsframework.common.util.DateUtil;
import com.jsmsframework.sysKeyword.entity.JsmsSysClientGroup;

/**
 * @author yeshiyuan
 * @create 2018/1/18
 */
public class JsmsSysClientGroupDTO extends JsmsSysClientGroup{
    private Integer rowNum;
    private String updateDateStr;
    private String operatorName;
    private String groupName;
    private String clientName;
    private String categoryName;


    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public String getUpdateDateStr() {
        if (getUpdateDate()!=null){
            updateDateStr = DateUtil.dateToStr(getUpdateDate(),"yyyy-MM-dd HH:mm:ss");
        }
        return updateDateStr;
    }

    public void setUpdateDateStr(String updateDateStr) {
        this.updateDateStr = updateDateStr;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
