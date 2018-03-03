package com.jsmsframework.audit.service;

import com.jsmsframework.audit.dto.JsmsAuditKeywordCategoryDTO;
import com.jsmsframework.audit.dto.JsmsAuditKeywordListAndCategoryDTO;
import com.jsmsframework.audit.entity.*;
import com.jsmsframework.audit.exception.JsmsAuditKeywordListException;
import com.jsmsframework.audit.mapper.JsmsAuditKeywordListMapper;
import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.enums.audit.AuditClientGroupIsDefault;
import com.jsmsframework.common.util.BeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * @description 审核关键字列表
 * @author huangwenjie
 * @date 2017-10-31
 */
@Service
public class JsmsAuditKeywordListServiceImpl implements JsmsAuditKeywordListService{

    @Autowired
    private JsmsAuditKeywordListMapper auditKeywordListMapper;
    @Autowired
    private JsmsAuditClientGroupService jsmsAuditClientGroupService;
    @Autowired
    private JsmsAuditKeywordCategoryService jsmsAuditKeywordCategoryService;
    @Autowired
    private JsmsAuditKgroupRefCategoryService jsmsAuditKgroupRefCategoryService;
    
    @Override
    public int insert(JsmsAuditKeywordList model) {
        return this.auditKeywordListMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsAuditKeywordList> modelList) {
        return this.auditKeywordListMapper.insertBatch(modelList);
    }

	@Override
    public int update(JsmsAuditKeywordList model) {
		JsmsAuditKeywordList old = this.auditKeywordListMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.auditKeywordListMapper.update(model); 
    }

    @Override
    public int updateIdempotent(JsmsAuditKeywordList oldModel, JsmsAuditKeywordList newModel) {
        JsmsAuditKeywordList old = this.auditKeywordListMapper.getById(oldModel.getId());
        if (old != null) {
            Map<String, JsmsAuditKeywordList> idempotentParams = new HashMap<>();
            idempotentParams.put("oldModel", oldModel);
            idempotentParams.put("newModel", newModel);
            return this.auditKeywordListMapper.updateIdempotent(idempotentParams);
        }
        return 0;
    }

	@Override
    public int delete(Long id) {

        return this.auditKeywordListMapper.delete(id);
    }

