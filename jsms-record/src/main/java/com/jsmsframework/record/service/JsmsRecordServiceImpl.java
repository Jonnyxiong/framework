package com.jsmsframework.record.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.jsmsframework.record.record.entity.JsmsRecord;
import com.jsmsframework.record.record.mapper.JsmsRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 短信记录流水表
 * @author Don
 * @date 2018-01-11
 */
@Service
public class JsmsRecordServiceImpl implements JsmsRecordService {

    @Autowired
    private JsmsRecordMapper record0Mapper;
    
    @Override
    public int insert(JsmsRecord model) {
        return this.record0Mapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsRecord> modelList) {
        return this.record0Mapper.insertBatch(modelList);
    }

	/*@Override
    public int update(JsmsRecord model) {
		JsmsRecord old = this.record0Mapper.getBySmsuuid(model.getSmsuuid());
		if(old == null){
			return 0;
		}
		return this.record0Mapper.update(model); 
    }*/

    /*@Override
    public int updateSelective(JsmsRecord model) {
		JsmsRecord old = this.record0Mapper.getBySmsuuid(model.getSmsuuid());
		if(old != null)
        	return this.record0Mapper.updateSelective(model);
		return 0;
    }*/

    @Override
    public JsmsRecord getBySmsuuid(String smsuuid,String tableName) {
        JsmsRecord model = this.record0Mapper.getBySmsuuid(smsuuid,tableName);
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsRecord> list = this.record0Mapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public List<JsmsRecord> findList(Map params) {
        List<JsmsRecord> list = this.record0Mapper.findList(params);
        return list;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.record0Mapper.count(params);
    }

    @Override
    public JsmsPage queryFailRecord(JsmsPage page) {
        /*----------------兼容record表字段问题，所以要先查表里有哪些字段，不存在的则默认补上----------------*/
        //获取当前数据库名称、表名
        String recordDatabaseName = this.record0Mapper.getCurrentDatabaseName();
        String tableName = ((List<String>)page.getParams().get("tableNames")).get(0);
        //查找表内列名
        List<Map<String,String>> columns = this.record0Mapper.getRecordTableSchema(tableName,recordDatabaseName);
        Map<String,Integer> columnMap = new HashMap<>();
        for (Map<String,String> map:columns) {
            columnMap.put(map.get("COLUMN_NAME"),1);
        }
        if (columnMap.containsKey("c_failed_resend_times")){
            page.getParams().putAll(columnMap);
            List<JsmsRecord> list = this.record0Mapper.queryFailRecord(page);
            page.setData(list);
        } else {
            page.setData(new ArrayList());
        }
        return page;
    }
}
