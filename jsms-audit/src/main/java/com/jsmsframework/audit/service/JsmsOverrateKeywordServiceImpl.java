package com.jsmsframework.audit.service;

import java.io.File;
import java.util.*;

import com.jsmsframework.common.dto.R;
import com.jsmsframework.common.util.*;

import com.ucpaas.sms.common.util.file.ExcelUtils;
import com.ucpaas.sms.model.Excel;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.audit.mapper.JsmsOverrateKeywordMapper;
import com.jsmsframework.audit.entity.JsmsOverrateKeyword;



import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 超频关键字表
 * @author huangwenjie
 * @date 2017-10-31
 */
@Service
public class JsmsOverrateKeywordServiceImpl implements JsmsOverrateKeywordService{
    private  static final Logger LOGGER= LoggerFactory.getLogger(JsmsOverrateKeywordServiceImpl.class);
    @Autowired
    private JsmsOverrateKeywordMapper overrateKeywordMapper;
    
    @Override
	@Transactional
    public int insert(JsmsOverrateKeyword model) {
        return this.overrateKeywordMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsOverrateKeyword> modelList) {
        return this.overrateKeywordMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsOverrateKeyword model) {
		JsmsOverrateKeyword old = this.overrateKeywordMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.overrateKeywordMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsOverrateKeyword model) {
		JsmsOverrateKeyword old = this.overrateKeywordMapper.getById(model.getId());
		if(old != null)
        	return this.overrateKeywordMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsOverrateKeyword getById(Integer id) {
        JsmsOverrateKeyword model = this.overrateKeywordMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsOverrateKeyword> list = this.overrateKeywordMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.overrateKeywordMapper.count(params);
    }

    @Override
    public Map<String, Object> checkExist(Map<String, Object> param) {
        return this.overrateKeywordMapper.checkExist(param);
    }

    @Override
    public R delOverrate(Integer id) {
        if(id==null){
            return  R.error("超频关键字设置ID不存在");
        }
        JsmsOverrateKeyword over=this.overrateKeywordMapper.getById(id);
        if(over.getId()==null){
            LOGGER.debug("删除超频关键字设置 ID={},不存在", id);
            return R.error("超频关键字设置不存在");
        }

        int del=this.overrateKeywordMapper.delOverrate(id);
        LOGGER.debug( "删除超频关键字设置 ID={}结束,{}", JsonUtil.toJson(id),del > 0 ? "成功删除": "失败删除");
        return del > 0 ? R.ok("删除超频关键字设置成功") : R.error("删除超频关键字设置失败");
    }




    private boolean generateErrorExcel(List<Map<String, Object>> dataList,String tempFileSavePath,Long adminId) {
        String filePath = tempFileSavePath + "/import" + "/超频关键字导入失败列表-userid-" + adminId
                + ".xls";
        File dir = new File(tempFileSavePath + "/import");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        Excel excel = new Excel();
        excel.setFilePath(filePath);
        excel.setShowPage(false);
        excel.setTitle("超频关键字导入失败列表");
        excel.addHeader(20, "超频关键字", "keyword");
        excel.addHeader(20, "用户账号", "clientid");
        excel.addHeader(20, "备注", "remarks");
        excel.addHeader(100, "原因", "reason");
        excel.setDataList(dataList);
        return ExcelUtils.exportExcel(excel);
    }
    @Override
    public R addOverrateKeywordBatch(File uploadFile, String uploadContentType, Long adminId, String tempFileSavePath) {
        R r = new R();
        Map<String, Object> data = new HashMap<String, Object>();
        String timeStamp = new DateTime().toString("yyyyMMddHHmmss");
        String fileName = "批量导入超频关键字" + timeStamp + ".xls";
        String filePath = tempFileSavePath;
        String fileAbsPath = tempFileSavePath + "/" + fileName;
        LOGGER.debug("批量导入超频关键字：文件路径 = {}", fileAbsPath);

        // 校验Excel格式、大小
        String  msg1 = JxlExcelUtils.validateExcel(uploadFile, uploadContentType);
        if(msg1 != null){
            return r=R.error(msg1);
        }

        // 获得Excel文件中的数据（上传文件、取出数据、删除文件）
        List<List<String>> excelDataList = getImportExcelData(uploadFile, fileName, filePath, fileAbsPath);

        if (excelDataList.size() > 60000) {
            return r=R.error("您选择的excel中数据记录大于60000条，请您拆分后上传");
        }

        List<Map<String, Object>> illegalDataList = new ArrayList<Map<String, Object>>(); // Excel中非法数据
        List<Map<String, Object>> legalDataList = new ArrayList<Map<String, Object>>(); // Excel中合法数据
        if (excelDataList != null && excelDataList.size() > 1) {
            validateExcelData(excelDataList, illegalDataList, legalDataList);
        } else {
            return r=R.error("excel中没有数据");
        }

        Map<String, Object> saveResult = saveDataBatch(legalDataList);

        if("success".equals(saveResult.get("result"))){
            List<Map<String, Object>> rowNotInsert = (List<Map<String, Object>>) saveResult.get("rowNotInsert");
            if(rowNotInsert != null && rowNotInsert.size() > 0){
                illegalDataList.addAll(rowNotInsert);
            }

            if(illegalDataList.size() > 0){
                // 生成导入失败的Excel
                generateErrorExcel(illegalDataList,tempFileSavePath,adminId);
            }

            int totalNum = excelDataList.size() - 1;
            int importFailNum = illegalDataList.size();
            int importNum = totalNum - importFailNum;
            StringBuilder msg = new StringBuilder();
            if(totalNum != importNum){
                msg.append("Excel中共");
                msg.append(totalNum);
                msg.append("条记录；");
                msg.append("成功导入");
                msg.append(importNum);
                msg.append("条记录；存在 ");
                msg.append(importFailNum);
                msg.append("条记录导入失败!");
                data.put("msg", msg.toString());
                data.put("existFail", 1);
                data.put("result", "success");
            }else{
                msg.append("导入成功");
                data.put("msg", msg);
                data.put("result", "success");
            }
        }else{
            data.put("result", "success");
            data.put("msg", "Excel导入失败");
        }
        r.setData(data);
        return r;


    }

    private Map<String, Object> saveDataBatch(List<Map<String, Object>> excelDataList) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> rowNotInsert = new ArrayList<Map<String, Object>>();
        try {
            for (Map<String, Object> oneRow : excelDataList) {

                Map<String, Object> queryCount = this.overrateKeywordMapper.checkExist(oneRow);
                     //   getOneInfo("auditKeyWords.saveCheck", oneRow);
                if(queryCount != null){
                    oneRow.put("reason", "数据已经存在或Excel中重复");
                    rowNotInsert.add(oneRow);
                }else{
                    JsmsOverrateKeyword over=new JsmsOverrateKeyword();
                    BeanUtil.mapToBean(oneRow,over);
                    over.setUpdateDate(new Date());
                    this.insert(over);
                  //  messageMasterDao.insert("auditKeyWords.insert", oneRow);
                }
            }

            result.put("result", "success");
            if(rowNotInsert.size() > 0){
                result.put("rowNotInsert", rowNotInsert);
            }
        } catch (Exception e) {
            result.put("result", "fail");
            LOGGER.debug("批量导入审核及超频关键字时系统错误", e);
        }

        return result;
    }

