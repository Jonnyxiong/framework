package com.jsmsframework.sysKeyword.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 系统关键字工具类
 * @author yeshiyuan
 * @create 2018/1/17
 */
public class SysKeywordUtil {

    /**
     * @Description  文本转化（繁体、简体、特殊字符处理）
     * @author yeshiyuan
     * @date 2018/1/17 9:39
     * @param [params]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    public static Map<String,Object> textTransform(Map<String,Object> params){
        if(params!=null){
            String condition = (String) params.get("condition");
            String simplifredChineseCondition = (String) params.get("simplifredChineseCondition");
            String traditionalChineseCondition = (String) params.get("traditionalChineseCondition");
            if(StringUtils.isNotBlank(condition)){
                condition = condition.replaceAll("\\\\","\\\\\\\\");
                condition = condition.replaceAll("_","\\\\_");
                condition = condition.replaceAll("%","\\\\%");
                condition = condition.replaceAll("'","\\\\'");
                params.put("condition",condition);
            }
            if(StringUtils.isNotBlank(simplifredChineseCondition)){
                simplifredChineseCondition = simplifredChineseCondition.replaceAll("\\\\","\\\\\\\\");
                simplifredChineseCondition = simplifredChineseCondition.replaceAll("_","\\\\_");
                simplifredChineseCondition = simplifredChineseCondition.replaceAll("%","\\\\%");
                simplifredChineseCondition = simplifredChineseCondition.replaceAll("'","\\\\'");
                params.put("simplifredChineseCondition",simplifredChineseCondition);
            }
            if(StringUtils.isNotBlank(traditionalChineseCondition)){
                traditionalChineseCondition = traditionalChineseCondition.replaceAll("\\\\","\\\\\\\\");
                traditionalChineseCondition = traditionalChineseCondition.replaceAll("_","\\\\_");
                traditionalChineseCondition = traditionalChineseCondition.replaceAll("%","\\\\%");
                traditionalChineseCondition = traditionalChineseCondition.replaceAll("'","\\\\'");
                params.put("traditionalChineseCondition",traditionalChineseCondition);
            }
        }
        return params;
    }

    /**
     * 转化为string
     * @param object
     * @return
     */
    public static String convertToStr(Object object){
        if(object instanceof Double){
            BigDecimal bigDecimal = new BigDecimal((double) object);
            return bigDecimal.toString();
        }else if(object instanceof String){
            return (String) object;
        }else {
            try {
                return String.valueOf(object) ;
            }catch (Exception e){
                return null;
            }
        }
    }

}
