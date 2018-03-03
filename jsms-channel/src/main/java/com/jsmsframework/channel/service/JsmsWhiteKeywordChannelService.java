package com.jsmsframework.channel.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.channel.entity.JsmsWhiteKeywordChannel;

import com.jsmsframework.common.dto.R;
import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.entity.KeywordMatchTuple;

/**
 * @description 白关键字强制路由通道表
 * @author huangwenjie
 * @date 2017-09-20
 */
public interface JsmsWhiteKeywordChannelService {

    int insert(JsmsWhiteKeywordChannel model);
    
    int insertBatch(List<JsmsWhiteKeywordChannel> modelList);

    int update(JsmsWhiteKeywordChannel model);
    
    int updateSelective(JsmsWhiteKeywordChannel model);

    JsmsWhiteKeywordChannel getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    R delWhitekeyChannel(Integer id);

    boolean isExistWhitekeyChannel(JsmsWhiteKeywordChannel model);
}
