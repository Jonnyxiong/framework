package com.jsmsframework.channel.service;

import java.util.Map;
import java.util.List;
import java.util.Set;

import com.jsmsframework.channel.entity.JsmsChannel;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;
import org.apache.ibatis.annotations.Param;

/**
 * @description 短信通道信息表
 * @author huangwenjie
 * @date 2017-10-12
 */
public interface JsmsChannelService {

    int insert(JsmsChannel model);
    
    int insertBatch(List<JsmsChannel> modelList);

    int update(JsmsChannel model);
    
    int updateSelective(JsmsChannel model);

    JsmsChannel getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    List<JsmsChannel> queryAll();

    List<JsmsChannel> queryAllByOperatorstype(Integer operatorstype);

    /**
     * 根据通道号查询通道
     * @param cid
     * @return
     */
    JsmsChannel getByCid(Integer cid);
    //根据cid获取通道信息
    List<JsmsChannel> getByCids(@Param("cids") Set<Integer> cids);

    /**
     *
     * @return
     */
    boolean checkChannel4One(Map<String,Object> params);
}
