package com.jsmsframework.channel.dto;

import com.jsmsframework.channel.entity.JsmsChannel;
import com.jsmsframework.channel.enums.ChannelTypeEnum;

import java.math.BigDecimal;

/**
 * Created by Don on 2017/12/2.
 */
public class JsmsChannelDTO extends JsmsChannel{

    private BigDecimal successRate;
    private String channelTypeStr;

    public BigDecimal getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(BigDecimal successRate) {
        this.successRate = successRate;
    }

    public String getChannelTypeStr() {
        if (getChanneltype()!=null){
            channelTypeStr = ChannelTypeEnum.getDescByValue(getChanneltype());
        }
        return channelTypeStr;
    }

    public void setChannelTypeStr(String channelTypeStr) {
        this.channelTypeStr = channelTypeStr;
    }
}
