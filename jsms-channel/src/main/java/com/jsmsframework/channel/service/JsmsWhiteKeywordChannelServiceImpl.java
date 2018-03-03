package com.jsmsframework.channel.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.jsmsframework.common.dto.R;
import com.jsmsframework.common.entity.KeywordMatchTuple;
import com.jsmsframework.common.util.JsonUtil;
import com.jsmsframework.common.util.KeyWrodSearchUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.channel.mapper.JsmsWhiteKeywordChannelMapper;
import com.jsmsframework.channel.entity.JsmsWhiteKeywordChannel;
import org.apache.commons.lang3.StringEscapeUtils;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 白关键字强制路由通道表
 * @author huangwenjie
 * @date 2017-09-20
 */
@Service
public class JsmsWhiteKeywordChannelServiceImpl implements JsmsWhiteKeywordChannelService{

    private  static final Logger LOGGER= LoggerFactory.getLogger(JsmsWhiteKeywordChannelServiceImpl.class);
    @Autowired
    private JsmsWhiteKeywordChannelMapper whiteKeywordChannelMapper;
    
    @Override
	@Transactional
    public int insert(JsmsWhiteKeywordChannel model) {
        return this.whiteKeywordChannelMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsWhiteKeywordChannel> modelList) {
        return this.whiteKeywordChannelMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsWhiteKeywordChannel model) {
		JsmsWhiteKeywordChannel old = this.whiteKeywordChannelMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.whiteKeywordChannelMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsWhiteKeywordChannel model) {
		JsmsWhiteKeywordChannel old = this.whiteKeywordChannelMapper.getById(model.getId());
		if(old != null)
        	return this.whiteKeywordChannelMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsWhiteKeywordChannel getById(Integer id) {
        JsmsWhiteKeywordChannel model = this.whiteKeywordChannelMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsWhiteKeywordChannel> list = this.whiteKeywordChannelMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.whiteKeywordChannelMapper.count(params);
    }

    @Override
    public R delWhitekeyChannel(Integer id) {
        if(id==null){
            return  R.error("白关键字强制路由通道ID不存在");
        }
        JsmsWhiteKeywordChannel white=this.whiteKeywordChannelMapper.getById(id);
        if(white.getId()==null){
            LOGGER.debug("删除白关键字强制路由通道 ID={},不存在", id);
            return R.error("白关键字强制路由通道不存在");
        }

        int del=this.whiteKeywordChannelMapper.delete(id);
        LOGGER.debug( "删除白关键字强制路由通道 ID={}结束,{}", JsonUtil.toJson(id),del > 0 ? "成功删除": "失败删除");
        return del > 0 ? R.ok("删除白关键字强制路由通道成功") : R.error("删除白关键字强制路由通道失败");

    }

    @Override
    public boolean isExistWhitekeyChannel(JsmsWhiteKeywordChannel model) {
        return this.whiteKeywordChannelMapper.isExist(model)>0;
    }

}
