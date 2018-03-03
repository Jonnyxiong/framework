package com.jsmsframework.user.audit.service;


import com.jsmsframework.audit.dto.JsmsAutoTemplateDTO;
import com.jsmsframework.audit.dto.JsmsBalckTemplateDTO;
import com.jsmsframework.audit.entity.JsmsAutoBlackTemplate;
import com.jsmsframework.audit.entity.JsmsAutoTemplate;
import com.jsmsframework.audit.service.JsmsAutoBlackTemplateService;
import com.jsmsframework.audit.service.JsmsAutoTemplateService;

import com.jsmsframework.common.constant.SysConstant;
import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.dto.JxlExcel;
import com.jsmsframework.common.dto.R;
import com.jsmsframework.common.enums.*;
import com.jsmsframework.common.enums.balckAndWhiteTemplate.AutoTemplateStatus;
import com.jsmsframework.common.enums.balckAndWhiteTemplate.AutoTemplateSubmitType;
import com.jsmsframework.common.enums.balckAndWhiteTemplate.AutoTemplateType;
import com.jsmsframework.common.enums.balckAndWhiteTemplate.AutoTemplateTypeEnum;
import com.jsmsframework.common.util.*;
import com.jsmsframework.common.enums.balckAndWhiteTemplate.*;

import com.jsmsframework.user.entity.JsmsAccount;
import com.jsmsframework.user.entity.JsmsAgentInfo;
import com.jsmsframework.user.entity.JsmsUser;
import com.jsmsframework.user.service.JsmsAccountService;
import com.jsmsframework.user.service.JsmsAgentInfoService;
import com.jsmsframework.user.service.JsmsUserService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.File;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class JsmsUserAutoTemplateServiceImpl implements JsmsUserAutoTemplateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsmsUserAutoTemplateServiceImpl.class);


    @Autowired
    private JsmsAccountService jsmsAccountService;

    @Autowired
    private JsmsAutoTemplateService jsmsAutoTemplateService;

    @Autowired
    private JsmsUserService jsmsUserService;

    @Autowired
    private JsmsAgentInfoService jsmsAgentInfoService;

    @Autowired
    private JsmsAutoBlackTemplateService jsmsAutoBlackTemplateService;

    private R validataAutoTemplateBussiness(String adminId, List<List<String>> excelDataList, List<Map<String, Object>> illegalDataList,
                                            List<Map<String, Object>> legalDataList, Integer webId) {
        List<String> row = null;
        row = excelDataList.get(0);
        if (!"用户账号".equals(row.get(0)) || !"短信签名".equals(row.get(1)) || !"模板属性".equals(row.get(2))
                || !"模板类型".equals(row.get(3)) || !"模板内容".equals(row.get(4))) {
            return R.error("请使用系统提供的批量添加智能模板Excel文件进行导入");
        }
        for (int pos = 1; pos < excelDataList.size(); pos++) {
            // 得到当前数据进行验证操作
            row = excelDataList.get(pos);

            if (row != null && row.size() > 0) {
                StringBuilder errorMsg = new StringBuilder();
                String clientId = "";
                String sign = "";
                String smsType = "";
                String templateType = "";
                String content = "";
                try {
                    clientId = Objects.toString(row.get(0), "").trim();
                    sign = Objects.toString(row.get(1), "").trim();
                    smsType = Objects.toString(row.get(2), "").trim();
                    templateType = Objects.toString(row.get(3), "").trim();
                    content = Objects.toString(row.get(4), "").trim();

                } catch (Exception e) {
                    errorMsg.append("数据格式异常");
                }

                JsmsAutoTemplate jsmsAutoTemplate = new JsmsAutoTemplate();
                jsmsAutoTemplate.setClientId(clientId);
                jsmsAutoTemplate.setSign(sign);

                if ("行业".equals(smsType)) {
                    jsmsAutoTemplate.setSmsType(AutoTemplateTypeEnum.行业.getValue());
                } else if ("会员营销".equals(smsType)) {
                    jsmsAutoTemplate.setSmsType(AutoTemplateTypeEnum.会员营销.getValue());
                } else {
                }

                if ("变量模板".equals(templateType)) {
                    jsmsAutoTemplate.setTemplateType(AutoTemplateType.变量模板.getValue());
                } else if ("固定模板".equals(templateType)) {
                    jsmsAutoTemplate.setTemplateType(AutoTemplateType.固定模板.getValue());
                }

                jsmsAutoTemplate.setContent(content);
                if (webId.equals(7)) {
                    jsmsAutoTemplate.setSubmitType(AutoTemplateSubmitType.客户提交.getValue());
                } else if (webId.equals(4)) {
                    jsmsAutoTemplate.setSubmitType(AutoTemplateSubmitType.代理商提交.getValue());
                } else {
                    jsmsAutoTemplate.setSubmitType(AutoTemplateSubmitType.平台提交.getValue());
                }

                //创建人
                // jsmsAutoTemplate.setUserId(adminId);

                if (WebId.短信调度系统.getValue().equals(webId)) {
                    jsmsAutoTemplate.setAdminId(Long.parseLong(adminId));
                    jsmsAutoTemplate.setState(AutoTemplateStatus.审核通过.getValue());
                    jsmsAutoTemplate.setWebId(WebId.短信调度系统.getValue());
                } else if (WebId.运营平台.getValue().equals(webId)) {
                    jsmsAutoTemplate.setState(AutoTemplateStatus.待审核.getValue());
                    jsmsAutoTemplate.setWebId(WebId.运营平台.getValue());
                    jsmsAutoTemplate.setAdminId(Long.parseLong(adminId));
                } else if (WebId.客户端.getValue().equals(webId)) {
                    jsmsAutoTemplate.setState(AutoTemplateStatus.待审核.getValue());
                    jsmsAutoTemplate.setWebId(WebId.客户端.getValue());
                } else if (WebId.OEM代理商平台.getValue().equals(webId)) {
                    jsmsAutoTemplate.setState(AutoTemplateStatus.待审核.getValue());
                    jsmsAutoTemplate.setWebId(WebId.OEM代理商平台.getValue());
                    jsmsAutoTemplate.setAgentId(Integer.parseInt(adminId));
                } else {
                    return R.error("未知入口来源,请在调度系统、运营平台、OEM代理商平台和OEM客户端提交");
                }

                jsmsAutoTemplate.setTemplateLevel(TemplateLevel.用户级别.getValue());
                if (jsmsAccountService.getByClientId(clientId) == null) {
                    errorMsg.append("用户账号不存在");
                } else {
                    //校验模板
                    if (!jsmsAutoTemplate.getWebId().equals(WebId.短信调度系统.getValue())) {//除调度系统外的其他系统需要校验
                        R r = this.checkAutoTemplate(jsmsAutoTemplate, adminId);
                        if (r != null) {
                            errorMsg.append(r.getMsg());
                        }
                    }
                    R r = jsmsAutoTemplateService.checkAutoTemplateRightful(jsmsAutoTemplate, false);
                    if (!Objects.equals(r.getCode(), SysConstant.SUCCESS_CODE)) {
                        errorMsg.append(r.getMsg());

                    }
                }
//                R backcheck=jsmsAutoBlackTemplateService.checkBalckTemplate(backdto);
//
//
//                R r = this.checkAutoTemplate(jsmsAutoTemplate,adminId);
//                if(r != null){
//                    errorMsg.append(r.getMsg());
//                }

                String errorMsgStr = errorMsg.toString();
                Map<String, Object> jsmsAutoTemplateMap = BeanUtil.beanToMap(jsmsAutoTemplate, true);
                String smsTypeText = SmsTypeEnum.getDescByValue(jsmsAutoTemplate.getSmsType());
                jsmsAutoTemplateMap.put("smsTypeText", smsTypeText);
                jsmsAutoTemplateMap.put("templateTypeText", AutoTemplateType.getDescByValue(jsmsAutoTemplate.getTemplateType()));
                if (org.apache.commons.lang3.StringUtils.isNotBlank(errorMsgStr)) {
                    jsmsAutoTemplateMap.put("importState", "失败");
                    jsmsAutoTemplateMap.put("failReason", errorMsgStr);
                    illegalDataList.add(jsmsAutoTemplateMap);
                } else {
                    jsmsAutoTemplateMap.put("importState", "成功");
                    jsmsAutoTemplateMap.put("failReason", errorMsgStr);
                    legalDataList.add(jsmsAutoTemplateMap);
                }

            }
        }

        R r = new R();
        Map<String, Object> data = new HashMap<>();
        data.put("illegalDataList", illegalDataList);
        data.put("legalDataList", legalDataList);
        r.setData(data);
        return r;
    }


    private String insertExcelDataBatch(List<Map<String, Object>> excelDataList, String adminId, Integer webId, List<Map<String, Object>> illegalDataList) {
        try {
            for (Map<String, Object> row : excelDataList) {
                JsmsAutoTemplateDTO jsmsAutoTemplate = new JsmsAutoTemplateDTO();
                BeanUtil.mapToBean(row, jsmsAutoTemplate);
                Boolean b = jsmsAutoTemplateService.isExistAutoTemplate(jsmsAutoTemplate);
                if (b) {
                    if (webId.equals(WebId.运营平台.getValue()) || webId.equals(WebId.客户端.getValue()) || webId.equals(WebId.OEM代理商平台.getValue())) {
                        Map<String, Object> illegal = new HashMap<>();
                        if (row != null && row.size() > 0) {
                            StringBuilder errorMsg = new StringBuilder();
                            String smsType = "";
                            String templateType = "";
                            smsType = Objects.toString(row.get(2), "").trim();
                            templateType = Objects.toString(row.get(3), "").trim();
                            if ("行业".equals(jsmsAutoTemplate)) {
                                jsmsAutoTemplate.setSmsType(AutoTemplateTypeEnum.行业.getValue());
                            } else if ("会员营销".equals(smsType)) {
                                jsmsAutoTemplate.setSmsType(AutoTemplateTypeEnum.会员营销.getValue());
                            } else {
                            }

                            if ("变量模板".equals(templateType)) {
                                jsmsAutoTemplate.setTemplateType(AutoTemplateType.变量模板.getValue());
                            } else if ("固定模板".equals(templateType)) {
                                jsmsAutoTemplate.setTemplateType(AutoTemplateType.固定模板.getValue());
                            }
                        }
                        illegal = BeanUtil.beanToMap(jsmsAutoTemplate, true);
                        String smsTypeText = SmsTypeEnum.getDescByValue(jsmsAutoTemplate.getSmsType());
                        illegal.put("templateTypeText", AutoTemplateType.getDescByValue(jsmsAutoTemplate.getTemplateType()));
                        illegal.put("smsTypeText", smsTypeText);
                        illegal.put("importState", "失败");
                        illegal.put("failReason", "数据已经存在");
                        illegalDataList.add(illegal);
                    } else {
                        row.put("importState", "失败");
                        row.put("failReason", "数据已经存在");
                    }
                } else {
                    //if(WebId.短信调度系统.getValue().equals(webId)){
                    jsmsAutoTemplate.setUpdateTime(new Date());
                    // }
                    if (webId.equals(WebId.运营平台.getValue()) || webId.equals(WebId.客户端.getValue()) || webId.equals(WebId.OEM代理商平台.getValue())) {
                        jsmsAutoTemplate.setAdminId(null);//没有审核人
                    }
                    jsmsAutoTemplate.setUserId(String.valueOf(adminId));
                    jsmsAutoTemplateService.insert(jsmsAutoTemplate);
                    row.put("importState", "成功");
                }
            }

        } catch (Exception e) {
            LOGGER.debug("批量导入智能模板时系统错误", e);
            return "批量导入智能模板时系统错误";
        }

        return null;
    }

    private boolean generateImportResultExcel(List<Map<String, Object>> dataList, String adminId, String tempFileSavePath) {
        String filePath = tempFileSavePath + "/import" + "/批量添加智能模板结果-userid-" + adminId
                + ".xls";
        File dir = new File(tempFileSavePath + "/import");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        JxlExcel excel = new JxlExcel();
        excel.setFilePath(filePath);
        excel.setShowPage(false);
        excel.setTitle("批量添加智能模板结果列表");
        excel.addHeader(20, "用户账号", "clientId");
        excel.addHeader(20, "短信签名", "sign");
        excel.addHeader(20, "模板属性", "smsTypeText");
        excel.addHeader(20, "模板类型", "templateTypeText");
        excel.addHeader(100, "模板内容", "content");
        excel.addHeader(20, "是否添加成功", "importState");
        excel.addHeader(100, "备注", "failReason");
        excel.setDataList(dataList);
        return JxlExcelUtils.exportExcel(excel);
    }



    /**
     * 检查自动模版的正确性
     *
     * @param autoTemplate
     * @return
     */
    @Override
    public R checkAutoTemplate(JsmsAutoTemplate autoTemplate, String adminId) {
        JsmsAccount jsa = jsmsAccountService.getByClientId(autoTemplate.getClientId());
        if (StringUtils.isBlank(autoTemplate.getClientId()) || jsa == null) {
            return R.error("用户账号不存在");
        }
        if (!autoTemplate.getWebId().equals(WebId.客户端.getValue())) {
            if (autoTemplate.getWebId().equals(WebId.OEM代理商平台.getValue())) {
                if (null == jsa.getAgentId()) {
                    return R.error("用户账号无归属代理商");
                }
            } else {
                if (null == jsa.getBelongSale()) {
                    return R.error("用户账号无归属销售");
                }
            }
            if (jsmsAccountService.checkAccountIsCancel(jsa.getClientid())) {
                return R.error("用户账号已经注销");
            }
        }
        if (autoTemplate.getWebId().equals(WebId.客户端.getValue())) {
            if (!autoTemplate.getClientId().equals(adminId)) {
                return R.error("导入智能模板的用户账户错误");
            }
        }

        if (autoTemplate.getWebId() == WebId.运营平台.getValue()) {
            if (jsa.getBelongSale().longValue() != autoTemplate.getAdminId().longValue()) {
                return R.error("用户账号不属于该销售");
            }
        }
        if (autoTemplate.getWebId() == WebId.OEM代理商平台.getValue()) {
            if (jsa.getAgentId().longValue() != autoTemplate.getAgentId().longValue()) {
                return R.error("用户账号不属于该代理商");
            }
        }
        JsmsAutoTemplateDTO dto = new JsmsAutoTemplateDTO();
        BeanUtil.copyProperties(autoTemplate, dto);
        Boolean b = jsmsAutoTemplateService.isExistAutoTemplate(dto);
        if (b) {
            return R.error("数据已经存在");
        }
        return null;
    }

    @Override
    public R addAutoTemplateBatch(File uploadFile, String uploadContentType, String adminId, String tempFileSavePath, Integer webId) {
        R r = new R();
        String fileAbsPath = null;
        Map<String, Object> data = new HashMap<>();
        if (webId.equals(WebId.运营平台.getValue()) || webId.equals(WebId.OEM代理商平台.getValue()) || webId.equals(WebId.客户端.getValue())) {
            fileAbsPath = tempFileSavePath;
        } else {
            String timeStamp = new DateTime().toString("yyyyMMddHHmmss");
            String fileName = "批量添加智能模板" + timeStamp + ".xls";
            fileAbsPath = tempFileSavePath + "/" + fileName;
            LOGGER.debug("批量导入添加智能模板：文件路径 = {}", fileAbsPath);

            // 校验Excel格式、大小
            String msg = JxlExcelUtils.validateExcel(uploadFile, uploadContentType);
            if (msg != null) {
                return r.error(msg);
            }
            FileUtils.upload(tempFileSavePath, fileName, uploadFile);
        }
        // 获得Excel文件中的数据
        boolean isFilterBlankRow = true;
        List<List<String>> excelDataList = new ArrayList<>();
        try {
            excelDataList = JxlExcelUtils.importExcel(fileAbsPath, isFilterBlankRow);
        } catch (Exception e) {
            LOGGER.error("导入Excel时发生错误，e = {}", e);
            return r.error("导入文件格式错误，目前支持.xls格式(97-2003)，请使用模板");
        }
        FileUtils.delete(fileAbsPath);

        if (excelDataList.size() > 1000) {
            return r.error("您选择的excel中数据记录大于1000条，请您拆分后上传");
        }

        List<Map<String, Object>> illegalDataList = new ArrayList<>(); // Excel中非法数据
        List<Map<String, Object>> legalDataList = new ArrayList<>(); // Excel中合法数据
        R validateR = null;
        if (excelDataList != null && excelDataList.size() > 1) {
            validateR = validataAutoTemplateBussiness(adminId, excelDataList, illegalDataList, legalDataList, webId);
        } else {
            return r.error("excel中没有数据");
        }
        if (validateR.getCode().equals(500)) {
            return validateR;
        }

        String insertError = this.insertExcelDataBatch(legalDataList, adminId, webId, illegalDataList);
        if (insertError == null) {
            List<Map<String, Object>> importResultList = new ArrayList<>();
            if (webId.equals(WebId.短信调度系统.getValue())) {
                if (legalDataList.size() > 0) {
                    importResultList.addAll(legalDataList);
                }
            }
            if (illegalDataList.size() > 0) {
                importResultList.addAll(illegalDataList);
            }
            generateImportResultExcel(importResultList, adminId, tempFileSavePath);
            if (webId.equals(WebId.运营平台.getValue()) || webId.equals(WebId.客户端.getValue()) || webId.equals(WebId.OEM代理商平台.getValue())) {
                data.put("importTotal", excelDataList.size() - 1);
                data.put("importSuccess", excelDataList.size() - 1 - illegalDataList.size());
                data.put("importFall", illegalDataList.size());
                r.setData(data);
            }
            r.ok("导入完成，");
            return r;

        } else {
            return r.error(insertError);
        }

    }

    @Override
    public JsmsPage findListOfAutoTemplate(JsmsPage page, Integer webid, AutoTemplateLevel templateLevel) {
        return findListOfAutoTemplate(page, webid, TemplateLevel.getInstanceByValue(templateLevel.getValue()));
    }

    @Override
    public JsmsPage findListOfAutoTemplate(JsmsPage page, Integer webid) {

        return findListOfAutoTemplate(page, webid,TemplateLevel.用户级别);
    }

    @Override
    public JsmsPage findListOfAutoTemplate(JsmsPage page, Integer webid, TemplateLevel templateLevel) {
        Set<String> userIds = new HashSet<>();
        Set<String> adminIds = new HashSet<>();
        JsmsAccount jsmsAccount = null;
        JsmsAgentInfo jsmsAgentInfo = null;
        String adminName = "";
        String userName = "";
        int submitType = -1;
        if (StringUtils.isNotBlank(String.valueOf(page.getParams().get("submitType"))) && page.getParams().get("submitType") != null) {
            submitType = Integer.parseInt(String.valueOf(page.getParams().get("submitType")));//提交来源
        }
        if (page.getParams().get("userName") != null) {
            userName = String.valueOf(page.getParams().get("userName"));//创建者
        }
        if (page.getParams().get("adminName") != null) {
            adminName = String.valueOf(page.getParams().get("adminName"));//审核人
        }
        if (StringUtils.isNotBlank(userName) && submitType != -1) {
            if (submitType == AutoTemplateSubmitType.客户提交.getValue().intValue()) {//客户提交
                List<JsmsAccount> jsmsAccounts = jsmsAccountService.getByName(userName);
                if (jsmsAccounts.size() > 0) {
                    for (int i = 0; i < jsmsAccounts.size(); i++) {
                        userIds.add(jsmsAccounts.get(i).getClientid());
                    }
                } else {
                    userIds.add("-1");
                }
                page.getParams().put("userIds", userIds);
            } else if (submitType == AutoTemplateSubmitType.代理商提交.getValue().intValue()) {//代理商提交
                List<JsmsAgentInfo> jsmsAgentInfos = jsmsAgentInfoService.getByAgentName(userName);
                if (jsmsAgentInfos.size() > 0) {
                    for (int i = 0; i < jsmsAgentInfos.size(); i++) {
                        userIds.add(String.valueOf(jsmsAgentInfos.get(i).getAgentId()));
                    }
                } else {
                    userIds.add("-1");
                }
                page.getParams().put("userIds", userIds);
            } else if (submitType == AutoTemplateSubmitType.平台提交.getValue().intValue()) {//平台提交
                List<JsmsUser> jsmsUser = jsmsUserService.getByRealname(userName);
                if (jsmsUser.size() > 0) {
                    for (int i = 0; i < jsmsUser.size(); i++) {
                        userIds.add(String.valueOf(jsmsUser.get(i).getId()));
                    }
                } else {
                    userIds.add("-1");
                }
                page.getParams().put("userIds", userIds);
            }
        }
        if (StringUtils.isNotBlank(adminName) && adminName != null) {//判断查询模板的的参数中是否包含审核人
            List<JsmsUser> jsmsUser = jsmsUserService.getByRealname(adminName);
            if (jsmsUser.size() > 0) {
                for (int i = 0; i < jsmsUser.size(); i++) {
                    adminIds.add(String.valueOf(jsmsUser.get(i).getId()));
                }
            } else {
                adminIds.add("-1");
            }
            page.getParams().put("adminIds", adminIds);
        }

        if (webid.intValue() == (WebId.运营平台.getValue().intValue())) {//运营平台
            page.getParams().put("templateLevel", templateLevel.getValue());
            page = jsmsAutoTemplateService.queryPageList(page);
        } else if (webid.intValue() == (WebId.短信调度系统.getValue().intValue())) {//调度系统
            page = jsmsAutoTemplateService.findList(page);
        } else {
            page.getParams().put("templateLevel", templateLevel.getValue());
            return jsmsAutoTemplateService.queryPageList(page);
        }
        List<JsmsAutoTemplateDTO> list = new ArrayList<>();
        for (Object temp : page.getData()) {
            JsmsAutoTemplateDTO jsmsAutoTemplateDTO = new JsmsAutoTemplateDTO();
            BeanUtils.copyProperties(temp, jsmsAutoTemplateDTO);
            com.jsmsframework.user.entity.JsmsUser jsmsUser = jsmsUserService.getById(String.valueOf(jsmsAutoTemplateDTO.getAdminId()));
            if (jsmsUser != null) {
                jsmsAutoTemplateDTO.setAdminName(jsmsUser.getRealname());
            }
            if (jsmsAutoTemplateDTO.getSubmitType().equals(AutoTemplateSubmitType.客户提交.getValue())) {
                jsmsAccount = jsmsAccountService.getByClientId(String.valueOf(jsmsAutoTemplateDTO.getUserId()));
                if (jsmsAccount != null) {
                    jsmsAutoTemplateDTO.setUserName(jsmsAccount.getName());
                }
            } else if (jsmsAutoTemplateDTO.getSubmitType().equals(AutoTemplateSubmitType.代理商提交.getValue())) {
                jsmsAgentInfo = jsmsAgentInfoService.getByAgentId(Integer.parseInt(String.valueOf(jsmsAutoTemplateDTO.getUserId())));
                if (jsmsAgentInfo != null) {
                    jsmsAutoTemplateDTO.setUserName(jsmsAgentInfo.getAgentName());
                }
            } else if (jsmsAutoTemplateDTO.getSubmitType().equals(AutoTemplateSubmitType.平台提交.getValue())) {
                jsmsUser = jsmsUserService.getById(jsmsAutoTemplateDTO.getUserId());
                if (jsmsUser != null) {
                    jsmsAutoTemplateDTO.setUserName(jsmsUser.getRealname());
                }
            }

            list.add(jsmsAutoTemplateDTO);
        }
        page.setData(list);
        return page;
    }


}