    private List<List<String>> getImportExcelData(File uploadFile, String fileName, String filePath, String fileAbsPath) {
        FileUtils.upload(filePath, fileName, uploadFile);
        List<List<String>> excelDataList = ExcelUtils.importExcel(fileAbsPath);
        FileUtils.delete(fileAbsPath);

        // 过滤掉excel中的空白行
        if(excelDataList != null && excelDataList.size() > 0){
            List<List<String>> tempList = new ArrayList<>();
            for (int i = 0; i < excelDataList.size(); i++) {
                List<String> row = excelDataList.get(i);
                boolean isNotEmpty = false;
                for (String cell : row) {
                    if (StringUtils.isNotBlank(cell)) {
                        isNotEmpty = true;
                    }
                }
                if (isNotEmpty) {
                    tempList.add(row);
                }
            }
            excelDataList = tempList;
        }
        return excelDataList;
    }

    private void validateExcelData(List<List<String>> excelDataList, List<Map<String, Object>> illegalDataList,
                                   List<Map<String, Object>> legalDataList) {
        String keyword = null;
        String clientId = null;
        String remarks = null;

        List<String> row = new ArrayList<String>();
        for (int pos = 1; pos < excelDataList.size(); pos++) {
            // 获得Excel中一行数据
            row = excelDataList.get(pos);

            // 对一行数据中的多个单元格数据进行校验
            if (row != null && row.size() > 0) {
                StringBuilder errorMsg = new StringBuilder();

                // 校验关键字
                try {
                    keyword = Objects.toString(row.get(0), "").trim();
                } catch (Exception e) {
                    keyword = "";
                }
                if (StringUtils.isBlank(keyword)) {
                    errorMsg.append("关键字为空;");
                }
                if (keyword.length() > 60){
                    errorMsg.append("关键字长度超过60;");
                }

                // 校验clientId
                try {
                    clientId = Objects.toString(row.get(1), "").trim();
                } catch (Exception e) {
                    clientId = "";
                }
                if (StringUtils.isBlank(clientId)) {
                    errorMsg.append("用户账号为空;");
                }
                if (!RegexUtils.isClientId(clientId) && !"*".equals(clientId)){
                    errorMsg.append("用户账号不合法");
                }

                // 校验备注
                try {
                    remarks = Objects.toString(row.get(2), "").trim();
                } catch (Exception e) {
                    remarks = "";
                }
                if (remarks.length() > 128) {
                    errorMsg.append("备注超长;");
                }

                // 校验后的数据合法的行保存在legalDataList中，非法的行保存在illegalDataList中
                Map<String, Object> validateRow = new HashMap<>();

                if (errorMsg.length() > 1) {
                    validateRow.put("keyword", keyword);
                    validateRow.put("clientid", clientId);
                    validateRow.put("remarks", remarks);
                    validateRow.put("reason", errorMsg);
                    illegalDataList.add(validateRow);
                }else{
                    validateRow.put("keyword", keyword);
                    validateRow.put("clientid", clientId);
                    validateRow.put("remarks", remarks);
                    legalDataList.add(validateRow);
                }
            }
        }
    }

}
