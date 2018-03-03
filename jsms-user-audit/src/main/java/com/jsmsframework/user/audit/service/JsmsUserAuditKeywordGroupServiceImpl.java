package com.jsmsframework.user.audit.service;

import com.jsmsframework.audit.dto.JsmsAuditKeywordGroupDTO;
import com.jsmsframework.audit.entity.JsmsAuditKeywordCategory;
import com.jsmsframework.audit.entity.JsmsAuditKeywordGroup;
import com.jsmsframework.audit.entity.JsmsAuditKgroupRefCategory;
import com.jsmsframework.audit.mapper.JsmsAuditClientGroupMapper;
import com.jsmsframework.audit.mapper.JsmsAuditKeywordGroupMapper;
import com.jsmsframework.audit.mapper.JsmsAuditKgroupRefCategoryMapper;
import com.jsmsframework.audit.service.JsmsAuditKeywordCategoryService;
import com.jsmsframework.audit.service.JsmsAuditKgroupRefCategoryService;
import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.user.entity.JsmsUser;
import com.jsmsframework.user.service.JsmsUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiongfenglin on 2017/10/31.
 *
 * @author: xiongfenglin
 */
@Service
public class JsmsUserAuditKeywordGroupServiceImpl implements  JsmsUserAuditKeywordGroupService{
    @Autowired
    private JsmsAuditKeywordGroupMapper auditKeywordGroupMapper;
    @Autowired
    private JsmsAuditKeywordCategoryService jsmsAuditKeywordCategoryService;
    @Autowired
    private JsmsAuditKgroupRefCategoryService jsmsAuditKgroupRefCategoryService;
    @Autowired
    private JsmsAuditKgroupRefCategoryMapper jsmsAuditKgroupRefCategoryMapper;
    @Autowired
    private JsmsAuditClientGroupMapper jsmsAuditClientGroupMapper;
    @Autowired
    private JsmsUserService jsmsUserService;
    @Override
    @Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAuditKeywordGroup> list = auditKeywordGroupMapper.queryList(page);
        List<JsmsAuditKeywordGroupDTO> list2 = new ArrayList<>();
        JsmsAuditKeywordCategory jsmsAuditKeywordCategory = new JsmsAuditKeywordCategory();
        List<JsmsAuditKgroupRefCategory> jsmsAuditKgroupRefCategory =  new ArrayList<>();
        JsmsUser jsmsUser = new JsmsUser();
        StringBuilder categoryName = new StringBuilder();
        if(list!=null&&list.size()!=0){
            for(int i =0;i<list.size();i++){
                categoryName.delete(0,categoryName.length());
                JsmsAuditKeywordGroupDTO jsmsAuditKeywordGroupDTO = new JsmsAuditKeywordGroupDTO();
                BeanUtils.copyProperties(list.get(i) , jsmsAuditKeywordGroupDTO);
                jsmsUser = jsmsUserService.getById(String.valueOf(jsmsAuditKeywordGroupDTO.getOperator()));
                if(jsmsUser!=null){
                    jsmsAuditKeywordGroupDTO.setUserName(jsmsUser.getRealname());
                }
                if(list.get(i).getKgroupId()!=null){
                    jsmsAuditKgroupRefCategory = jsmsAuditKgroupRefCategoryService.getBykgroupId(list.get(i).getKgroupId());
                    if(jsmsAuditKgroupRefCategory!=null&&jsmsAuditKgroupRefCategory.size()>0){
                        for(int j =0;j<jsmsAuditKgroupRefCategory.size();j++){
                            if(jsmsAuditKgroupRefCategory.get(j).getCategoryId()!=null){
                                jsmsAuditKeywordCategory = jsmsAuditKeywordCategoryService.getByCategoryId(jsmsAuditKgroupRefCategory.get(j).getCategoryId());
                                if(j==0&&j!=jsmsAuditKgroupRefCategory.size()-1){
                                    categoryName = categoryName.append(jsmsAuditKeywordCategory.getCategoryName()).append(",");
                                }else if(j==jsmsAuditKgroupRefCategory.size()-1){
                                    categoryName =  categoryName.append(jsmsAuditKeywordCategory.getCategoryName());
                                }else{
                                    categoryName = categoryName.append(jsmsAuditKeywordCategory.getCategoryName()).append(",");
                                }
                            }
                        }
                        jsmsAuditKeywordGroupDTO.setCategoryName(categoryName.toString());
                    }
                }
                list2.add(jsmsAuditKeywordGroupDTO);
            }
        }
        page.setData(list2);
        return page;
    }
}
