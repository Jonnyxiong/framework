package com.jsmsframework.sms.send.service;


import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.enums.WebId;
import com.jsmsframework.common.enums.smsSend.TaskSubmitTypeEnum;
import com.jsmsframework.common.util.BeanUtil;
import com.jsmsframework.common.util.StringUtils;
import com.jsmsframework.sms.send.dto.JsmsSubmitProgressDTO;
import com.jsmsframework.sms.send.entity.JsmsSubmitProgress;
import com.jsmsframework.sms.send.mapper.JsmsSubmitProgressMapper;
import com.jsmsframework.user.entity.JsmsAccount;
import com.jsmsframework.user.entity.JsmsAgentInfo;
import com.jsmsframework.user.service.JsmsAccountService;
import com.jsmsframework.user.service.JsmsAgentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Don
 * @description 短信提交进度表
 * @date 2018-01-18
 */
@Service
public class JsmsSubmitProgressServiceImpl implements JsmsSubmitProgressService {

    @Autowired
    private JsmsSubmitProgressMapper submitProgressMapper;
    @Autowired
    private JsmsAccountService jsmsAccountService;
    @Autowired
    private JsmsAgentInfoService jsmsAgentInfoService;

    @Override
    public int insert(JsmsSubmitProgress model) {
        return this.submitProgressMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsSubmitProgress> modelList) {
        return this.submitProgressMapper.insertBatch(modelList);
    }

    @Override
    public int updateProgress(int id, int submitNum) {

        return this.submitProgressMapper.updateProgress(id, submitNum);
    }

    @Override
    public int finishSubmitState() {

        return this.submitProgressMapper.finishSubmitState(new Date());
    }

    @Override
    public int update(JsmsSubmitProgress model) {
        JsmsSubmitProgress old = this.submitProgressMapper.getById(model.getId());
        if (old == null) {
            return 0;
        }
        return this.submitProgressMapper.update(model);
    }

    @Override
    public int updateSelective(JsmsSubmitProgress model) {
        JsmsSubmitProgress old = this.submitProgressMapper.getById(model.getId());
        if (old != null)
            return this.submitProgressMapper.updateSelective(model);
        return 0;
    }

    @Override
    public JsmsSubmitProgress getById(Integer id) {
        JsmsSubmitProgress model = this.submitProgressMapper.getById(id);
        return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsSubmitProgress> list = this.submitProgressMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public List<JsmsSubmitProgress> findList(Map params) {
        List<JsmsSubmitProgress> list = this.submitProgressMapper.findList(params);
        return list;
    }

    @Override
    public int count(Map<String, Object> params) {
        return this.submitProgressMapper.count(params);
    }

    @Override
    public JsmsPage queryPageList(JsmsPage jsmsPage, WebId webId, String id) {
        List<JsmsSubmitProgressDTO> jsmsSubmitProgressDTOList = new ArrayList<>();
        Set<String> adminIds = new HashSet<>();
        if (jsmsPage.getParams().get("creater") != null && StringUtils.isNotBlank(jsmsPage.getParams().get("creater").toString())) {
            List<JsmsAccount> accountList = jsmsAccountService.getByName(String.valueOf(jsmsPage.getParams().get("creater")));
            List<JsmsAgentInfo> agentInfoList = jsmsAgentInfoService.getByAgentName(String.valueOf(jsmsPage.getParams().get("creater")));
            if (accountList.size() > 0) {
                for (int i = 0; i < accountList.size(); i++) {
                    adminIds.add(accountList.get(i).getClientid());
                }
            }
            if (agentInfoList.size() > 0) {
                for (int j = 0; j < agentInfoList.size(); j++) {
                    adminIds.add(String.valueOf(agentInfoList.get(j).getAgentId()));
                }
            }

            jsmsPage.getParams().put("adminIds", adminIds);
        }
        jsmsPage.getParams().put("noShowInitState", true); // 不显示初始化状态, 此时尚未作账号和余额校验
        List<JsmsSubmitProgress> origin = submitProgressMapper.queryPageList(jsmsPage);

        int rowNum = jsmsPage.getRows() * (jsmsPage.getPage() - 1);

        for (JsmsSubmitProgress progress : origin) {
            JsmsSubmitProgressDTO dto = new JsmsSubmitProgressDTO();
            BeanUtil.copyProperties(progress, dto);
            // 设置行号
            dto.setRowNum(++rowNum);
            //创建者
            if (TaskSubmitTypeEnum.代理商.getValue().equals(progress.getSubmitType())) {
                JsmsAgentInfo jsmsAgentInfo = jsmsAgentInfoService.getByAgentId(progress.getAgentId());
                dto.setCreator(jsmsAgentInfo.getAgentName());
            } else if (TaskSubmitTypeEnum.子账户.getValue().equals(progress.getSubmitType())) {
                JsmsAccount jsmsAccount = jsmsAccountService.getByClientId(progress.getClientId());
                dto.setCreator(jsmsAccount.getName());
            }
            jsmsSubmitProgressDTOList.add(dto);
        }

        jsmsPage.setData(jsmsSubmitProgressDTOList);
        jsmsPage.setParams(null);
        return jsmsPage;
    }

}
