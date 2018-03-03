package com.jsmsframework.middleware.dto;

import com.jsmsframework.common.enums.middleware.MessageType;
import com.jsmsframework.common.enums.middleware.MiddlewareType;
import com.jsmsframework.common.enums.middleware.ModeType;
import com.jsmsframework.middleware.entity.JsmsComponentRefMq;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * Created by Don on 2018/1/2.
 */
public class ComponentRefMqDTO extends JsmsComponentRefMq {

    private  String modeName;

    private String messageTpyeStr;

    private String updateTimeStr;

    private String componentIdAndName;

    private String mqIdAndName;

    private String weightStr;

    private String getRateStr;

    private String ids;

    private String mqIdAndOther;


    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getMqIdAndOther() {
        return mqIdAndOther;
    }

    public void setMqIdAndOther(String mqIdAndOther) {
        this.mqIdAndOther = mqIdAndOther;
    }

    public String getModeName() {
        return modeName= ModeType.getDescByValue(getMode());
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    public String getMessageTpyeStr() {
        return messageTpyeStr= MessageType.getDescByValue(getMessageType());
    }

    public void setMessageTpyeStr(String messageTpyeStr) {
        this.messageTpyeStr = messageTpyeStr;
    }

    public String getUpdateTimeStr() {
        if(getUpdateDate()!=null){
            updateTimeStr= DateFormatUtils.format(getUpdateDate(), "yyyy-MM-dd HH:mm:ss");
        }else {
            updateTimeStr="-";
        }
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public String getComponentIdAndName() {
        return componentIdAndName;
    }

    public void setComponentIdAndName(String componentIdAndName) {
        this.componentIdAndName = componentIdAndName;
    }

    public String getMqIdAndName() {
        return mqIdAndName;
    }

    public void setMqIdAndName(String mqIdAndName) {
        this.mqIdAndName = mqIdAndName;
    }

    public String getWeightStr() {
        return weightStr;
    }

    public void setWeightStr(String weightStr) {
        this.weightStr = weightStr;
    }

    public String getGetRateStr() {
        return getRateStr;
    }

    public void setGetRateStr(String getRateStr) {
        this.getRateStr = getRateStr;
    }
}
