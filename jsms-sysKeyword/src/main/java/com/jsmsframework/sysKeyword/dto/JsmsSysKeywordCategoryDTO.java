package com.jsmsframework.sysKeyword.dto;

import com.jsmsframework.common.util.DateUtil;
import com.jsmsframework.sysKeyword.entity.JsmsSysKeywordCategory;

/**
 * @author yeshiyuan
 * @create 2018/1/15
 */
public class JsmsSysKeywordCategoryDTO extends JsmsSysKeywordCategory{

    //序号
    private Integer rownum;
    //操作人名字
    private String operatorName;
    //更新时间
    private String updateDateStr;

    public Integer getRownum() {
        return rownum;
    }

    public void setRownum(Integer rownum) {
        this.rownum = rownum;
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
}
