package com.jsmsframework.sysKeyword.dto;

import com.jsmsframework.common.util.DateUtil;
import com.jsmsframework.sysKeyword.entity.JsmsKeywordList;
import com.jsmsframework.sysKeyword.entity.JsmsSysKeywordGroup;

/**
 * @author yeshiyuan
 * @create 2018/1/17
 */
public class JsmsSysKeywordGroupDTO extends JsmsSysKeywordGroup{
    private Integer rowNum;
    private String operatorName;
    private String updateDateStr;
    private String categoryNames;

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
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

    public String getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(String categoryNames) {
        this.categoryNames = categoryNames;
    }
}
