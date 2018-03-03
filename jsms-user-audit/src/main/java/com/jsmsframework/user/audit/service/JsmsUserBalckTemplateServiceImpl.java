package com.jsmsframework.user.audit.service;

import com.jsmsframework.audit.dto.JsmsBalckTemplateDTO;
import com.jsmsframework.audit.entity.JsmsAutoBlackTemplate;
import com.jsmsframework.audit.entity.JsmsAutoTemplate;
import com.jsmsframework.audit.service.JsmsAutoBlackTemplateService;
import com.jsmsframework.common.constant.SysConstant;
import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.dto.JxlExcel;
import com.jsmsframework.common.dto.R;
import com.jsmsframework.common.enums.SmsTypeEnum;
import com.jsmsframework.common.enums.WebId;
import com.jsmsframework.common.enums.balckAndWhiteTemplate.*;
import com.jsmsframework.common.util.BeanUtil;
import com.jsmsframework.common.util.FileUtils;
import com.jsmsframework.common.util.JsonUtil;
import com.jsmsframework.common.util.JxlExcelUtils;
import com.jsmsframework.user.audit.exception.JsmsUserBalckTemplateException;
import com.jsmsframework.user.entity.JsmsAccount;
import com.jsmsframework.user.entity.JsmsAgentInfo;
import com.jsmsframework.user.service.JsmsAccountService;
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

/**
 * Created by Don on 2017/12/8.
 */
