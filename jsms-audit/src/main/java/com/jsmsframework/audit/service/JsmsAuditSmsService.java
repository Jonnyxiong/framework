package com.jsmsframework.audit.service;

import com.jsmsframework.audit.dto.JsmsIdAndCreatetime;
import com.jsmsframework.audit.entity.JsmsAuditSms;
import com.jsmsframework.common.dto.JsmsPage;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description 审核明细表
 * @author huangwenjie
 * @date 2017-08-29
 */
public interface JsmsAuditSmsService {

    int insert(JsmsAuditSms model);
    
    int insertBatch(List<JsmsAuditSms> modelList);

    int delete(Long id);

    int update(JsmsAuditSms model);

    int updateSelective(JsmsAuditSms model);

    JsmsAuditSms getById(Long id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    /**
     * 获取需要备份的数据（包含 总数量、最大ID，最小ID）
     * @return
     */
    Map<String, Object> getNeedBakCount(int day);

    List<JsmsAuditSms> findNeedBakList(@Param("min") int min, @Param("max") int max);

    int batchDeleteAuditSms(int[] auditSmsIdList);

    List<Long> hasBakButNotDel();


    /**
     * 获取审核短信主表待备份的id和createtime，预测1000W数据为600M左右内存  （16+8+8+32）*1000W / 1024 /1024
     * @param dataDate  数据时间，createtime小于该时间的则满足条件
     * @return
     */
    List<JsmsIdAndCreatetime> queryAllRemoveIdAndCreatetime(Date dataDate);


    /**
     * 根据联合主键(id,createtime)去获取审核短信信息
     * @return
     */
    JsmsAuditSms getByIdAndCreatetime(Long id, Date createtime);



    /**
     * 根据联合主键(id,createtime)去删除审核信息
     * @return
     */
    int deleteByIdAndCreatetime(Long id,Date createtime);
}
