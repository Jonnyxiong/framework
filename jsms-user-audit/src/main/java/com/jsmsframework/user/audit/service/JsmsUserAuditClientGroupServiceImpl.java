package com.jsmsframework.user.audit.service;

import com.jsmsframework.audit.dto.JsmsAuditClientGroupDTO;
import com.jsmsframework.audit.entity.*;
import com.jsmsframework.audit.mapper.JsmsAuditClientGroupMapper;
import com.jsmsframework.audit.service.JsmsAuditCgroupRefClientService;
import com.jsmsframework.audit.service.JsmsAuditKeywordCategoryService;
import com.jsmsframework.audit.service.JsmsAuditKeywordGroupService;
import com.jsmsframework.audit.service.JsmsAuditKgroupRefCategoryService;
import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.user.entity.JsmsAccount;
import com.jsmsframework.user.entity.JsmsUser;
import com.jsmsframework.user.service.JsmsAccountService;
import com.jsmsframework.user.service.JsmsUserService;
import com.ucpaas.sms.common.util.StringUtils;
import com.ucpaas.sms.model.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by xiongfenglin on 2017/10/31.
 *
 * @author: xiongfenglin
 */
@Service
public class JsmsUserAuditClientGroupServiceImpl implements JsmsUserAuditClientGroupService{
    @Autowired
    private JsmsAuditClientGroupMapper auditClientGroupMapper;
    @Autowired
    private JsmsUserService jsmsUserService;
    @Autowired
    private JsmsAuditCgroupRefClientService jsmsAuditCgroupRefClientService;
    @Autowired
    private JsmsAuditKeywordGroupService jsmsAuditKeywordGroupService;
    @Autowired
    private JsmsAuditKgroupRefCategoryService jsmsAuditKgroupRefCategoryService;
    @Autowired
    private JsmsAuditKeywordCategoryService jsmsAuditKeywordCategoryService;
    @Autowired
    private JsmsAccountService jsmsAccountService;
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAuditClientGroup> jsmsAuditClientGroupList = this.auditClientGroupMapper.queryList(page);
        List<JsmsAuditClientGroupDTO> jsmsAuditClientGroupDTOList = new ArrayList<>();
        JsmsUser jsmsUser = new JsmsUser();
        List<JsmsAuditCgroupRefClient> jsmsAuditCgroupRefClient = new ArrayList<>();
        JsmsAuditKeywordGroup JsmsAuditKeywordGroup = new JsmsAuditKeywordGroup();
        JsmsAuditKeywordCategory jsmsAuditKeywordCategory = new JsmsAuditKeywordCategory();
        List<JsmsAuditKgroupRefCategory> jsmsAuditKgroupRefCategory =  new ArrayList<>();
        JsmsAccount jsmsAccount = new JsmsAccount();
        StringBuilder name = new StringBuilder();
        StringBuilder categoryName = new StringBuilder();
        if(jsmsAuditClientGroupList!=null&&jsmsAuditClientGroupList.size()!=0){
            for(int i =0;i<jsmsAuditClientGroupList.size();i++){
                name.delete(0,name.length());
                categoryName.delete(0,categoryName.length());
                JsmsAuditClientGroupDTO jsmsAuditClientGroupDTO = new JsmsAuditClientGroupDTO();
                BeanUtils.copyProperties(jsmsAuditClientGroupList.get(i) , jsmsAuditClientGroupDTO);
                jsmsUser = jsmsUserService.getById(String.valueOf(jsmsAuditClientGroupDTO.getOperator()));
                if(jsmsUser!=null){
                    jsmsAuditClientGroupDTO.setUserName(jsmsUser.getRealname());
                }
                if(jsmsAuditClientGroupList.get(i).getCgroupId()!=null){
                    jsmsAuditCgroupRefClient = jsmsAuditCgroupRefClientService.getCgroupId(jsmsAuditClientGroupList.get(i).getCgroupId());
                   if(jsmsAuditCgroupRefClient!=null && jsmsAuditCgroupRefClient.size()>0){
                       for(int k =0;k<jsmsAuditCgroupRefClient.size();k++){
                           if(StringUtils.isNotBlank(jsmsAuditCgroupRefClient.get(k).getClientid())){
                               jsmsAccount = jsmsAccountService.getByClientId(jsmsAuditCgroupRefClient.get(k).getClientid());
                               if(jsmsAccount!=null){
                               if(k==0&&k!=jsmsAuditCgroupRefClient.size()-1){
                                   name = name.append(jsmsAccount.getClientid()+"-"+jsmsAccount.getName()).append(",");
                               }else if(k==jsmsAuditCgroupRefClient.size()-1){
                                   name =  name.append(jsmsAccount.getClientid()+"-"+jsmsAccount.getName());
                               }else{
                                   name = name.append(jsmsAccount.getClientid()+"-"+jsmsAccount.getName()).append(",");
                               }
                               }
                           }
                       }
                       jsmsAuditClientGroupDTO.setName(name.toString());
                   }
                }
                if(jsmsAuditClientGroupList.get(i).getKgroupId()!=null){
                    JsmsAuditKeywordGroup = jsmsAuditKeywordGroupService.getByKgroupId(jsmsAuditClientGroupList.get(i).getKgroupId());
                    if(JsmsAuditKeywordGroup!=null){
                        jsmsAuditClientGroupDTO.setKgroupName(JsmsAuditKeywordGroup.getKgroupName());
                        jsmsAuditKgroupRefCategory = jsmsAuditKgroupRefCategoryService.getBykgroupId(JsmsAuditKeywordGroup.getKgroupId());
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
                            jsmsAuditClientGroupDTO.setCategoryName(categoryName.toString());
                        }
                    }
                }
                jsmsAuditClientGroupDTOList.add(jsmsAuditClientGroupDTO);
            }
        }
        page.setData(jsmsAuditClientGroupDTOList);
        return page;
    }

    @Override
    public int configurationkeyword(Map<String, String> params,JsmsAuditClientGroup jsmsAuditClientGroup) {
        int updateFlag;
        int deteleFlag =0;
        int insertFlag =0;
        int totalinsertFlag =0;
        updateFlag = auditClientGroupMapper.update(jsmsAuditClientGroup);
        JsmsAuditCgroupRefClient jsmsAuditCgroupRefClient = new JsmsAuditCgroupRefClient();
        if(updateFlag>0){
            if(jsmsAuditClientGroup.getCgroupId()!=null){
                deteleFlag = jsmsAuditCgroupRefClientService.deteleJsmsAuditCgroupRefClient(jsmsAuditClientGroup.getCgroupId());
            }
                if (params.size() > 0) {
                    params.remove("cgroupId");
                    params.remove("kgroupId");
                    for (int i = 0; i < params.size(); i++) {
                        jsmsAuditCgroupRefClient.setCgroupId(jsmsAuditClientGroup.getCgroupId());
                        jsmsAuditCgroupRefClient.setClientid(params.get("list[" + i + "][clientid]"));
                        insertFlag = jsmsAuditCgroupRefClientService.insert(jsmsAuditCgroupRefClient);
                        if(insertFlag>0){
                            totalinsertFlag+=1;
                        }
                    }
                }
        }
        if(updateFlag>0&&totalinsertFlag==params.size()){
            return 1;
        }else{
            return 0;
        }

    }
}
