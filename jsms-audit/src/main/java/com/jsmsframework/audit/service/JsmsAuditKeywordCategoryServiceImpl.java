package com.jsmsframework.audit.service;

import com.jsmsframework.audit.entity.JsmsAuditCgroupRefClient;
import com.jsmsframework.audit.entity.JsmsAuditKeywordCategory;
import com.jsmsframework.audit.entity.JsmsAuditKeywordGroup;
import com.jsmsframework.audit.entity.JsmsAuditKeywordList;
import com.jsmsframework.audit.exception.JsmsAuditKeywordCategoryException;
import com.jsmsframework.audit.mapper.JsmsAuditKeywordCategoryMapper;
import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.dto.ResultVO;
import com.jsmsframework.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @description 审核关键字分类表
 * @author huangwenjie
 * @date 2017-10-31
 */
@Service
public class JsmsAuditKeywordCategoryServiceImpl implements JsmsAuditKeywordCategoryService{

    private static Logger logger = LoggerFactory.getLogger(JsmsAuditKeywordCategoryService.class);

    @Autowired
    private JsmsAuditKeywordCategoryMapper auditKeywordCategoryMapper;
    @Autowired
    private JsmsAuditKeywordListService jsmsAuditKeywordListService;
    @Autowired
    private JsmsAuditKgroupRefCategoryService jsmsAuditKgroupRefCategoryService;
    @Autowired
    private JsmsAuditKeywordGroupService jsmsAuditKeywordGroupService;
    @Autowired
    private JsmsAuditCgroupRefClientService jsmsAuditCgroupRefClientService;
    
    @Override
	@Transactional
    public int insert(JsmsAuditKeywordCategory model) {
        return this.auditKeywordCategoryMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsAuditKeywordCategory> modelList) {
        return this.auditKeywordCategoryMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsAuditKeywordCategory model) {
		JsmsAuditKeywordCategory old = this.auditKeywordCategoryMapper.getByCategoryId(model.getCategoryId());
		if(old == null){
			return 0;
		}
		return this.auditKeywordCategoryMapper.update(model); 
    }

    @Override
    public int updateIdempotent(JsmsAuditKeywordCategory oldModel, JsmsAuditKeywordCategory newModel) {
        JsmsAuditKeywordCategory old = this.auditKeywordCategoryMapper.getByCategoryId(oldModel.getCategoryId());
        if (old != null) {
            Map<String, JsmsAuditKeywordCategory> idempotentParams = new HashMap<>();
            idempotentParams.put("oldModel", oldModel);
            idempotentParams.put("newModel", newModel);
            return this.auditKeywordCategoryMapper.updateIdempotent(idempotentParams);
        }
        return 0;
    }

    @Override
	@Transactional
    public int updateSelective(JsmsAuditKeywordCategory model) {
		JsmsAuditKeywordCategory old = this.auditKeywordCategoryMapper.getByCategoryId(model.getCategoryId());
		if(old != null)
        	return this.auditKeywordCategoryMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsAuditKeywordCategory getByCategoryId(Integer categoryId) {
        JsmsAuditKeywordCategory model = this.auditKeywordCategoryMapper.getByCategoryId(categoryId);
		return model;
    }

    @Override
    public List<JsmsAuditKeywordCategory> getByCategoryName(String categoryName) {
        List<JsmsAuditKeywordCategory> list = this.auditKeywordCategoryMapper.getByCategoryName(categoryName);
		return list;
    }

    @Override
    public List<JsmsAuditKeywordCategory> getByCategoryNames(Set<String> categoryNames){
        if (categoryNames == null || categoryNames.isEmpty()){
            return null;
        }
        List<JsmsAuditKeywordCategory> list = this.auditKeywordCategoryMapper.getByCategoryNames(categoryNames);
		return list;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAuditKeywordCategory> list = this.auditKeywordCategoryMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.auditKeywordCategoryMapper.count(params);
    }

    @Override
    public List<JsmsAuditKeywordCategory> getList() {
        return this.auditKeywordCategoryMapper.getList();
    }

    @Override
    public List<JsmsAuditKeywordCategory> getExitList(Integer kgroupId) {
        return auditKeywordCategoryMapper.getExitList(kgroupId);
    }


    @Override
    public ResultVO delete(Integer categoryId) {
        //增加删除功能，点击删除，需判断该类别中是否含有审核关键字，如有则删除失败，如无，则判断该类别是否存在于组别中，如存在则，删除失败，如无，则删除成功。
        //查看原有的类别
        JsmsAuditKeywordCategory jsmsAuditKeywordCategory = this.getByCategoryId(categoryId);
        if(jsmsAuditKeywordCategory==null){
            throw new JsmsAuditKeywordCategoryException("不存在此关键字类别");
        }
        logger.debug("删除关键字类别jsmsAuditKeywordCategory={}", JsonUtil.toJson(jsmsAuditKeywordCategory));

        //1. 有审核关键字则删除失败
        Map<String, Object> keywordParams = new HashMap<>();
        keywordParams.put("categoryId",categoryId);
        int keywordCount = jsmsAuditKeywordListService.count(keywordParams);
        if(keywordCount>0){
            logger.debug("此类别已存在关键字，删除失败，categoryId={}",categoryId);
            throw new JsmsAuditKeywordCategoryException("此类别已存在关键字，删除失败");
        }

        //2. 存在于组别中
        int refCount = jsmsAuditKgroupRefCategoryService.count(keywordParams);
        if(refCount>0){
            logger.debug("此类别关联了其他组别，删除失败，categoryId={}",categoryId);
            throw new JsmsAuditKeywordCategoryException("此类别关联了其他组别，删除失败");
        }

        //3. 删除
        int row = this.auditKeywordCategoryMapper.delete(categoryId);
        if(row==1){
            logger.debug("删除类别成功");
            return ResultVO.successDefault("删除类别成功");
        }else {
            logger.debug("删除类别失败");
            throw new JsmsAuditKeywordCategoryException("操作失败，数据存在异常或此类别已被删除");
        }
    }

    @Override
    public List<JsmsAuditKeywordCategory> getByCategoryIds(List<Integer> keywordGroupIds) {
        return this.auditKeywordCategoryMapper.getByCategoryIds(keywordGroupIds);
    }

    @Override
    public List<String> getRefClientIdsByCategoryId(Integer categoryId) {
        List<JsmsAuditKeywordGroup> jsmsAuditKeywordGroups = jsmsAuditKeywordGroupService.getByRefCategoryId(categoryId);
        if(jsmsAuditKeywordGroups==null||jsmsAuditKeywordGroups.isEmpty()){
            return null;
        }
        List<String> clientIds = new ArrayList<>();
        for(JsmsAuditKeywordGroup group:jsmsAuditKeywordGroups){
            List<String> clientIdsSub = jsmsAuditKeywordGroupService.getRefClientIdsByGroupId(group.getKgroupId());
            clientIds.addAll(clientIdsSub);
        }
        return clientIds;
    }
}
