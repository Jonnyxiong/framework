package com.jsmsframework.channel.service;

import com.jsmsframework.channel.entity.JsmsComplaintList;
import com.jsmsframework.channel.entity.JsmsComplaintListExt;
import com.jsmsframework.common.dto.JsmsPage;

import java.util.List;
import java.util.Map;

/**
 * @description 投诉明细记录表
 * @author huangwenjie
 * @date 2018-01-09
 */
public interface JsmsComplaintListService {

    int insert(JsmsComplaintList model);
    
    int insertBatch(List<JsmsComplaintList> modelList);

    int update(JsmsComplaintList model);
    
    int updateSelective(JsmsComplaintList model);

    JsmsComplaintList getById(Integer id);

    List<JsmsComplaintList> getDuplicateData(Map params);

    List<JsmsComplaintList> findListGroup(Map params);

    JsmsPage queryList(JsmsPage page);

    JsmsPage queryListExt(JsmsPage page);

    List<JsmsComplaintList> findList(Map params);

    int count(Map<String, Object> params);

    int count4Channel(Map<String, Object> params);
    //投诉搜索的求总数
    int countComplaint(Map<String, Object> params);

    //根据id删除投诉
    int deleteById(Integer id);
    //搜索投诉,分页
    List<JsmsComplaintListExt> searchComplaint(JsmsPage page);

}
