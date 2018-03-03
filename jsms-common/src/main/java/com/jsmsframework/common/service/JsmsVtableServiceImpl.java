package com.jsmsframework.common.service;

import com.jsmsframework.common.mapper.JsmsVtableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Don on 2017/12/25.
 */
@Service
public class JsmsVtableServiceImpl implements  JsmsVtableService{

    @Autowired
    private JsmsVtableMapper jsmsVtableMapper;
    /**
     * 根据多个表名，返回存在的表名集合
     *
     * @param tableList
     * @return
     */
    @Override
    public List<Map<String,Object>> getExistTables(List<String> tableList) {
        return this.jsmsVtableMapper.getExistTables(tableList);
    }

    /**
     * 根据表名判断是否存在表
     *
     * @param tableName
     * @return
     */
    @Override
    public boolean getExistTable(String tableName) {
        return this.jsmsVtableMapper.getExistTable(tableName)>0;
    }
}