@Service
public class JsmsUserBalckTemplateServiceImpl implements  JsmsUserBalckTemplateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsmsUserBalckTemplateServiceImpl.class);
    @Autowired
    private JsmsAutoBlackTemplateService jsmsAutoBlackTemplateService;
    @Autowired
    private JsmsUserService jsmsUserService;
    @Autowired
    private JsmsAccountService jsmsAccountService;

    @Override
    public JsmsPage findListOfBalckTemplate(JsmsPage<JsmsAutoBlackTemplate> page) {

        //调度系统
        JsmsPage   jpage =jsmsAutoBlackTemplateService.queryList(page);

        List<JsmsBalckTemplateDTO> list = new ArrayList<>();
        for (Object temp : page.getData()) {
            JsmsBalckTemplateDTO balckTemplate = new JsmsBalckTemplateDTO();
            BeanUtils.copyProperties(temp , balckTemplate);
            com.jsmsframework.user.entity.JsmsUser jsmsUser = jsmsUserService.getById(String.valueOf(balckTemplate.getUserId()));
            if(jsmsUser != null){
                balckTemplate.setUserName(jsmsUser.getRealname());
            }
            list.add(balckTemplate);
        }
        jpage.setData(list);
        return jpage;

    }






    @Override
    @Transactional("message")
    public R addBalckTemplateBatch(File uploadFile, String uploadContentType, String adminId, String tempFileSavePath) {
    //    R r = new R();
        String fileAbsPath = null;
        Map<String, Object> data = new HashMap<>();

            String timeStamp = new DateTime().toString("yyyyMMddHHmmss");
            String fileName = "批量添加智能模板" + timeStamp + ".xls";
            fileAbsPath = tempFileSavePath + "/" + fileName;
            LOGGER.debug("批量导入添加智能模板：文件路径 = {}", fileAbsPath);

            // 校验Excel格式、大小
            String msg = JxlExcelUtils.validateExcel(uploadFile, uploadContentType);
            if(msg != null){
                return R.error(msg);
            }
            FileUtils.upload(tempFileSavePath, fileName, uploadFile);

        // 获得Excel文件中的数据
        boolean isFilterBlankRow = true;
        List<List<String>> excelDataList = new ArrayList<>();
        try {
            excelDataList = JxlExcelUtils.importExcel(fileAbsPath, isFilterBlankRow);
        }catch (Exception e){
            LOGGER.error("导入Excel时发生错误，e = {}", e);
            return R.error("导入文件格式错误，目前支持.xls格式(97-2003)，请使用模板");
        }
        FileUtils.delete(fileAbsPath);

        if (excelDataList.size() > 1000) {
            return R.error("您选择的excel中数据记录大于1000条，请您拆分后上传");
        }

        List<Map<String, Object>> illegalDataList = new ArrayList<>(); // Excel中非法数据
        List<Map<String, Object>> legalDataList = new ArrayList<>(); // Excel中合法数据
        R validateR = null;
        if (excelDataList != null && excelDataList.size() > 1) {
            validateR = validataBalckTemplateRightful(adminId, excelDataList, illegalDataList, legalDataList);
        } else {
            return R.error("excel中没有数据");
        }
        if(validateR.getCode().equals(500)){
            return validateR;
        }

        R insertError = this.insertExcelDataBatch(legalDataList,adminId);
        if(Objects.equals(insertError.getCode(),SysConstant.SUCCESS_CODE)){
            List<Map<String, Object>> importResultList = new ArrayList<>();

            if(legalDataList.size() > 0){
                    importResultList.addAll(legalDataList);
            }

            if(illegalDataList.size() > 0){
                importResultList.addAll(illegalDataList);
            }
            generateImportResultExcel(importResultList, adminId, tempFileSavePath);

            data.put("importTotal", excelDataList.size() - 1);
            data.put("importSuccess", excelDataList.size() - 1 - illegalDataList.size());
            data.put("importFall", illegalDataList.size());
//            r.setData(data);
//
//            r.ok("导入完成，");
            return R.ok("导入完成，",data);

        }else{
            return R.error(insertError.getMsg());
        }
    }


    private R validataBalckTemplateRightful(String adminId, List<List<String>> excelDataList, List<Map<String, Object>> illegalDataList,
                                            List<Map<String, Object>> legalDataList){
        List<String> row = null;
        row = excelDataList.get(0);
        if(!"用户账号".equals(row.get(0)) || !"短信签名".equals(row.get(1)) || !"模板属性".equals(row.get(2))
                || !"模板类型".equals(row.get(3)) || !"模板内容".equals(row.get(4))){
            return R.error("请使用系统提供的批量添加黑模板Excel文件进行导入");
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

                JsmsBalckTemplateDTO balckTemplate = new JsmsBalckTemplateDTO();
                balckTemplate.setClientId(clientId);
                balckTemplate.setSign(sign);

                if(Objects.equals(AutoTemplateTypeEnum.行业.getDesc(),smsType)) {
                    balckTemplate.setSmsType(AutoTemplateTypeEnum.行业.getValue());
                }else if("会员营销".equals(smsType)){
                    balckTemplate.setSmsType(AutoTemplateTypeEnum.会员营销.getValue());
                }else{
                }

                if("变量模板".equals(templateType)){
                    balckTemplate.setTemplateType(AutoTemplateType.变量模板.getValue());
                }else if("固定模板".equals(templateType)){
                    balckTemplate.setTemplateType(AutoTemplateType.固定模板.getValue());
                }

                balckTemplate.setContent(content);
                balckTemplate.setUserId(adminId);
                balckTemplate.setState(BalckTemplateState.启用.getValue());
                balckTemplate.setTemplateLevel(TemplateLevel.用户级别.getValue());
                if(jsmsAccountService.getByClientId(clientId)==null){
                    errorMsg.append("用户账号不存在");
                }else {
                    R r = jsmsAutoBlackTemplateService.checkBalckTemplate(balckTemplate);
                    if(Objects.equals(r.getCode(), SysConstant.FAIL_CODE)){
                        errorMsg.append(r.getMsg());
                    }else {
                        r=jsmsAutoBlackTemplateService.checkBalckTemplate4Rightful(balckTemplate);
                        if(Objects.equals(r.getCode(), SysConstant.FAIL_CODE)){
                            errorMsg.append(r.getMsg());
                        }
                    }
                }



                String errorMsgStr = errorMsg.toString();
                Map<String, Object> jsmsAutoTemplateMap = BeanUtil.beanToMap(balckTemplate, true);
                String smsTypeText = SmsTypeEnum.getDescByValue(balckTemplate.getSmsType());
                jsmsAutoTemplateMap.put("smsTypeText", smsTypeText);
                jsmsAutoTemplateMap.put("templateTypeText", AutoTemplateType.getDescByValue(balckTemplate.getTemplateType()));
                if(org.apache.commons.lang3.StringUtils.isNotBlank(errorMsgStr)){
                    jsmsAutoTemplateMap.put("importState", "失败");
                    jsmsAutoTemplateMap.put("failReason", errorMsgStr);
                    illegalDataList.add(jsmsAutoTemplateMap);
                }else{
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


    public R insertExcelDataBatch(List<Map<String, Object>> excelDataList,String adminId) {
        try {
            for (Map<String, Object> row : excelDataList) {
                JsmsAutoBlackTemplate jsmsBalckTemplate = new JsmsAutoBlackTemplate();
                BeanUtil.mapToBean(row, jsmsBalckTemplate);
                jsmsBalckTemplate.setUpdateTime(new Date());
                jsmsBalckTemplate.setUserId(String.valueOf(adminId));
                int add=jsmsAutoBlackTemplateService.insert(jsmsBalckTemplate);

                if(add>0){
                    row.put("importState", "成功");
                }else {
                    R.error("批量导入黑模板失败,入库不成功！");
                    throw new JsmsUserBalckTemplateException("批量导入黑模板失败,数据入库不成功,data="+ JsonUtil.toJson(jsmsBalckTemplate));
                }
            }

        } catch (Exception e) {
            LOGGER.debug("批量导入黑模板时系统错误", e);
            return R.error("批量导入黑模板时系统错误");
        }

        return R.ok("批量导入黑模板成功！");
    }

    private boolean generateImportResultExcel(List<Map<String, Object>> dataList, String adminId, String tempFileSavePath) {
        String filePath = tempFileSavePath + "/import" + "/批量添加黑模板结果-userid-" + adminId
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
        excel.setTitle("批量添加黑模板结果列表");
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

}
