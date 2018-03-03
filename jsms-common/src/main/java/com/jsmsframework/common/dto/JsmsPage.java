package com.jsmsframework.common.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页类
 *
 * @author huangwenjie
 * @create 2017-01-02-21:05
 */
public class JsmsPage<T> implements Cloneable {

    public static String DEFAULT_PAGESIZE = "10";
    private int page; // 当前页码
    private int rows; // 每页行数
    private int totalRecord; // 总记录数
    private int totalPage; // 总页数
    private Map<String, Object> params; // 查询条件
    private List<T> data;
    private String orderByClause; //排序条件
    private String groupByClause; //分组条件
    private Integer[] pageRowArray = { 5, 10, 30, 50, 100 };// 每页显示行数下拉框
    /**
     * Just for {@code JsmsPageInterceptor}
     * {@code maxQueryLimit}, max query limit num, if the record size({@code totalRecord}) beyond maxQueryLimit,
     * it will return the JsmsPage with a empty {@code data}
     */
    private int maxQueryLimit = -1;// 最大查询记录

    public JsmsPage() {
        page = 1;
        rows = Integer.valueOf(DEFAULT_PAGESIZE);
        totalRecord = 0;
        totalPage = 0;
        params = new HashMap<>();
    }

    public JsmsPage(int page, int rows) {
        this.page = page;
        this.rows = rows;
        totalRecord = 0;
        totalPage = 0;
        params = new HashMap<>();
    }


    /**
     * 总件数变化时，更新总页数并计算显示样式
     */
    private void refreshPage() {
        // 总页数计算
        totalPage = totalRecord % rows == 0 ? totalRecord / rows : (totalRecord / rows + 1);
        // 防止超出最末页（浏览途中数据被删除的情况）
        if (page > totalPage && totalPage != 0) {
            page = totalPage;
        }
    }


    public String getGroupByClause() {
        return groupByClause;
    }

    public void setGroupByClause(String groupByClause) {
        this.groupByClause = groupByClause;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
        refreshPage();
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public Integer[] getPageRowArray() {
        return pageRowArray;
    }

    public void setPageRowArray(Integer[] pageRowArray) {
        this.pageRowArray = pageRowArray;
    }

    public int getMaxQueryLimit() {
        return maxQueryLimit;
    }

    public void setMaxQueryLimit(int maxQueryLimit) {
        this.maxQueryLimit = maxQueryLimit;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


}
