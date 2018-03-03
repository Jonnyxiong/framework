package com.jsmsframework.audit.service;

import java.util.*;

import com.jsmsframework.audit.dto.JsmsAuditKeywordGroupDTO;
import com.jsmsframework.audit.entity.*;
import com.jsmsframework.audit.mapper.JsmsAuditClientGroupMapper;
import com.jsmsframework.audit.mapper.JsmsAuditKgroupRefCategoryMapper;
import com.jsmsframework.common.enums.audit.AuditClientGroupIsDefault;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.audit.mapper.JsmsAuditKeywordGroupMapper;
import com.jsmsframework.audit.service.JsmsAuditKeywordGroupService;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @author huangwenjie
 * @description 审核关键字分组表
 * @date 2017-10-31
 */
@Service
public class JsmsAuditKeywordGroupServiceImpl implements JsmsAuditKeywordGroupService {

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
    private JsmsAuditCgroupRefClientService jsmsAuditCgroupRefClientService;
    @Autowired
    private JsmsAuditClientGroupService jsmsAuditClientGroupService;

    @Override
    @Transactional
    public int insert(JsmsAuditKeywordGroup model, List<String> categoryNameList) {
        Date now = new Date();
        int flag;
        int insertFlag;
        int totalinsertFlag = 0;
        int count;
        Map<String, Object> params = new HashMap<>();
        JsmsAuditKgroupRefCategory jsmsAuditKgroupRefCategory = new JsmsAuditKgroupRefCategory();
        JsmsAuditKeywordGroup jsmsAuditKeywordGroup = new JsmsAuditKeywordGroup();
        JsmsAuditClientGroup jsmsAuditClientGroup = new JsmsAuditClientGroup();
        count = auditKeywordGroupMapper.count(params);
        if (count == 0) {
            model.setIsDefault(1);
        } else {
            model.setIsDefault(0);
        }
        flag = this.auditKeywordGroupMapper.insert(model);
        if (flag > 0) {
            jsmsAuditKeywordGroup = auditKeywordGroupMapper.getJsmsAuditKeywordGroup(model.getKgroupName());
            if (categoryNameList != null && categoryNameList.size() > 0 && jsmsAuditKeywordGroup != null) {
                for (int i = 0; i < categoryNameList.size(); i++) {
                    jsmsAuditKgroupRefCategory.setKgroupId(jsmsAuditKeywordGroup.getKgroupId());
                    jsmsAuditKgroupRefCategory.setCategoryId(Integer.parseInt(categoryNameList.get(i)));
                    jsmsAuditKgroupRefCategory.setUpdateDate(now);
                    jsmsAuditKgroupRefCategory.setSort(i);
                    insertFlag = jsmsAuditKgroupRefCategoryService.insert(jsmsAuditKgroupRefCategory);
                    if (insertFlag > 0) {
                        totalinsertFlag += 1;
                    }
                }
            }
        }
        if (flag > 0 && totalinsertFlag == categoryNameList.size()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    @Transactional
    public int insertBatch(List<JsmsAuditKeywordGroup> modelList) {
        return this.auditKeywordGroupMapper.insertBatch(modelList);
    }

    @Override
    @Transactional
    public int update(JsmsAuditKeywordGroup model, List<String> categoryNameList) {
        Date now = new Date();
        JsmsAuditKgroupRefCategory jsmsAuditKgroupRefCategory = new JsmsAuditKgroupRefCategory();
        int updataFalg;
        int insertFlag;
        int totalinsertFlag = 0;
        int deteleFlag;
        List<JsmsAuditKgroupRefCategory> list = new ArrayList<JsmsAuditKgroupRefCategory>();
        JsmsAuditKeywordGroup old = this.auditKeywordGroupMapper.getByKgroupId(model.getKgroupId());
        if (old == null) {
            return 0;
        } else {
            updataFalg = this.auditKeywordGroupMapper.update(model);
            if (updataFalg > 0) {
                list = jsmsAuditKgroupRefCategoryMapper.getBykgroupId(model.getKgroupId());
                if (list.size() > 0) {
                    deteleFlag = jsmsAuditKgroupRefCategoryMapper.deteleJsmsAuditKgroupRefCategory(model.getKgroupId());
                    if (deteleFlag > 0) {
                        if (categoryNameList != null && categoryNameList.size() > 0) {
                            for (int i = 0; i < categoryNameList.size(); i++) {
                                jsmsAuditKgroupRefCategory.setKgroupId(model.getKgroupId());
                                jsmsAuditKgroupRefCategory.setCategoryId(Integer.parseInt(categoryNameList.get(i)));
                                jsmsAuditKgroupRefCategory.setUpdateDate(now);
                                jsmsAuditKgroupRefCategory.setSort(i);
                                insertFlag = jsmsAuditKgroupRefCategoryService.insert(jsmsAuditKgroupRefCategory);
                                if (insertFlag > 0) {
                                    totalinsertFlag += 1;
                                }
                            }
                        }
                    }
                } else {
                    if (categoryNameList != null && categoryNameList.size() > 0) {
                        for (int i = 0; i < categoryNameList.size(); i++) {
                            jsmsAuditKgroupRefCategory.setKgroupId(model.getKgroupId());
                            jsmsAuditKgroupRefCategory.setCategoryId(Integer.parseInt(categoryNameList.get(i)));
                            jsmsAuditKgroupRefCategory.setSort(i);
                            jsmsAuditKgroupRefCategory.setUpdateDate(now);
                            insertFlag = jsmsAuditKgroupRefCategoryService.insert(jsmsAuditKgroupRefCategory);
                            if (insertFlag > 0) {
                                totalinsertFlag += 1;
                            }
                        }
                    }
                }
            }
        }
        if (totalinsertFlag == categoryNameList.size() && updataFalg > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    @Transactional
    public int updateSelective(JsmsAuditKeywordGroup model) {
        JsmsAuditKeywordGroup old = this.auditKeywordGroupMapper.getByKgroupId(model.getKgroupId());
        if (old != null)
            return this.auditKeywordGroupMapper.updateSelective(model);
        return 0;
    }

    @Override
    @Transactional
    public JsmsAuditKeywordGroup getByKgroupId(Integer kgroupId) {
        JsmsAuditKeywordGroup model = this.auditKeywordGroupMapper.getByKgroupId(kgroupId);
        return model;
    }

    @Override
    @Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAuditKeywordGroup> list = this.auditKeywordGroupMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    @Transactional
    public int count(Map<String, Object> params) {
        return this.auditKeywordGroupMapper.count(params);
    }

    @Override
    public int checkKgroupName(String kgroupName, Integer kgroupId) {
        return auditKeywordGroupMapper.checkKgroupName(kgroupName, kgroupId);
    }

    @Override
    public int checkKgroupNameOfInsert(String kgroupName) {
        return auditKeywordGroupMapper.checkKgroupNameOfInsert(kgroupName);
    }

    @Override
    public int deteleJsmsAuditKeywordGroup(Integer kgroupId) {
        int deteleFlag = 0;
        int unboundFlag = 0;
        deteleFlag = auditKeywordGroupMapper.deteleJsmsAuditKeywordGroup(kgroupId);
        if (deteleFlag > 0) {
            //删除用户组要解除绑定的分类
            unboundFlag = jsmsAuditKgroupRefCategoryMapper.deteleJsmsAuditKgroupRefCategory(kgroupId);
        }
        if (deteleFlag > 0 && deteleFlag > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public List<JsmsAuditKeywordGroup> getList() {
        return auditKeywordGroupMapper.getList();
    }

    @Override
    public List<JsmsAuditKeywordGroup> getByRefCategoryId(Integer categoryId) {
        List<JsmsAuditKgroupRefCategory> refs = jsmsAuditKgroupRefCategoryService.getByCategoryId(categoryId);
        if (refs == null || refs.isEmpty()) {
            return null;
        }
        List<Integer> kgroupIds = new ArrayList<>();
        for (JsmsAuditKgroupRefCategory ref : refs) {
            kgroupIds.add(ref.getKgroupId());
        }
        Map parmas = new HashMap();
        parmas.put("kgroupIds", kgroupIds);

        return auditKeywordGroupMapper.findList(parmas);
    }

    @Override
    public List<String> getRefClientIdsByGroupId(Integer groupId) {
        List<String> clientIds = new ArrayList<>();

        List<JsmsAuditClientGroup> jsmsAuditClientGroups = jsmsAuditClientGroupService.getByKgroupId(groupId);
        for(JsmsAuditClientGroup clientGroup:jsmsAuditClientGroups) {
            if(AuditClientGroupIsDefault.默认组.getValue().equals(clientGroup.getIsDefault())){ //默认用户组是没有用户的
                if(!clientIds.contains("*"))
                    clientIds.add("*");
            }else{
                List<JsmsAuditCgroupRefClient> jsmsAuditCgroupRefClients = jsmsAuditCgroupRefClientService.getCgroupId(clientGroup.getCgroupId());
                for (JsmsAuditCgroupRefClient ref : jsmsAuditCgroupRefClients) {
                    clientIds.add(ref.getClientid());
                }
            }
        }
        return clientIds;
    }
}
