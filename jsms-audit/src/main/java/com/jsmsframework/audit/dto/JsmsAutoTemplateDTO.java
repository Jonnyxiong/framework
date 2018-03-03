package com.jsmsframework.audit.dto;

import com.jsmsframework.audit.entity.JsmsAutoTemplate;
import com.jsmsframework.common.enums.balckAndWhiteTemplate.*;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * Created by xiongfenglin on 2017/10/23.
 *
 * @author: xiongfenglin
 */
public class JsmsAutoTemplateDTO extends JsmsAutoTemplate {
    private String adminName; // 操作者名称

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
    private String smsTypeStr;
    private String templateTypeStr;
    private String stateStr;

    private String updateTimeStr;
    private String createTimeStr;
    private String userName;//创建者名称
    private String templateLevelStr;//模板级别名称
    private String latelyMatchDateStr; //最近匹配日期
    private String submitTypeStr;

    public String getSubmitTypeStr() {
        return submitTypeStr= AutoTemplateSubmitType.getDescByValue(getSubmitType());
    }

    public void setSubmitTypeStr(String submitTypeStr) {
        this.submitTypeStr = submitTypeStr;
    }

    /**
     * 查询参数
     */
    private Integer updateTemId;

    public Integer getUpdateTemId() {
        return updateTemId;
    }

    public void setUpdateTemId(Integer updateTemId) {
        this.updateTemId = updateTemId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUpdateTimeStr() {
        if(getUpdateTime()!=null){
            updateTimeStr= DateFormatUtils.format(getUpdateTime(), "yyyy-MM-dd HH:mm:ss");
        }else {
            updateTimeStr="-";
        }
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public String getCreateTimeStr() {

        if(getCreateTime()!=null){
            createTimeStr= DateFormatUtils.format(getCreateTime(), "yyyy-MM-dd HH:mm:ss");
        }else {
            createTimeStr="-";
        }
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getSmsTypeStr() {
        return smsTypeStr= AutoTemplateTypeEnum.getDescByValue(getSmsType());
    }

    public void setSmsTypeStr(String smsTypeStr) {
        this.smsTypeStr = smsTypeStr;
    }

    public String getTemplateTypeStr() {
        return templateTypeStr= AutoTemplateType.getDescByValue(getTemplateType());
    }

    public void setTemplateTypeStr(String templateTypeStr) {
        this.templateTypeStr = templateTypeStr;
    }

    public String getStateStr() {
        return stateStr= AutoTemplateStatus.getDescByValue(getState());
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }

    public String getTemplateLevelStr() {
        return templateLevelStr = TemplateLevel.getDescByValue(getTemplateLevel());
    }

    public void setTemplateLevelStr(String templateLevelStr) {
        this.templateLevelStr = templateLevelStr;
    }

    public String getLatelyMatchDateStr() {
        if(getLatelyMatchDate()!=null){
            latelyMatchDateStr= DateFormatUtils.format(getLatelyMatchDate(), "yyyy-MM-dd");
        }else {
            latelyMatchDateStr="-";
        }
        return latelyMatchDateStr;
    }

    public void setLatelyMatchDateStr(String latelyMatchDateStr) {
        this.latelyMatchDateStr = latelyMatchDateStr;
    }
}
