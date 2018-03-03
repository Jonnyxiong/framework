package com.jsmsframework.audit.dto;


/**
 *
 */
public class JsmsTableAndNum {

    // 分表表名后缀
    private String tableName;
    // 总条数
    private int total;

    private int limitIndex;

    public int getLimitIndex() {
        return limitIndex;
    }

    public void setLimitIndex(int limitIndex) {
        this.limitIndex = limitIndex;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
