package com.jsmsframework.channel.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.jsmsframework.common.dto.R;
import com.jsmsframework.common.entity.KeywordMatchTuple;
import com.jsmsframework.common.util.JsonUtil;
import com.jsmsframework.common.util.KeyWrodSearchUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.channel.mapper.JsmsKeywordListMapper;
import com.jsmsframework.channel.entity.JsmsKeywordList;
import com.jsmsframework.channel.service.JsmsKeywordListService;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 关键字表
 * @author huangwenjie
 * @date 2017-12-06
 */
@Service
public class JsmsKeywordListServiceImpl implements JsmsKeywordListService{

    @Autowired
    private JsmsKeywordListMapper keywordListMapper;
    
    @Override
    public int insert(JsmsKeywordList model) {
        return this.keywordListMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsKeywordList> modelList) {
        return this.keywordListMapper.insertBatch(modelList);
    }

	@Override
    public int update(JsmsKeywordList model) {
		JsmsKeywordList old = this.keywordListMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.keywordListMapper.update(model); 
    }

    @Override
    public int updateSelective(JsmsKeywordList model) {
		JsmsKeywordList old = this.keywordListMapper.getById(model.getId());
		if(old != null)
        	return this.keywordListMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsKeywordList getById(Long id) {
        JsmsKeywordList model = this.keywordListMapper.getById(id);
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsKeywordList> list = this.keywordListMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.keywordListMapper.count(params);
    }


    @Override
    public String checkKeyword(String content) {
        String returnStr;
        R r = new R();
        List<String> list = new ArrayList<>();
        list = keywordListMapper.getAllKeywordList();
        Map<String, Object> WhiteKeywordList = KeyWrodSearchUtils.builWorldMap(list);
        Map<Integer, String> returnMap = new HashMap<>();
        Map<Integer, String> map = new HashMap<>();
        // 转移HTML防止注入，JSP页面输出是不能再用c:cout 因为下面需要显示标红的关键字
        String newContent = StringEscapeUtils.escapeHtml4(content);
        // 匹配当前内容中的审核关键字
        List<KeywordMatchTuple<String, Integer, Integer>> keywordMatchTuples = KeyWrodSearchUtils
                .searchKeywordPosByWorldMap(newContent, WhiteKeywordList);
        if(keywordMatchTuples.size()>0){
            for(int i =0;i<keywordMatchTuples.size();i++){
                KeywordMatchTuple keywordMatchTuple = keywordMatchTuples.get(i);
                map.put(500+i,String.valueOf(keywordMatchTuple.getStart())+","+String.valueOf(keywordMatchTuple.getEnd()));
            }
        }else{
            map.put(200,"无敏感内容");
        }
        returnStr = JsonUtil.toJson(map);
        return returnStr;
    }
}
