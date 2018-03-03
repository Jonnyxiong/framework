package com.jsmsframework.audit.service;

import com.jsmsframework.audit.dto.JsmsAuditidAndCreatetime;
import com.jsmsframework.audit.entity.JsmsAudit;
import com.jsmsframework.audit.enums.AuditPage;
import com.jsmsframework.common.dto.JsmsPage;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * @description 审核内容表
 * @author huangwenjie
 * @date 2017-08-28
 */
public interface JsmsAuditService {

    int insert(JsmsAudit model);
    
    int insertBatch(List<JsmsAudit> modelList);

    int delete(Long auditid);

    int update(JsmsAudit model);
    
    int updateSelective(JsmsAudit model);

    JsmsAudit getByAuditid(Long auditid);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    /*
    * 获取需要备份的数据（包含 总数量、最大ID，最小ID）
    * @return
    */
    Map<String, Object> getNeedBakCount(int day);

    List<JsmsAudit> findNeedBakList(@Param("min") int min, @Param("max") int max);

    /**
     * 批量删除
     *
     * @param auditIdList
     */
    int batchDeleteAudit(int[] auditIdList);


    /**
     * 历史审核记录
     * @param page
     * @return
     */
    JsmsPage queryhisList(JsmsPage page);

    int countHis(JsmsPage page);
    
    List<Map<String,Object>> queryhisAll(Map<String,Object> params);

    List<Long> hasBakButNotDel();

    /**
     * 获取审核表主表待备份的auditid和createtime，预测1000W数据为600M左右内存  （16+8+8+32）*1000W / 1024 /1024
     * @param dataDate  数据时间，createtime小于该时间的则满足条件
     * @return
     */
    List<JsmsAuditidAndCreatetime> queryAllRemoveAuditidAndCreatetime(Date dataDate);


    /**
     * 根据联合主键(auditid,createtime)去获取审核信息
     * @return
     */
    JsmsAudit getByAuditidAndCreatetime(Long auditid,Date createtime);

    /**
     * @param clientId
     * @param majorClients 重点客户
     * @param lockedAuditids 锁定的客户id
     * @param content 包含内容
     * @param startCreateTime 起始创建时间
     * @param endCreateTime 结束的创建时间
     * @param sign 签名
     * @param smsType 短信类型
     * @param lessSendnum 最低发送数量
     * @param greaterSendnum 最大发送数量
     * @param auditid 审核id
     * @param auditPage 对应审核页面(验证码审核页面、重要客户审核页面、普通审核页面)
     * @param limit 查询记录
     * @return
     */
    List<JsmsAudit> queryAuditRecord(String clientId,Long transferperson, TreeSet<String> majorClients, TreeSet<Long> lockedAuditids, String content, Date startCreateTime, Date endCreateTime, String sign, Integer smsType, Integer lessSendnum, Integer greaterSendnum, Long auditid, AuditPage auditPage, Integer limit);
    /**
     * 根据联合主键(auditid,createtime)去删除审核信息
     * @return
     */
    int deleteByAuditidAndCreatetime(Long auditid,Date createtime);

    /**
     *
     * 设置短信为通过
     * @param auditid  短信审核id
     * @param auditperson  审核人 @see userId
     * @param optRemark  审核备注
     * @return
     */
    int pass(Long auditid,String auditperson,String optRemark);


    /**
     * 设置短信为不通过
     * @param auditid
     * @return
     */
    int nopass(Long auditid,String auditperson,String optRemark);


    /**
     * 设置短信为转审
     * @param auditid
     * @return
     */
    int transfer(Long auditid,String transferperson,String optRemark);



    /**
     *
     * 批量设置短信为通过
     * @param auditIdList  短信审核ids
     * @param auditperson  审核人 @see userId
     * @param optRemark  审核备注
     * @return
     */
    int pass(List<Long> auditIdList,String auditperson,String optRemark);


    /**
     *
     * 批量设置短信为不通过
     * @param auditIdList  短信审核ids
     * @param auditperson  审核人 @see userId
     * @param optRemark  审核备注
     * @return
     */
    int nopass(List<Long> auditIdList,String auditperson,String optRemark);


    /**
     * 批量设置短信为转审
     * @param auditIdList  短信审核ids
     * @param transferperson  审核人 @see userId
     * @param optRemark
     * @return
     */
    int transfer(List<Long> auditIdList,String transferperson,String optRemark);



}
