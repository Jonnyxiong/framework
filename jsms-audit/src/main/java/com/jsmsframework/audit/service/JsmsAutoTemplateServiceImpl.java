package com.jsmsframework.audit.service;

import com.jsmsframework.audit.dto.JsmsAutoTemplateDTO;
import com.jsmsframework.audit.dto.JsmsBalckTemplateDTO;
import com.jsmsframework.audit.entity.JsmsAutoTemplate;
import com.jsmsframework.audit.mapper.JsmsAutoTemplateMapper;
import com.jsmsframework.common.constant.SysConstant;
import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.dto.JxlExcel;
import com.jsmsframework.common.dto.R;
import com.jsmsframework.common.enums.SmsTypeEnum;
import com.jsmsframework.common.enums.WebId;
import com.jsmsframework.common.enums.balckAndWhiteTemplate.*;
import com.jsmsframework.common.util.*;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description 智能模板表
 * @author huangwenjie
 * @date 2017-08-28
 */
@Service
public class JsmsAutoTemplateServiceImpl implements JsmsAutoTemplateService {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsmsAutoTemplateServiceImpl.class);

	private static final Pattern SIGN_PATTERN=Pattern.compile("^[a-zA-Z0-9\u4E00-\u9FA5]+$");

	private static final Pattern CONTENT_PATTERN=Pattern.compile("([\\u4E00-\\u9FA5]|[\\（\\）\\《\\》\\——\\；\\，\\。\\“\\”\\！\\【\\】])");

	private static final Pattern FIX_PATTERN=Pattern.compile("\\{.*?\\}");
	@Autowired
	private JsmsAutoTemplateMapper autoTemplateMapper;

	@Autowired
	private JsmsAutoBlackTemplateService jsmsAutoBlackTemplateService;



	@Override
	@Transactional
	public int insert(JsmsAutoTemplate model) {
		return this.autoTemplateMapper.insert(model);
	}

	@Override
	@Transactional
	public int insertBatch(List<JsmsAutoTemplate> modelList) {
		return this.autoTemplateMapper.insertBatch(modelList);
	}

	@Override
	@Transactional
	public int update(JsmsAutoTemplate model) {
		JsmsAutoTemplate old = this.autoTemplateMapper.getByTemplateId(model.getTemplateId());
		if (old == null) {
			return 0;
		}
		return this.autoTemplateMapper.update(model);
	}

	@Override
	@Transactional
	public int updateSelective(JsmsAutoTemplate model) {
		JsmsAutoTemplate old = this.autoTemplateMapper.getByTemplateId(model.getTemplateId());
		if (old != null)
			return this.autoTemplateMapper.updateSelective(model);
		return 0;
	}

	@Override
	@Transactional
	public JsmsAutoTemplate getByTemplateId(Integer templateId) {
		JsmsAutoTemplate model = this.autoTemplateMapper.getByTemplateId(templateId);
		return model;
	}

	@Override
	public JsmsPage queryList(JsmsPage page) {
		List<JsmsAutoTemplate> list = this.autoTemplateMapper.queryList(page);
		page.setData(list);
		return page;
	}

	@Override
	public JsmsPage findList(JsmsPage page) {
		List<JsmsAutoTemplate> list = this.autoTemplateMapper.findList(page);
		page.setData(list);
		return page;
	}

	@Override
	public int count(Map<String, Object> params) {
		return this.autoTemplateMapper.count(params);
	}

	@Override
	public int countOfOperation(Map<String, String> params) {
		return this.autoTemplateMapper.countOfOperation(params);
	}
	/**
	 * 检查自动模板的正确性
	 *
	 * @param autoTemplate
	 * @return
	 */
	@Override
	public R checkAutoTemplate(JsmsAutoTemplate autoTemplate) {
		if (autoTemplate == null){
			return R.error("智能模板不能为空");

		}
		//模板签名
		String sign = autoTemplate.getSign();
		//模板级别
		Integer templateLevel = autoTemplate.getTemplateLevel();

		if(! (Objects.equals(templateLevel,TemplateLevel.全局级别.getValue()) || Objects.equals(templateLevel,TemplateLevel.用户级别.getValue()))){
			return R.error("模板级别不能为空!");
		}

		//空签名的通用模板不校验签名
		if (!(Objects.equals(templateLevel, TemplateLevel.全局级别.getValue()) && StringUtils.isBlank(sign))){
			try {
				if (StringUtils.isBlank(sign) ) {
					return R.error("用户级别模板签名不能为空!");
				}
				if ((sign.length() < 2|| sign.length() > 12)) {
					return R.error("模板的短信签名长度必须介于 2 和 12 之间 ");
				}
				Matcher m = SIGN_PATTERN.matcher(sign);
				if (!m.matches()) {
					return R.error("签名格式为中文或者英文字母或者数字");
				}
			} catch (Throwable e) {
				LOGGER.error("验证短信签名错误", e);
				return R.error("签名格式校验错误");
			}
		}

		try {
			String content = autoTemplate.getContent();
			if(StringUtils.isBlank(content)){
				return R.error("模板内容不能为空");
			}
			//Pattern p = Pattern.compile("([\\u4E00-\\u9FA5]|[\\（\\）\\《\\》\\——\\；\\，\\。\\“\\”\\！\\【\\】])");
			Matcher m = CONTENT_PATTERN.matcher(content);
			if (m.find()) {
				if(content.indexOf("【") != -1 || content.indexOf("】") != -1 || content.indexOf("【】") != -1){
					return R.error("中文短信模板内容不能包含【,】和【】");
				}
			}
		} catch (Throwable e) {
			LOGGER.error("验证模板内容校验错误", e);
			return R.error("验证模板内容错误");
		}

		try {
//			String check = "\\{.*?\\}";
//			Pattern regex = Pattern.compile(check);
			Matcher matcher = FIX_PATTERN.matcher(autoTemplate.getContent());
			if(matcher.find()){
				if(AutoTemplateType.固定模板.getValue().equals(autoTemplate.getTemplateType())){
					return R.error("固定模板中不能包含'{}'");
				}
			}
		} catch (Throwable e) {
			LOGGER.error("验证模板内容错误", e);
			return R.error("验证模板内容错误");
		}

		if (autoTemplate.getSmsType() == null || AutoTemplateTypeEnum.getDescByValue(autoTemplate.getSmsType()) == null){
			return R.error("智能模板的模板属性不存在");
		}


		if (autoTemplate.getTemplateType() == null
				|| AutoTemplateType.getDescByValue(autoTemplate.getTemplateType()) == null){
			return R.error("智能模板的模板类型不存在");
		}


		if (StringUtils.isBlank(autoTemplate.getContent()) || autoTemplate.getContent().length() > 500){
			return R.error("智能模板的模板内容长度必须介于 1 和 500 之间");
		}


		if (autoTemplate.getSubmitType() == null
				|| AutoTemplateSubmitType.getDescByValue(autoTemplate.getSubmitType()) == null){
			return R.error("智能模板的提交类型不存在");
		}


		if (autoTemplate.getState() != null) {
			if (AutoTemplateStatus.getDescByValue(autoTemplate.getState()) == null) {
				return R.error("智能模板的状态不存在");
			}

			if (autoTemplate.getState() == AutoTemplateStatus.审核不通过.getValue()) {
				if (StringUtils.isBlank(autoTemplate.getRemark()) || autoTemplate.getRemark().length() > 100) {
					return R.error("智能模板的审核不通过原因长度必须介于 1 和 20 之间");
				}
			}
		}

		if (autoTemplate.getWebId() != null && WebId.getDescByValue(autoTemplate.getWebId()) == null){
			return R.error("WebId不存在");
		}


		if (autoTemplate.getTemplateType() == AutoTemplateType.变量模板.getValue()) {
			int index = autoTemplate.getContent().indexOf("{}");
			if (index == -1) {
				return R.error("智能模板的模板类型为变量模板，最少需要一个变量");
			}
		}
		return null;
	}

	@Override
	public boolean isExistAutoTemplate(JsmsAutoTemplateDTO autoTemplate){
		return this.autoTemplateMapper.isExist(autoTemplate) > 0;
	}

	/**
	 * 审核的时候检查
	 *
	 * @param autoTemplate
	 * @return
	 */
	@Override
	public R checkAutoTemplateAudit(JsmsAutoTemplate autoTemplate) {
		if (autoTemplate == null)
			return R.error("智能模板不能为空");

		if (autoTemplate.getTemplateId() == null)
			return R.error("智能模板的模板ID不能为空");
		if (autoTemplate.getAdminId() == null)
			return R.error("智能模板的审核人不能为空");
		if (autoTemplate.getState() == null)
			return R.error("智能模板的状态不能为空");

		return this.checkAutoTemplate(autoTemplate);
	}
	/**
	 * 检查自动模板的正确性
	 *
	 * @param autoTemplate
	 * @return
	 */
	@Override
	public R checkModifTemplate(JsmsAutoTemplate autoTemplate) {
		if (autoTemplate == null)
			return R.error("智能模板不能为空");

//		if (StringUtils.isBlank(autoTemplate.getClientId()) || jsmsAccountService.getByClientId(autoTemplate.getClientId()) == null){
//			return R.error("用户账号不存在");
//		}

		try {
			String sign = autoTemplate.getSign();
		//	Pattern p = Pattern.compile("^[a-zA-Z0-9\u4E00-\u9FA5]+$");
			Matcher m = SIGN_PATTERN.matcher(sign);
			if (!m.matches()) {
				return R.error("签名格式为中文或者英文字母或者数字");
			}
		} catch (Throwable e) {
			LOGGER.error("验证短信签名错误", e);
			return R.error("签名格式校验错误");
		}

		try {
			String content = autoTemplate.getContent();
		//	Pattern p = Pattern.compile("([\\u4E00-\\u9FA5]|[\\（\\）\\《\\》\\——\\；\\，\\。\\“\\”\\！\\【\\】])");
			Matcher m = CONTENT_PATTERN.matcher(content);
			if (m.find()) {
				if(content.indexOf("【") != -1 || content.indexOf("】") != -1 || content.indexOf("【】") != -1){
					return R.error("中文短信模板内容不能包含【,】和【】");
				}
			}
		} catch (Throwable e) {
			LOGGER.error("验证短信签名错误", e);
			return R.error("签名格式校验错误");
		}

		try {
//			String check = "\\{.*?\\}";
//			Pattern regex = Pattern.compile(check);
			Matcher matcher = FIX_PATTERN.matcher(autoTemplate.getContent());
			if(matcher.find()){
				if(AutoTemplateType.固定模板.getValue().equals(autoTemplate.getTemplateType())){
					return R.error("固定模板中不能包含'{}'");
				}
			}
		} catch (Throwable e) {
			LOGGER.error("验证模板内容错误", e);
			return R.error("验证模板内容错误");
		}
		if(StringUtils.isBlank(autoTemplate.getSign()) && Objects.equals(autoTemplate.getTemplateLevel(), TemplateLevel.全局级别.getValue())){

		}else {
			if(StringUtils.isBlank(autoTemplate.getSign()) && Objects.equals(autoTemplate.getTemplateLevel(), TemplateLevel.全局级别.getValue())){
				return R.error("用户智能模板的短信签名不能为空");
			}
			if ( autoTemplate.getSign().length() < 2
					|| autoTemplate.getSign().length() > 12){
				return R.error("智能模板的短信签名长度必须介于 2 和 12 之间 ");
			}

		}



		if (autoTemplate.getSmsType() == null || SmsTypeEnum.getDescByValue(autoTemplate.getSmsType()) == null){
			return R.error("智能模板的模板属性不存在");
		}


		if (autoTemplate.getTemplateType() == null
				|| AutoTemplateType.getDescByValue(autoTemplate.getTemplateType()) == null){
			return R.error("智能模板的模板类型不存在");
		}


		if (StringUtils.isBlank(autoTemplate.getContent()) || autoTemplate.getContent().length() > 500){
			return R.error("智能模板的模板内容长度必须介于 1 和 500 之间");
		}


		if (autoTemplate.getSubmitType() == null
				|| AutoTemplateSubmitType.getDescByValue(autoTemplate.getSubmitType()) == null){
			return R.error("智能模板的提交类型不存在");
		}


		if (autoTemplate.getState() != null) {
			if (AutoTemplateStatus.getDescByValue(autoTemplate.getState()) == null) {
				return R.error("智能模板的状态不存在");
			}
		}
		if (autoTemplate.getTemplateType() == AutoTemplateType.变量模板.getValue()) {
			int index = autoTemplate.getContent().indexOf("{}");
			if (index == -1) {
				return R.error("智能模板的模板类型为变量模板，最少需要一个变量");
			}
		}
		return null;
	}
	/**
	 * 添加自动模板
	 *
	 * @param autoTemplate
	 * @return
	 */
	@Override
	@Transactional
	public R addAutoTemplate(JsmsAutoTemplate autoTemplate) {
		//默认删除标志为false
		boolean isSureDel = false;
		return this.addAutoTemplate(autoTemplate,false);
	}

	@Override
	@Transactional
	public R delAutoTemplate(Integer templateId) {
		if (templateId == null){
			return R.error("智能模板的ID不能为空");
		}


		JsmsAutoTemplate template = this.autoTemplateMapper.getByTemplateId(templateId);

		if (template == null){
			LOGGER.debug("删除智能模板 模板ID={},不存在", templateId);
			return R.error("智能模板不存在");
		}


		if (template.getState() == AutoTemplateStatus.删除.getValue()){
			LOGGER.debug("已经删除智能模板 模板ID={},删除成功", templateId);
			return R.ok("删除成功");
		}


		LOGGER.debug("删除智能模板 模板ID={}", templateId);

		JsmsAutoTemplate waitUpdate = new JsmsAutoTemplate();
		waitUpdate.setTemplateId(templateId);
		waitUpdate.setUpdateTime(Calendar.getInstance().getTime());
		waitUpdate.setState(AutoTemplateStatus.删除.getValue());
		LOGGER.debug( "删除智能模板 模板ID={}开始", JsonUtil.toJson(templateId));
		int count = this.autoTemplateMapper.updatestate(waitUpdate);

		LOGGER.debug( "删除智能模板 模板ID={}结束,{}", JsonUtil.toJson(templateId),count > 0 ? "成功删除": "失败删除");
		return count > 0 ? R.ok("删除智能模板成功") : R.error("删除智能模板失败");
	}

	/**
	 * 审核修改智能模板
	 *
	 * @param autoTemplate
	 * @return
	 */
	@Override
	@Transactional
	public R modAutoTemplate(JsmsAutoTemplate autoTemplate,boolean isSureDel) {
        //智能模板校验
        R r = checkAutoTemplateRightful(autoTemplate, isSureDel);
        if (!Objects.equals(r.getCode(), SysConstant.SUCCESS_CODE)) {
            return r;
        }

        LOGGER.debug("--------------审核修改智能模板查重处理结束--------------");
        JsmsAutoTemplate old = this.autoTemplateMapper.getByTemplateId(autoTemplate.getTemplateId());
        if (old == null) {
            LOGGER.debug("【审核/修改智能模板】 模板ID={},不存在", autoTemplate.getTemplateId());
            return R.error("智能模板不存在");
        }

        // 未修改状态表明是修改
        boolean isMod = old.getState() == autoTemplate.getState();

        // 设置更新时间
        autoTemplate.setUpdateTime(Calendar.getInstance().getTime());

        String desc = isMod ? "修改" : "审核";
        LOGGER.debug(desc + "智能模板 {}开始", JsonUtil.toJson(autoTemplate));
        int count = this.autoTemplateMapper.updateSelective(autoTemplate);
        LOGGER.debug(desc + "智能模板 {}结束,{}", JsonUtil.toJson(autoTemplate), count > 0 ? desc + "智能模板成功" : desc + "智能模板失败");
        return count > 0 ? R.ok(desc + "智能模板成功") : R.error(desc + "智能模板失败");
    }

    /**
	 * 修改运营平台智能模板
	 *
	 * @param autoTemplate
	 * @return
	 */
	@Override
	@Transactional
	public R modifyTemplate(JsmsAutoTemplate autoTemplate) {
		int count = 0;
		R r = this.checkAutoTemplateRightful(autoTemplate,false);
		if (!Objects.equals(r.getCode(), SysConstant.SUCCESS_CODE)) {
			return r;
		}

		JsmsAutoTemplateDTO queryExist = new JsmsAutoTemplateDTO();
		queryExist.setTemplateId(autoTemplate.getTemplateId());
		queryExist.setClientId(autoTemplate.getClientId());
		queryExist.setState(autoTemplate.getState());
		queryExist.setSign(autoTemplate.getSign());
		queryExist.setContent(autoTemplate.getContent());
		queryExist.setSmsType(autoTemplate.getSmsType());
		queryExist.setTemplateType(autoTemplate.getTemplateType());
		if (isExistAutoTemplate(queryExist))
		{
			LOGGER.debug("【修改智能模板】 模板ID={}存在相同模板", autoTemplate.getTemplateId());
			return R.error("该模板已存在，请勿重新编辑");
		}

		JsmsAutoTemplate old = this.autoTemplateMapper.getByTemplateId(autoTemplate.getTemplateId());
		if (old == null) {
			LOGGER.debug("【/修改智能模板】 模板ID={},不存在", autoTemplate.getTemplateId());
			return R.error("智能模板不存在");
		}
		// 设置更新时间
		autoTemplate.setUpdateTime(Calendar.getInstance().getTime());
		LOGGER.debug("修改智能模板 {}", JsonUtil.toJson(autoTemplate));
		count = this.autoTemplateMapper.updateAdminId(autoTemplate);
		LOGGER.debug("修改智能模板 {}结束,{}", JsonUtil.toJson(autoTemplate),count > 0 ?"修改成功": "修改失败");
		return count > 0 ? R.ok("修改智能模板成功") : R.error("修改智能模板失败");
	}


	@Override
    @Transactional
	public R addAutoTemplateBatch(File uploadFile, String uploadContentType, Long adminId, String tempFileSavePath) {
	//	R r = new R();
		String timeStamp = new DateTime().toString("yyyyMMddHHmmss");
		String fileName = "批量添加智能模板" + timeStamp + ".xls";
		String fileAbsPath = tempFileSavePath + "/" + fileName;
		LOGGER.debug("批量导入添加智能模板：文件路径 = {}", fileAbsPath);

		// 校验Excel格式、大小
		String msg = JxlExcelUtils.validateExcel(uploadFile, uploadContentType);
		if(msg != null){
			return R.error(msg);
		}

		// 获得Excel文件中的数据
		boolean isFilterBlankRow = true;
		FileUtils.upload(tempFileSavePath, fileName, uploadFile);
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
			validateR = validataAutoTemplateBussiness(adminId, excelDataList, illegalDataList, legalDataList);
		} else {
			return R.error("excel中没有数据");
		}

		if(validateR.getCode().equals(500)){
			return validateR;
		}

		String insertError = this.insertExcelDataBatch(legalDataList);

		if(insertError == null){

			List<Map<String, Object>> importResultList = new ArrayList<>();
			if(illegalDataList.size() > 0){
				importResultList.addAll(illegalDataList);
			}
			if(legalDataList.size() > 0){
				importResultList.addAll(legalDataList);
			}
			generateImportResultExcel(importResultList, adminId, tempFileSavePath);

			return R.ok("导入完成，");

		}else{
			return R.error(insertError);
		}

	}
	private R validataAutoTemplateBussiness(Long adminId, List<List<String>> excelDataList, List<Map<String, Object>> illegalDataList,
											List<Map<String, Object>> legalDataList){
		List<String> row = null;
		row = excelDataList.get(0);
		if(!"用户账号".equals(row.get(0)) || !"短信签名".equals(row.get(1)) || !"模板属性".equals(row.get(2))
				|| !"模板类型".equals(row.get(3)) || !"模板内容".equals(row.get(4))){
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

				if("行业".equals(smsType)) {
					jsmsAutoTemplate.setSmsType(AutoTemplateTypeEnum.行业.getValue());
				}else if("会员营销".equals(smsType)){
					jsmsAutoTemplate.setSmsType(AutoTemplateTypeEnum.会员营销.getValue());
				}else{
				}

				if("变量模板".equals(templateType)){
					jsmsAutoTemplate.setTemplateType(AutoTemplateType.变量模板.getValue());
				}else if("固定模板".equals(templateType)){
					jsmsAutoTemplate.setTemplateType(AutoTemplateType.固定模板.getValue());
				}else{
				}

				jsmsAutoTemplate.setContent(content);
				jsmsAutoTemplate.setAdminId(adminId);
				jsmsAutoTemplate.setSubmitType(AutoTemplateSubmitType.平台提交.getValue());
				jsmsAutoTemplate.setState(AutoTemplateStatus.审核通过.getValue());
				jsmsAutoTemplate.setWebId(WebId.短信调度系统.getValue());

				//导入只支持用户模板(默认)
                jsmsAutoTemplate.setTemplateLevel(TemplateLevel.用户级别.getValue());

                String errorMsgStr = "";

                //校验模板
                R r = this.checkAutoTemplateRightful(jsmsAutoTemplate, false);
                if (!Objects.equals(r.getCode(), SysConstant.SUCCESS_CODE)) {
                    errorMsg.append(r.getMsg());
                    errorMsgStr = errorMsg.toString();
                }

				Map<String, Object> jsmsAutoTemplateMap = BeanUtil.beanToMap(jsmsAutoTemplate, true);
				String smsTypeText = SmsTypeEnum.getDescByValue(jsmsAutoTemplate.getSmsType());
				if( "营销".equals(smsTypeText) ){
					smsTypeText = "会员" + smsTypeText;
				}
				jsmsAutoTemplateMap.put("smsTypeText", smsTypeText);
				jsmsAutoTemplateMap.put("templateTypeText", AutoTemplateType.getDescByValue(jsmsAutoTemplate.getTemplateType()));
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
		LOGGER.debug("批量导入智能模板成功！");
		return r;
	}


	private String insertExcelDataBatch(List<Map<String, Object>> excelDataList) {
		try {
			for (Map<String, Object> row : excelDataList) {
				JsmsAutoTemplateDTO jsmsAutoTemplate = new JsmsAutoTemplateDTO();
				BeanUtil.mapToBean(row, jsmsAutoTemplate);
				int isExist = this.autoTemplateMapper.isExist(jsmsAutoTemplate);
				if(isExist > 0){
					row.put("importState", "失败");
					row.put("failReason", "数据已经存在");
				}else{
					if(jsmsAutoTemplate.getWebId()!=WebId.运营平台.getValue()){
						jsmsAutoTemplate.setUpdateTime(new Date());
					}
					this.autoTemplateMapper.insert(jsmsAutoTemplate);
					row.put("importState", "成功");
				}
			}

		} catch (Exception e) {
			LOGGER.debug("批量导入智能模板时系统错误", e);
			return "批量导入智能模板时系统错误";
		}

		return null;
	}

	private boolean generateImportResultExcel(List<Map<String, Object>> dataList, Long adminId, String tempFileSavePath) {
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

	@Override
	public JsmsPage findListOfOperation(JsmsPage page) {
		List<JsmsAutoTemplate> list = this.autoTemplateMapper.findListOfOperation(page);
		page.setData(list);
		return page;
	}
	@Override
	public JsmsPage queryPageList(JsmsPage page) {
		List<JsmsAutoTemplate> list = this.autoTemplateMapper.queryPageList(page);
		page.setData(list);
		return page;
	}

	@Override
	public Map<String, Object> getAuditNum() {
		int auditNum = 0;
		int sendNum = 0;
		int lockNum = 0;
		int OEMAgentNum =0;
		int OEMNum = 0;
		String submit_type = "0,1,2,3" ;
		auditNum = this.autoTemplateMapper.getNeedAuditNum(submit_type);
		submit_type = "2" ;
		sendNum = this.autoTemplateMapper.getNeedAuditNum(submit_type);
		 submit_type = "3";
		lockNum = this.autoTemplateMapper.getNeedAuditNum(submit_type);
		submit_type = "0";
		OEMNum = this.autoTemplateMapper.getNeedAuditNum(submit_type);
		submit_type = "1";
		OEMAgentNum = this.autoTemplateMapper.getNeedAuditNum(submit_type);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("auditNum", auditNum);
		resultMap.put("sendNum", sendNum);
		resultMap.put("lockNum", lockNum);
		resultMap.put("OEMNum", OEMNum);
		resultMap.put("OEMAgentNum", OEMAgentNum);
		return resultMap;
	}

	/**
	 * 判断空签名通用模板添加是否有签名需要删除
	 * @param autoTemplate
	 * @return
	 */
	private R checkGeneralTemplate4SureDel(JsmsAutoTemplate autoTemplate,boolean isSureDel){
		JsmsAutoTemplateDTO queryExist = new JsmsAutoTemplateDTO();

		queryExist.setContent(autoTemplate.getContent());
		queryExist.setSmsType(autoTemplate.getSmsType());
		queryExist.setTemplateType(autoTemplate.getTemplateType());
		R r = new R();
		if (autoTemplate.getTemplateId() != null) {
			queryExist.setUpdateTemId(autoTemplate.getTemplateId());
		}
		Integer isadd = queryExist.getUpdateTemId();
		if (Objects.equals(autoTemplate.getTemplateLevel(), TemplateLevel.全局级别.getValue()) && StringUtils.isBlank(autoTemplate.getSign())) {

			if (isSureDel) {
				r.setCode(SysConstant.DEL_CODE);
				r.setMsg("确定添加,原【相同内容+签名】的通用模板将被删除!");
				LOGGER.debug("——————————————————————————确定添加,原【相同内容+签名】的通用模板将被删除!——————————————————————————");
				return r;
			} else {

				queryExist.setSign("-2");//非空标志位-2
				//通用模板clientID为*
				queryExist.setClientId(SysConstant.ALL);
				//模板级别
				queryExist.setTemplateLevel(TemplateLevel.全局级别.getValue());
				if (isExistAutoTemplate(queryExist)) {
//					JsmsPage<JsmsAutoTemplate> dellist=this.queryList(page);
					r.setCode(SysConstant.SUER_DEL_CODE);
					r.setMsg("已存在【相同内容+签名】的通用模板，如确定添加，则原【相同内容+签名】的通用模板将被删除");
//					LOGGER.debug("——————————————————————————已存在【相同内容+签名】的通用模板，如确定" + isadd == null ? "添加" : "编辑" + "，则原【相同内容+签名】的通用模板将被删除,相同内容的通用模板有{}——————————————————————————",JsonUtil.toJson(dellist));
					return r;
				}
				LOGGER.debug("——————————————————————————通用模板空签名可" + isadd == null ? "添加" : "编辑" + "——————————————————————————");
				return R.ok("通用黑模板空签名可" + isadd == null ? "添加" : "编辑");

			}

		}

		return  R.error("非法校验");
	}


	/**
	 * 用户和通用白板的校验
	 * @param autoTemplate
	 * @return
	 */
	@Override
    //@Transactional("message")
	public R checkAutoTemplateRightful(JsmsAutoTemplate autoTemplate,Boolean isSureDel) {
		R r = new R();
        if (!(AutoTemplateStatus.审核不通过.getValue().equals(autoTemplate.getState()))) {
			//不做校验
            // 检测模板信息
			if(autoTemplate.getWebId().equals(WebId.短信调度系统.getValue())){
				r = this.checkAutoTemplate(autoTemplate);
			}else{
				r= this.checkModifTemplate(autoTemplate);
			}
            if (r != null) {
                return r;
            }
        }
        //模板id
        Integer temId = autoTemplate.getTemplateId();

        //签名
        String sign = autoTemplate.getSign();
        //模板级别
        Integer templateLevel = autoTemplate.getTemplateLevel();
        //系统ID
        Integer templateWebId = autoTemplate.getWebId();

        JsmsAutoTemplateDTO queryExist = new JsmsAutoTemplateDTO();
        //模板内容
        queryExist.setContent(autoTemplate.getContent());
        //模板属性
        queryExist.setSmsType(autoTemplate.getSmsType());
        //模板类型
        queryExist.setTemplateType(autoTemplate.getTemplateType());
        //clientId
        String clientId = autoTemplate.getClientId();
		//经过黑模板校验
        r = this.checkForAutoBlackTemplate(autoTemplate, templateWebId);
        if (Objects.equals(r.getCode(), SysConstant.FAIL_CODE)) {
            return r;
        }
		//如果存在temID说明是修改,要排除自己的ID
        queryExist.setUpdateTemId(temId);

        //是否存【空签名+模板属性+模板类型+模板内容】通用模板（已删除不校验）
        queryExist.setTemplateLevel(TemplateLevel.全局级别.getValue());
        queryExist.setSign("-1");    //此处传-1为了mapper里的sql判断,具体可参照(JsmsAutoTemplateMapper.xml)
        queryExist.setClientId(SysConstant.ALL);  //通用模板的cliendId为*
        if (isExistAutoTemplate(queryExist)) {
            return R.error("已存在相同类型的通用模板");
        }
        if(templateWebId.equals(WebId.短信调度系统.getValue())){//只有调度系统需要校验
			//是否存在【签名+模板属性+模板类型+模板内容】的通用模板（已删除不校验）
			if (StringUtils.isNotBlank(sign)) {
				queryExist.setSign(sign);
				if (isExistAutoTemplate(queryExist)) {
					return R.error("已存在相同类型的通用模板");
				}
			}
		}else{
			queryExist.setSign(sign);
		}

        //校验重复性
        queryExist.setClientId(clientId);
        queryExist.setTemplateLevel(templateLevel);
		if ( isExistAutoTemplate(queryExist)) {
            if (templateWebId.equals(WebId.运营平台.getValue()) || templateWebId.equals(WebId.OEM代理商平台.getValue()) || templateWebId.equals(WebId.客户端.getValue())) {
                return R.error("已存在相同类型的智能模板");
            } else {
                return R.error("已存在相同类型的智能模板");
            }
        }

        //调度系统的空签名通用模板需要校验是否存在相同类型非空签名的通用模板,进行二次确认删除
        if (templateWebId.equals(WebId.短信调度系统.getValue())) {
            //调度系统校验二次确认删除判断
            if (StringUtils.isBlank(autoTemplate.getSign()) && Objects.equals(autoTemplate.getTemplateLevel(), TemplateLevel.全局级别.getValue())) {
                r = this.checkGeneralTemplate4SureDel(autoTemplate, isSureDel);

                //调度系统添加空签名通用模板，需删除有签名通用模板
                if (Objects.equals(r.getCode(), SysConstant.DEL_CODE)) {
                    //查出相同内容有签名通用模板进行删除，日志输出
                    List<JsmsAutoTemplate> templateList = this.queryTemplate4SignIsNull(autoTemplate);
                    LOGGER.debug("——————————————————————————已存在【相同内容+签名】的通用模板，【相同内容+签名】的通用模板将被删除,相同内容的通用模板有{}——————————————————————————", JsonUtil.toJson(templateList));
                    for (JsmsAutoTemplate jsmsAutoTemplate : templateList) {
                        Integer templateId = jsmsAutoTemplate.getTemplateId();
                        if (templateId != null) {
                            //删除“模板属性+{签名}+模板类型+模板内容”的模板
                            this.delAutoTemplate(templateId);
                        }
                    }

                } else if (!Objects.equals(r.getCode(), SysConstant.SUCCESS_CODE)) {
                    return r;
                }
            }
        }

        return R.ok("用户和通用白板的校验通过！");
    }

	/**
	 * 智能白模板经过通用黑模板校验
	 * @param autoTemplate
	 * @param WebId
	 */
	private R checkForAutoBlackTemplate(JsmsAutoTemplate autoTemplate, Integer WebId) {
		JsmsBalckTemplateDTO blackDto = new JsmsBalckTemplateDTO();
		//模板级别
		blackDto.setTemplateLevel(autoTemplate.getTemplateLevel());
		//模板类型
		blackDto.setTemplateType(autoTemplate.getTemplateType());
		//短信类型
		blackDto.setSmsType(autoTemplate.getSmsType());
		//模板内容
		blackDto.setContent(autoTemplate.getContent());
		//模板签名
		blackDto.setSign(autoTemplate.getSign());
		//账号
		blackDto.setClientId(autoTemplate.getClientId());

		return  jsmsAutoBlackTemplateService.checkBalckTemplate4AutoTemplate(blackDto, WebId);

	}

	/**
	 * 查询相同类型的非空签名通用模板
	 * @param autoTemplate
	 * @return
	 */
	private List<JsmsAutoTemplate> queryTemplate4SignIsNull(JsmsAutoTemplate autoTemplate) {
        JsmsAutoTemplateDTO queryExist = new JsmsAutoTemplateDTO();

        //模板内容
        queryExist.setContent(autoTemplate.getContent());
        //模板属性
        queryExist.setSmsType(autoTemplate.getSmsType());
        //模板类型
        queryExist.setTemplateType(autoTemplate.getTemplateType());
        //通用模板cliendId为*
        queryExist.setClientId(SysConstant.ALL);
        //模板级别
        queryExist.setTemplateLevel(TemplateLevel.全局级别.getValue());

        queryExist.setSign("-2"); //非空标志为-2

        JsmsPage<JsmsAutoTemplate> page = new JsmsPage<>();
        page.setRows(60000);
        page.setPage(1);
        page.setParams(BeanUtil.beanToMap(queryExist, false));

        List list = this.autoTemplateMapper.findList(page);

        return list;
    }

	@Override
	@Transactional
	public R addAutoTemplate(JsmsAutoTemplate autoTemplate, boolean isSureDel) {
        R r = new R();

        //校验智能模板通用校验
        r = this.checkAutoTemplateRightful(autoTemplate, isSureDel);
        if (!Objects.equals(r.getCode(), SysConstant.SUCCESS_CODE)) {
            return r;
        }
        Integer templateWebId = autoTemplate.getWebId();

        //创建时间
        autoTemplate.setCreateTime(Calendar.getInstance().getTime());
        // 设置更新时间
        autoTemplate.setUpdateTime(Calendar.getInstance().getTime());


        LOGGER.debug("添加智能模板 {}", JsonUtil.toJson(autoTemplate));
        //执行插入操作
        int count = this.autoTemplateMapper.insert(autoTemplate);

        if (templateWebId.equals(WebId.运营平台.getValue()) || templateWebId.equals(WebId.OEM代理商平台.getValue()) || templateWebId.equals(WebId.客户端.getValue())) {

            LOGGER.debug("运营平台添加智能模板 {},{}", JsonUtil.toJson(autoTemplate), count > 0 ? "添加成功" : "添加失败");
            return count > 0 ? R.ok("模板提交成功，进入系统审核") : R.error("模板提交失败");
        } else if (templateWebId.equals(WebId.短信调度系统.getValue())) {

            LOGGER.debug("调度系统添加智能模板 {},{}", JsonUtil.toJson(autoTemplate), count > 0 ? "添加成功" : "添加失败");
            return count > 0 ? R.ok("添加智能模板成功") : R.error("添加智能模板失败");
        } else {

            LOGGER.debug("其他系统添加智能模板 {},{}", JsonUtil.toJson(autoTemplate), count > 0 ? "添加成功" : "添加失败");
            return count > 0 ? R.ok("添加智能模板成功") : R.error("添加智能模板失败");
        }

    }

}
