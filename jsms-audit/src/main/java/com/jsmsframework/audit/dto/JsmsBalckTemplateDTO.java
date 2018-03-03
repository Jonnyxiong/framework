package com.jsmsframework.audit.dto;

import com.jsmsframework.audit.entity.JsmsAutoBlackTemplate;
import com.jsmsframework.common.enums.balckAndWhiteTemplate.AutoTemplateType;
import com.jsmsframework.common.enums.balckAndWhiteTemplate.AutoTemplateTypeEnum;
import com.jsmsframework.common.enums.balckAndWhiteTemplate.BalckTemplateState;
import com.jsmsframework.common.enums.balckAndWhiteTemplate.TemplateLevel;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * Created by Don on 2017/12/6.
 */
public class JsmsBalckTemplateDTO extends JsmsAutoBlackTemplate {

    private String templateLevelStr;

    private String templateTypeStr;

    private String smsTypeStr;

    private String userName;

    private String stateStr;

    private String latelyMatchDateStr;

    private String updateTimeStr;

    private Boolean isSureDel;

    /**
     * 编辑时查询条件 不等于更新模板ID
     */
    private Integer updateTemId;
    //查询条件
    private String startTime;
    //查询条件
    private String endTime;

    private Integer mixNum;

    private Integer maxNum;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getMixNum() {
        return mixNum;
    }

    public void setMixNum(Integer mixNum) {
        this.mixNum = mixNum;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public Integer getUpdateTemId() {
        return updateTemId;
    }

    public void setUpdateTemId(Integer updateTemId) {
        this.updateTemId = updateTemId;
    }

    public Boolean getSureDel() {
        return isSureDel;
    }

    public void setSureDel(Boolean sureDel) {
        isSureDel = sureDel;
    }

    public String getTemplateLevelStr() {
        return templateLevelStr=TemplateLevel.getDescByValue(getTemplateLevel());
    }

    public void setTemplateLevelStr(String templateLevelStr) {

        this.templateLevelStr = templateLevelStr;
    }

    public String getTemplateTypeStr() {

        return templateTypeStr=AutoTemplateType.getDescByValue(getTemplateType());
    }

    public void setTemplateTypeStr(String templateTypeStr) {
        this.templateTypeStr = templateTypeStr;
    }

    public String getSmsTypeStr() {
        return smsTypeStr=AutoTemplateTypeEnum.getDescByValue(getSmsType());
    }

    public void setSmsTypeStr(String smsTypeStr) {
        this.smsTypeStr = smsTypeStr;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStateStr() {

        return stateStr= BalckTemplateState.getDescByValue(getState());
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
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

    public String getUpdateTimeStr() {
        if(getUpdateTime()!=null){
            updateTimeStr=DateFormatUtils.format(getUpdateTime(), "yyyy-MM-dd HH:mm:ss");
        }else {
            updateTimeStr="-";
        }
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }
}
