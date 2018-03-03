package com.jsmsframework.sysKeyword.dto;

import com.jsmsframework.common.util.DateUtil;
import com.jsmsframework.sysKeyword.entity.JsmsKeywordList;

/**
 * @author yeshiyuan
 * @create 2018/1/17
 */
public class JsmsSysKeywordListDTO extends JsmsKeywordList{
    private Integer rowNum;
    private String updateDateStr;
    private String operatorName;
    private String categoryName;
    private String groupName;

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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
