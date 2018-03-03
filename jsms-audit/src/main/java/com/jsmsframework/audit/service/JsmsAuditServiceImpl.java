package com.jsmsframework.audit.service;

import com.jsmsframework.audit.dto.JsmsAuditidAndCreatetime;
import com.jsmsframework.audit.entity.JsmsAudit;
import com.jsmsframework.audit.enums.AuditPage;
import com.jsmsframework.audit.exception.JsmsAuditException;
import com.jsmsframework.audit.mapper.JsmsAuditMapper;
import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.enums.audit.AuditStatus;
import com.jsmsframework.common.enums.audit.JsmsAuditStatus;
import com.jsmsframework.common.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import static com.jsmsframework.common.util.JsonUtil.toJson;

import java.util.*;

/**
 * @description 审核内容表
 * @author huangwenjie
 * @date 2017-08-28
 */
@Service
public class JsmsAuditServiceImpl implements JsmsAuditService{
    private static Logger logger = LoggerFactory.getLogger(JsmsAuditServiceImpl.class);

    @Autowired
    private JsmsAuditMapper auditMapper;

    @Override
	@Transactional
    public int insert(JsmsAudit model) {
        return this.auditMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsAudit> modelList) {
        return this.auditMapper.insertBatch(modelList);
    }

    @Override
    @Transactional
    public int delete(Long auditid) {
        return this.auditMapper.delete(auditid);
    }

    @Override
	@Transactional
    public int update(JsmsAudit model) {
		JsmsAudit old = this.auditMapper.getByAuditid(model.getAuditid());
		if(old == null){
			return 0;
		}
		return this.auditMapper.update(model);
    }

