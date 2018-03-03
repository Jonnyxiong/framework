package com.jsmsframework.common.util;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.dto.ResultVO;
import com.jsmsframework.common.entity.JsmsExcel;
import com.jsmsframework.common.util.file.ExcelUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dylan on 2017/11/30.
 *
 * @author: dylan
 */
public class PageExportUtil<T> {
    private static final Logger logger = LoggerFactory.getLogger(PageExportUtil.class);
    private static final int DEFAULT_EXPORT_NUM = 6000;

    public static final PageExportUtil instance(){
        return new PageExportUtil();
    }

    /**
     * @param t 注入的service对象
     * @param jsmsPage
     * @param jsmsExcel
     * @param queryMethod 页面查询方法,
     * @return 报表是否生成成功, 返回生成的报表path
     */
    public ResultVO exportPage(T t, JsmsPage jsmsPage, JsmsExcel jsmsExcel, String pageExportQualifier) {
        jsmsPage.setRows(DEFAULT_EXPORT_NUM);
        jsmsPage.setMaxQueryLimit(DEFAULT_EXPORT_NUM);
        return commonExport(t,jsmsPage, jsmsExcel,pageExportQualifier);
    }

    /**
     * @param maxExportNum 最大导出数
     * @param t 注入的service对象
     * @param jsmsPage
     * @param jsmsExcel
     * @param pageExportQualifier 页面查询方法,
     * @return 报表是否生成成功, 返回生成的报表path
     */
    public ResultVO exportPage(int maxExportNum,T t,JsmsPage jsmsPage, JsmsExcel jsmsExcel, String pageExportQualifier) {
        jsmsPage.setRows(maxExportNum);
        jsmsPage.setMaxQueryLimit(maxExportNum);
        return commonExport(t,jsmsPage, jsmsExcel,pageExportQualifier);
    }

    /*private ResultVO commonExport(T t,JsmsPage jsmsPage, JsmsExcel jsmsExcel, String queryMethod){
        ResultVO resultVO = ResultVO.failure();
        resultVO.setMsg("生成报表失败");
        try {

            jsmsPage = (JsmsPage) t.getClass().getMethod(queryMethod,jsmsPage.getClass()).invoke(t, jsmsPage);
            List data = jsmsPage.getData();
            if (jsmsPage.getTotalRecord() <= 0){
                return ResultVO.failure("没有数据！先不导出了  ^_^");
            }else if (jsmsPage.getTotalRecord() > jsmsPage.getMaxQueryLimit()){
                StringBuilder msg = new StringBuilder("数据量超过");
                msg.append(jsmsPage.getMaxQueryLimit()).append("，请缩小范围后再导出...  ^_^");
                return ResultVO.failure(msg.toString());
            }

            List result = new ArrayList(jsmsPage.getTotalRecord());
            for (Object obj:data){
                Map<String, String> describe = BeanUtils.describe(obj);
                result.add(describe);
            }
//            todo 总计行
//            jsmsPage = (JsmsPage) this.getClass().getMethod(queryMethod + "Total",jsmsPage.getClass()).invoke(this, jsmsPage);
//            result.add(BeanUtils.describe(jsmsPage.getTotalOtherData().get("totalLine")));
            jsmsExcel.setDataList(result);

            if (ExcelUtils.exportExcel(jsmsExcel)) {
                resultVO.setSuccess(true);
                resultVO.setMsg("报表生成成功");
                resultVO.setData(jsmsExcel.getFilePath());
                return resultVO;
            }
        } catch (Exception e) {
            logger.error("生成报表失败", e);
        }
        return resultVO;
    }*/

    private ResultVO commonExport(T t,JsmsPage jsmsPage, JsmsExcel jsmsExcel, String pageExportQualifier){
        ResultVO resultVO = ResultVO.failure();
        resultVO.setMsg("生成报表失败");
        try {
            Class clazz = t.getClass();
            Method[] methods = clazz.getMethods();
            Method invokeMethod = null;
            for (Method method : methods) {
                PageExportQualifier exportQualifier = method.getAnnotation(PageExportQualifier.class);
                if (exportQualifier != null && pageExportQualifier.equals(exportQualifier.value())){
                    invokeMethod = method;
                    break;
                }
            }
            if (invokeMethod == null){
                invokeMethod = t.getClass().getMethod(pageExportQualifier, jsmsPage.getClass());
            }
            jsmsPage = (JsmsPage) invokeMethod.invoke(t, jsmsPage);
            List data = jsmsPage.getData();
            if (jsmsPage.getTotalRecord() <= 0){
                return ResultVO.failure("没有数据！先不导出了  ^_^");
            }else if (jsmsPage.getTotalRecord() > jsmsPage.getMaxQueryLimit()){
                StringBuilder msg = new StringBuilder("数据量超过");
                msg.append(jsmsPage.getMaxQueryLimit()).append("，请缩小范围后再导出...  ^_^");
                return ResultVO.failure(msg.toString());
            }

            List result = new ArrayList(jsmsPage.getTotalRecord());
            for (Object obj:data){
                Map<String, String> describe = BeanUtils.describe(obj);
                result.add(describe);
            }
//            todo 总计行
//            jsmsPage = (JsmsPage) this.getClass().getMethod(queryMethod + "Total",jsmsPage.getClass()).invoke(this, jsmsPage);
//            result.add(BeanUtils.describe(jsmsPage.getTotalOtherData().get("totalLine")));
            jsmsExcel.setDataList(result);

            if (ExcelUtils.exportExcel(jsmsExcel)) {
                resultVO.setSuccess(true);
                resultVO.setMsg("报表生成成功");
                resultVO.setData(jsmsExcel.getFilePath());
                return resultVO;
            }
        } catch (Exception e) {
            logger.error("生成报表失败", e);
        }
        return resultVO;
    }
}
