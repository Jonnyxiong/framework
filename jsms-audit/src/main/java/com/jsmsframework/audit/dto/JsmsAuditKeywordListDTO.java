package com.jsmsframework.audit.dto;

import com.jsmsframework.audit.entity.JsmsAuditKeywordList;
import org.apache.commons.lang3.time.DateFormatUtils;

public class JsmsAuditKeywordListDTO extends JsmsAuditKeywordList {

    private Integer rowNum; // 更新时间
    private String operatorStr; // 操作者
    private String updateDateStr; // 更新时间
    // 类别ID，自增长
    private Integer categoryId;
    // 类别名称
    private String categoryName;
    //
    private String kgroupId;
    // 组别名称
    private String kgroupName;

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public String getOperatorStr() {
        return operatorStr;
    }

    public void setOperatorStr(String operatorStr) {
        this.operatorStr = operatorStr;
    }

    public String getUpdateDateStr() {
        if (getUpdateDate() != null){
            updateDateStr = DateFormatUtils.format(getUpdateDate(), "yyyy-MM-dd HH:mm:ss");
        }
        return updateDateStr;
    }

    public void setUpdateDateStr(String updateDateStr) {
        this.updateDateStr = updateDateStr;
    }

    @Override
    public Integer getCategoryId() {
        return categoryId;
    }

    @Override
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getKgroupId() {
        return kgroupId;
    }

    public void setKgroupId(String kgroupId) {
        this.kgroupId = kgroupId;
    }

    public String getKgroupName() {
        return kgroupName;
    }

    public void setKgroupName(String kgroupName) {
        this.kgroupName = kgroupName;
    }
}
