package com.jsmsframework.channel.service;

import com.jsmsframework.channel.entity.JsmsChannel;
import com.jsmsframework.channel.mapper.JsmsChannelMapper;
import com.jsmsframework.common.dto.JsmsPage;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @description 短信通道信息表
 * @author huangwenjie
 * @date 2017-10-12
 */
@Service
public class JsmsChannelServiceImpl implements JsmsChannelService{

    @Autowired
    private JsmsChannelMapper channelMapper;
    
    @Override
	@Transactional
    public int insert(JsmsChannel model) {
        return this.channelMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsChannel> modelList) {
        return this.channelMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsChannel model) {
		JsmsChannel old = this.channelMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.channelMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsChannel model) {
		JsmsChannel old = this.channelMapper.getById(model.getId());
		if(old != null)
        	return this.channelMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsChannel getById(Integer id) {
        JsmsChannel model = this.channelMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsChannel> list = this.channelMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.channelMapper.count(params);
    }

    @Override
    public List<JsmsChannel> queryAll() {
        return this.channelMapper.queryAll();
    }

    @Override
    public List<JsmsChannel> queryAllByOperatorstype(Integer operatorstype) {
        return this.channelMapper.queryAllByOperatorstype(operatorstype);
    }

    /**
     * 根据通道号查询通道
     *
     * @param cid
     * @return
     */
    @Override
    public JsmsChannel getByCid(Integer cid) {
        return this.channelMapper.getByCid(cid);
    }

    @Override
    public List<JsmsChannel> getByCids(Set<Integer> cids) {
        List<JsmsChannel> channels = channelMapper.getByCids(cids);
        return channels;
    }

    /**
     * 按字段 clientid + password + state + httpmode 判断唯一
     *
     * @return
     */
    @Override
    public boolean checkChannel4One(Map<String,Object> params) {


        return this.count(params)>0?false:true;
    }
}