    @Override
    public int updateSelective(JsmsAuditKeywordList model) {
		JsmsAuditKeywordList old = this.auditKeywordListMapper.getById(model.getId());
		if(old != null)
        	return this.auditKeywordListMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsAuditKeywordList getById(Long id) {
        JsmsAuditKeywordList model = this.auditKeywordListMapper.getById(id);
		return model;
    }

    @Override
    public JsmsAuditKeywordList getByKeyword(String keyword) {
        JsmsAuditKeywordList model = this.auditKeywordListMapper.getByKeyword(keyword);
		return model;
    }

    @Override
    public JsmsAuditKeywordList getByKeywordAndCategoryId(String keyword,Integer categoryId) {
        JsmsAuditKeywordList model = this.auditKeywordListMapper.getByKeywordAndCategoryId(keyword,categoryId);
		return model;
    }


    @Override
    public List<JsmsAuditKeywordList> getByKeywords(Set<String> keywords) {
        if (keywords == null || keywords.isEmpty()){
            return null;
        }
        List<JsmsAuditKeywordList> list = this.auditKeywordListMapper.getByKeywords(keywords);
		return list;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        Map params = page.getParams();
        if(params!=null){
            String condition = (String) params.get("condition");
            String simplifredChineseCondition = (String) params.get("simplifredChineseCondition");
            String traditionalChineseCondition = (String) params.get("traditionalChineseCondition");
            if(StringUtils.isNotBlank(condition)){
                condition = condition.replaceAll("\\\\","\\\\\\\\");
                condition = condition.replaceAll("_","\\\\_");
                condition = condition.replaceAll("%","\\\\%");
                condition = condition.replaceAll("'","\\\\'");
                params.put("condition",condition);
            }
            if(StringUtils.isNotBlank(simplifredChineseCondition)){
                simplifredChineseCondition = simplifredChineseCondition.replaceAll("\\\\","\\\\\\\\");
                simplifredChineseCondition = simplifredChineseCondition.replaceAll("_","\\\\_");
                simplifredChineseCondition = simplifredChineseCondition.replaceAll("%","\\\\%");
                simplifredChineseCondition = simplifredChineseCondition.replaceAll("'","\\\\'");
                params.put("simplifredChineseCondition",simplifredChineseCondition);
            }
            if(StringUtils.isNotBlank(traditionalChineseCondition)){
                traditionalChineseCondition = traditionalChineseCondition.replaceAll("\\\\","\\\\\\\\");
                traditionalChineseCondition = traditionalChineseCondition.replaceAll("_","\\\\_");
                traditionalChineseCondition = traditionalChineseCondition.replaceAll("%","\\\\%");
                traditionalChineseCondition = traditionalChineseCondition.replaceAll("'","\\\\'");
                params.put("traditionalChineseCondition",traditionalChineseCondition);
            }
        }

        List<JsmsAuditKeywordList> list = this.auditKeywordListMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.auditKeywordListMapper.count(params);
    }


    @Override
    public List<JsmsAuditKeywordList> getByClientId(String clientId) {

        //1. 获取用户关联的组别，2.5需求定义了 一个用户只能在一个组别中
        List<JsmsAuditClientGroup> clientGroups = jsmsAuditClientGroupService.getKgroupIdsByClientId(clientId);
        if(clientGroups==null)
            return null;

        if(clientGroups.size()>1){
            throw new JsmsAuditKeywordListException("关键字分级配置异常，2.5需求定义了 一个用户只能在一个组别中");
        }


        //2. 获取用户关联的类别ID
        List<JsmsAuditKgroupRefCategory> jsmsAuditKgroupRefCategories = jsmsAuditKgroupRefCategoryService.getBykgroupId(clientGroups.get(0).getKgroupId());

        List<Integer> categoryIds = new ArrayList<>();
        for(JsmsAuditKgroupRefCategory ref:jsmsAuditKgroupRefCategories){
            categoryIds.add(ref.getCategoryId());
        }
        List<JsmsAuditKeywordList> jsmsAuditKeywordLists = this.getByCategoryIds(categoryIds);

        return jsmsAuditKeywordLists;
    }

    @Override
    public List<JsmsAuditKeywordList> getByCategoryIds(List<Integer> categoryIds) {
        return this.auditKeywordListMapper.getByCategoryIds(categoryIds);
    }


    @Override
    public List<JsmsAuditKeywordListAndCategoryDTO> getListAndCategoryByClientId(String clientId) {

        //1. 获取用户关联的组别，2.5需求定义了 一个用户只能在一个组别中
        List<JsmsAuditClientGroup> clientGroups = jsmsAuditClientGroupService.getKgroupIdsByClientId(clientId);
        if(clientGroups==null||clientGroups.isEmpty())
            return null;

        if(clientGroups.size()>1){
            throw new JsmsAuditKeywordListException("关键字分级配置异常，2.5需求定义了 一个用户只能在一个组别中");
        }
        JsmsAuditClientGroup group = clientGroups.get(0);


        //2. 获取用户关联的类别ID
        List<JsmsAuditKgroupRefCategory> jsmsAuditKgroupRefCategories = jsmsAuditKgroupRefCategoryService.getBykgroupId(group.getKgroupId());
        List<Integer> categoryIds = new ArrayList<>();
        Map<Integer,JsmsAuditKgroupRefCategory> jsmsAuditKgroupRefCategoryMap = new HashMap<>();
        for(JsmsAuditKgroupRefCategory ref:jsmsAuditKgroupRefCategories){
            categoryIds.add(ref.getCategoryId());
            jsmsAuditKgroupRefCategoryMap.put(ref.getCategoryId(),ref);
        }

        //3. 根据类别ID获取关键字列表
        List<JsmsAuditKeywordList> jsmsAuditKeywordLists = this.getByCategoryIds(categoryIds);

        //4. 根据类别ID获取类别信息
        List<JsmsAuditKeywordCategory> jsmsAuditKeywordCategories = jsmsAuditKeywordCategoryService.getByCategoryIds(categoryIds);

        //5. 将类别和关键字列表 组装成DTO
        List<JsmsAuditKeywordListAndCategoryDTO> jsmsAuditKeywordListAndCategoryDTOS = new ArrayList<>();
        Map<Integer,JsmsAuditKeywordListAndCategoryDTO> jsmsAuditKeywordListAndCategoryDTOHashMap = new HashMap<>();
        for(JsmsAuditKeywordCategory jsmsAuditKeywordCategory:jsmsAuditKeywordCategories){

            //5.1 确定category的危险级别sort
            JsmsAuditKeywordCategoryDTO jsmsAuditKeywordCategoryDTO = new JsmsAuditKeywordCategoryDTO();
            BeanUtil.copyProperties(jsmsAuditKeywordCategory,jsmsAuditKeywordCategoryDTO);
            JsmsAuditKgroupRefCategory ref = jsmsAuditKgroupRefCategoryMap.get(jsmsAuditKeywordCategory.getCategoryId());
            jsmsAuditKeywordCategoryDTO.setSort(ref.getSort());
            jsmsAuditKeywordCategoryDTO.setAuditClientGroupIsDefault(AuditClientGroupIsDefault.getByValue(group.getIsDefault()));

            JsmsAuditKeywordListAndCategoryDTO dto = new JsmsAuditKeywordListAndCategoryDTO(jsmsAuditKeywordCategoryDTO,new ArrayList<JsmsAuditKeywordList>());
            jsmsAuditKeywordListAndCategoryDTOS.add(dto);
            jsmsAuditKeywordListAndCategoryDTOHashMap.put(jsmsAuditKeywordCategory.getCategoryId(),dto);
        }

        for(JsmsAuditKeywordList jsmsAuditKeywordList:jsmsAuditKeywordLists){
            jsmsAuditKeywordListAndCategoryDTOHashMap.get(jsmsAuditKeywordList.getCategoryId()).getKeywordLists().add(jsmsAuditKeywordList);
        }


        return jsmsAuditKeywordListAndCategoryDTOS;
    }

    @Override
    public List<Map<String, Object>> queryByKgroupId(Integer kgroupId) {
        return this.auditKeywordListMapper.queryByKgroupId(kgroupId);
    }
}