    @Override
	@Transactional
    public int updateSelective(JsmsAudit model) {
		JsmsAudit old = this.auditMapper.getByAuditid(model.getAuditid());
		if(old != null)
        	return this.auditMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsAudit getByAuditid(Long auditid) {
        JsmsAudit model = this.auditMapper.getByAuditid(auditid);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAudit> list = this.auditMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.auditMapper.count(params);
    }

    @Override
    public Map<String, Object> getNeedBakCount(int day) {
        return this.auditMapper.getNeedBakCount(day);
    }

    @Override
    public List<JsmsAudit> findNeedBakList(int min, int max) {
        return this.auditMapper.findNeedBakList(min, max);
    }

    @Override
    @Transactional
    public int batchDeleteAudit(int[] auditIdList) {
        return this.auditMapper.batchDeleteAudit(auditIdList);
    }

    /**
     * 历史审核记录
     *
     * @param page
     * @return
     */
    @Override
    public JsmsPage queryhisList(JsmsPage page) {
        List<JsmsAudit> list = this.auditMapper.queryhisList(page);
        page.setData(list);
        return page;
    }

    @Override
    public int countHis(JsmsPage page) {
        return this.auditMapper.countHis(page);
    }

    @Override
    public List<Map<String, Object>> queryhisAll(Map<String, Object> params) {
        return this.auditMapper.queryhisAll(params);
    }

    /**
     * 审核表主表未删除记录
     * @return
     */
    @Override
    public List<Long> hasBakButNotDel() {
        return this.auditMapper.hasBakButNotDel();
    }

    @Override
    public List<JsmsAuditidAndCreatetime> queryAllRemoveAuditidAndCreatetime(Date dataDate) {
        return this.auditMapper.queryAllRemoveAuditidAndCreatetime(dataDate);
    }

    @Override
    public JsmsAudit getByAuditidAndCreatetime(Long auditid,Date createtime) {
        if(auditid==null&&createtime==null)
            return null;
        Map<String,Object> params = new HashMap<>();
        params.put("auditid",auditid);
        params.put("createtime",createtime);
        return this.auditMapper.getByAuditidAndCreatetime(params);
    }

    @Override
    public List<JsmsAudit> queryAuditRecord(String clientId,Long transferperson, TreeSet<String> majorClients, TreeSet<Long> lockedAuditids,
                                            String content, Date startCreateTime, Date endCreateTime, String sign, Integer smsType,
                                            Integer lessSendnum, Integer greaterSendnum, Long auditid, AuditPage auditPage, Integer limit){
        Map<String,Object> params = new HashMap<>();
        params.put("lockedAuditids",lockedAuditids);
        params.put("majorClients",majorClients);
        params.put("auditid",auditid);
        params.put("clientId",clientId);
        params.put("transferperson",transferperson);
        params.put("content",content);
        params.put("startCreateTime",startCreateTime);
        params.put("endCreateTime",endCreateTime);
        params.put("sign",sign);
        params.put("smsType",smsType);
        params.put("lessSendnum",lessSendnum);
        params.put("greaterSendnum",greaterSendnum);
        params.put("limit",limit);
        List<JsmsAudit> jsmsAudits = null;
        if(AuditPage.YZM_AUDIT_PAGE.equals(auditPage)){
            jsmsAudits = this.auditMapper.queryYZMAuditRecord(params);
        }else if(AuditPage.MAJOR_AUDIT_PAGE.equals(auditPage)){
            if (majorClients != null && !majorClients.isEmpty()){
                jsmsAudits = this.auditMapper.queryMajorAuditRecord(params);
            }
        }else if(AuditPage.ORDINARY_AUDIT_PAGE.equals(auditPage)){
            jsmsAudits = this.auditMapper.queryOrdinaryAuditRecord(params);
        }
        return jsmsAudits;
    }


    @Override
    public int deleteByAuditidAndCreatetime(Long auditid, Date createtime) {
        if(auditid==null&&createtime==null)
            return 0;
        Map<String,Object> params = new HashMap<>();
        params.put("auditid",auditid);
        params.put("createtime",createtime);
        return this.auditMapper.deleteByAuditidAndCreatetime(params);
    }

    @Override
    public int pass(Long auditid,String auditperson,String optRemark) {
        Assert.notNull(auditid,"审核id不能为空");
        Assert.notNull(auditperson,"审核人不能为空");

        if(auditid==0)
            return 0;
        JsmsAudit jsmsAudit = this.auditMapper.getByAuditid(auditid);
        logger.debug("设置短信状态为通过,origin jsmsAudit={}", toJson(jsmsAudit));
        if(jsmsAudit==null||!jsmsAudit.getStatus().equals(JsmsAuditStatus.待审核.getValue())){
            String msg = "操作失败，记录已经被被审核或不存在";
            logger.debug(msg);
            throw new JsmsAuditException(msg);
        }

        jsmsAudit.setStatus(JsmsAuditStatus.审核通过.getValue());
        jsmsAudit.setAuditperson(auditperson);
        jsmsAudit.setAudittime(new Date());
        if(StringUtils.isNotBlank(optRemark)){
            jsmsAudit.setOptRemark(optRemark);
        }
        int updateCount = this.auditMapper.updateStatus(jsmsAudit);
        logger.debug("updateCount={}, after update jsmsAudit={}",updateCount,toJson(jsmsAudit));

        return updateCount;
    }

    @Override
    public int nopass(Long auditid,String auditperson,String optRemark) {
        Assert.notNull(auditid,"审核id不能为空");
        Assert.notNull(auditperson,"审核人不能为空");
        Assert.notNull(optRemark,"审核原因不能为空");

        if(auditid==0)
            return 0;
        JsmsAudit jsmsAudit = this.auditMapper.getByAuditid(auditid);
        logger.debug("设置短信状态为通过,origin jsmsAudit={}", toJson(jsmsAudit));
        if(jsmsAudit==null||!jsmsAudit.getStatus().equals(JsmsAuditStatus.待审核.getValue())){
            String msg = "操作失败，记录已经被被审核或不存在";
            logger.debug(msg);
            throw new JsmsAuditException(msg);
        }

        jsmsAudit.setStatus(JsmsAuditStatus.审核不通过.getValue());
        jsmsAudit.setAuditperson(auditperson);
        jsmsAudit.setAudittime(new Date());
        jsmsAudit.setOptRemark(optRemark);
        int updateCount = this.auditMapper.updateStatus(jsmsAudit);
        logger.debug("updateCount={}, after update jsmsAudit={}",updateCount,toJson(jsmsAudit));

        return updateCount;
    }

    @Override
    public int transfer(Long auditid,String transferperson,String optRemark) {
        Assert.notNull(auditid,"审核id不能为空");
        Assert.notNull(transferperson,"转审人不能为空");
        Assert.notNull(optRemark,"审核原因不能为空");

        if(auditid==0)
            return 0;
        JsmsAudit jsmsAudit = this.auditMapper.getByAuditid(auditid);
        logger.debug("设置短信状态为转审,origin jsmsAudit={}", toJson(jsmsAudit));
        if(jsmsAudit==null||!jsmsAudit.getStatus().equals(JsmsAuditStatus.待审核.getValue())){
            String msg = "操作失败，记录已经被被审核或不存在";
            logger.debug(msg);
            throw new JsmsAuditException(msg);
        }

        jsmsAudit.setStatus(JsmsAuditStatus.转审.getValue());
        jsmsAudit.setTransferperson(transferperson);
        jsmsAudit.setTransfertime(new Date());
        jsmsAudit.setOptRemark(optRemark);
        int updateCount = this.auditMapper.updateStatus(jsmsAudit);
        logger.debug("updateCount={}, after update jsmsAudit={}",updateCount,toJson(jsmsAudit));

        return updateCount;
    }

    @Override
    public int pass(List<Long> auditIdList, String auditperson, String optRemark) {
        Assert.notNull(auditIdList,"审核id不能为空");
        Assert.notNull(auditperson,"审核人不能为空");

        if(auditIdList.size()==0)
            return 0;
        Map params = new HashMap();
        params.put("auditIdList",auditIdList);
        List<JsmsAudit> jsmsAudits = auditMapper.findList(params);
        logger.debug("设置短信状态为通过,origin jsmsAudit={}", toJson(jsmsAudits));
        if(jsmsAudits==null||jsmsAudits.isEmpty())
            return 0;

        List<Long> updateAuditIds = new ArrayList<>();
        for(JsmsAudit jsmsAudit:jsmsAudits){
            if(!jsmsAudit.getStatus().equals(JsmsAuditStatus.待审核.getValue())){//预防findList方法出错
                String msg = "";
                logger.debug("审核记录auditId的状态不正确, 跳过此auditId={}",jsmsAudit.getAuditid());
                continue;
            }
            if(auditIdList.contains(jsmsAudit.getAuditid())) {
                updateAuditIds.add(jsmsAudit.getAuditid());
            }else{
                logger.debug("审核记录auditId不正确, auditId={}",jsmsAudit.getAuditid());
            }
        }
        if(updateAuditIds.isEmpty()){
            logger.debug("审核记录不存在或状态不正确");
            return 0;
        }

        Map updateParams = new HashMap();
        updateParams.put("updateAuditIds",updateAuditIds);
        updateParams.put("auditperson",auditperson);
        updateParams.put("audittime",new Date());
        updateParams.put("status",JsmsAuditStatus.审核通过.getValue());

        if(StringUtils.isNotBlank(optRemark)){
            updateParams.put("optRemark",optRemark);
        }

        int updateCount = this.auditMapper.updateStatusBatch(updateParams);
        logger.debug("updateCount={}, updateParams",updateCount, toJson(updateParams));

        return updateCount;
    }


    @Override
    public int nopass(List<Long> auditIdList, String auditperson, String optRemark) {
        Assert.notNull(auditIdList,"审核id不能为空");
        Assert.notNull(auditperson,"审核人不能为空");
        Assert.notNull(optRemark,"审核原因不能为空");

        if(auditIdList.size()==0)
            return 0;
        Map params = new HashMap();
        params.put("auditIdList",auditIdList);
        List<JsmsAudit> jsmsAudits = auditMapper.findList(params);
        logger.debug("设置短信状态为不通过,origin jsmsAudit={}", toJson(jsmsAudits));
        if(jsmsAudits==null||jsmsAudits.isEmpty())
            return 0;

        List<Long> updateAuditIds = new ArrayList<>();
        for(JsmsAudit jsmsAudit:jsmsAudits){
            if(!jsmsAudit.getStatus().equals(JsmsAuditStatus.待审核.getValue())){
                String msg = "";
                logger.debug("审核记录auditId的状态不正确, 跳过此auditId={}",jsmsAudit.getAuditid());
                continue;
            }
            if(auditIdList.contains(jsmsAudit.getAuditid())) {  //预防findList方法出错
                updateAuditIds.add(jsmsAudit.getAuditid());
            }else{
                logger.debug("审核记录auditId不正确, auditId={}",jsmsAudit.getAuditid());
            }
        }
        if(updateAuditIds.isEmpty()){
            logger.debug("审核记录不存在或状态不正确");
            return 0;
        }


        Map updateParams = new HashMap();
        updateParams.put("updateAuditIds",updateAuditIds);
        updateParams.put("auditperson",auditperson);
        updateParams.put("audittime",new Date());
        updateParams.put("status",JsmsAuditStatus.审核不通过.getValue());

        if(StringUtils.isNotBlank(optRemark)){
            updateParams.put("optRemark",optRemark);
        }

        int updateCount = this.auditMapper.updateStatusBatch(updateParams);
        logger.debug("updateCount={}, updateParams",updateCount, toJson(updateParams));

        return updateCount;
    }


    @Override
    public int transfer(List<Long> auditIdList, String transferperson, String optRemark) {
        Assert.notNull(auditIdList,"审核id不能为空");
        Assert.notNull(transferperson,"转审人不能为空");
        Assert.notNull(optRemark,"审核原因不能为空");

        if(auditIdList.size()==0)
            return 0;
        Map params = new HashMap();
        params.put("auditIdList",auditIdList);
        List<JsmsAudit> jsmsAudits = auditMapper.findList(params);
        logger.debug("设置短信状态为转审,origin jsmsAudit={}", toJson(jsmsAudits));
        if(jsmsAudits==null||jsmsAudits.isEmpty())
            return 0;

        List<Long> updateAuditIds = new ArrayList<>();
        for(JsmsAudit jsmsAudit:jsmsAudits){
            if(!jsmsAudit.getStatus().equals(JsmsAuditStatus.待审核.getValue())){
                String msg = "";
                logger.debug("审核记录auditId的状态不正确, 跳过此auditId={}",jsmsAudit.getAuditid());
                continue;
            }
            if(auditIdList.contains(jsmsAudit.getAuditid())) {  //预防findList方法出错
                updateAuditIds.add(jsmsAudit.getAuditid());
            }else{
                logger.debug("审核记录auditId不正确, auditId={}",jsmsAudit.getAuditid());
            }
        }
        if(updateAuditIds.isEmpty()){
            logger.debug("审核记录不存在或状态不正确");
            return 0;
        }


        Map updateParams = new HashMap();
        updateParams.put("updateAuditIds",updateAuditIds);
        updateParams.put("transferperson",transferperson);
        updateParams.put("transfertime",new Date());
        updateParams.put("status",JsmsAuditStatus.转审.getValue());

        if(StringUtils.isNotBlank(optRemark)){
            updateParams.put("optRemark",optRemark);
        }

        int updateCount = this.auditMapper.updateStatusBatch(updateParams);
        logger.debug("updateCount={}, updateParams",updateCount, toJson(updateParams));

        return updateCount;
    }
}
