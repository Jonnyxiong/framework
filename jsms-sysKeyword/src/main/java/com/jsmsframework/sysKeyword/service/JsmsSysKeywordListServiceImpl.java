package com.jsmsframework.sysKeyword.service;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Set;

import com.jsmsframework.sysKeyword.entity.JsmsSysCgroupRefClient;
import com.jsmsframework.sysKeyword.entity.JsmsSysClientGroup;
import com.jsmsframework.sysKeyword.entity.JsmsSysKgroupRefCategory;
import com.jsmsframework.sysKeyword.mapper.JsmsSysCgroupRefClientMapper;
import com.jsmsframework.sysKeyword.mapper.JsmsSysClientGroupMapper;
import com.jsmsframework.sysKeyword.mapper.JsmsSysKgroupRefCategoryMapper;
import com.jsmsframework.sysKeyword.util.SysKeywordUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsmsframework.sysKeyword.mapper.JsmsSysKeywordListMapper;
import com.jsmsframework.sysKeyword.entity.JsmsKeywordList;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 系统关键字列表
 * @author huangwenjie
 * @date 2018-01-15
 */
@Service("jsmsSysKeywordCategoryService")
public class JsmsSysKeywordListServiceImpl implements JsmsSysKeywordListService {

    @Autowired
    private JsmsSysKeywordListMapper keywordListMapper;
    @Autowired
    private JsmsSysCgroupRefClientMapper jsmsSysCgroupRefClientMapper;
    @Autowired
    private JsmsSysClientGroupMapper jsmsSysClientGroupMapper;
    @Autowired
    private JsmsSysKgroupRefCategoryMapper jsmsSysKgroupRefCategoryMapper;

    @Override
    public int insert(JsmsKeywordList model) {
        return this.keywordListMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsKeywordList> modelList) {
        return this.keywordListMapper.insertBatch(modelList);
    }

	@Override
    public int update(JsmsKeywordList model) {
		JsmsKeywordList old = this.keywordListMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.keywordListMapper.update(model); 
    }

    @Override
    public int updateSelective(JsmsKeywordList model) {
		JsmsKeywordList old = this.keywordListMapper.getById(model.getId());
		if(old != null)
        	return this.keywordListMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsKeywordList getById(Long id) {
        JsmsKeywordList model = this.keywordListMapper.getById(id);
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        SysKeywordUtil.textTransform(page.getParams());
        List<JsmsKeywordList> list = this.keywordListMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public List<JsmsKeywordList> findList(Map params) {
        List<JsmsKeywordList> list = this.keywordListMapper.findList(params);
        return list;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.keywordListMapper.count(params);
    }

    @Override
    public int deleteById(Long id) {
        return this.keywordListMapper.deleteById(id);
    }

    @Override
    public List<JsmsKeywordList> findByKeywordsAndCategoryId(Set<String> keywords,Integer categoryId) {
        List<JsmsKeywordList> list = null;
        if (!keywords.isEmpty()){
            list = this.keywordListMapper.findByKeywordsAndCategoryId(keywords,categoryId);
        }
        return list;
    }

    @Override
    public List<String> queryKeywordByCategoryId(Integer categoryId) {
        return this.keywordListMapper.queryKeywordByCategoryId(categoryId);
    }

    @Override
    public List<String> queryKeywordByClientId(String clientId) {
        // 2018-1-20 系统关键字分类分级，需要先查找用户存在那个系统关键字用户组
        List<String> systemKeywordList = new ArrayList<String>();
        JsmsSysCgroupRefClient refClient = jsmsSysCgroupRefClientMapper.getByClientId(clientId);
        Integer cgroupId,kgroupId;
        if (refClient==null){
            //不存在的话则查找默认用户组
            JsmsSysClientGroup clientGroup = jsmsSysClientGroupMapper.getDefault();
            kgroupId = clientGroup.getKgroupId();
        }else{
            //查找对应系统关键字组别
            JsmsSysClientGroup clientGroup = jsmsSysClientGroupMapper.getByCgroupId(refClient.getCgroupId());
            kgroupId = clientGroup.getKgroupId();
        }
        //查找组内的所有类别
        List<JsmsSysKgroupRefCategory> refCategories = jsmsSysKgroupRefCategoryMapper.getByKgroupId(kgroupId);
        if (!refCategories.isEmpty()){
            for (JsmsSysKgroupRefCategory refCategory:refCategories) {
                List<String> keywords = this.queryKeywordByCategoryId(refCategory.getCategoryId());
                systemKeywordList.addAll(keywords);
            }
        }
        return systemKeywordList;
    }
}
