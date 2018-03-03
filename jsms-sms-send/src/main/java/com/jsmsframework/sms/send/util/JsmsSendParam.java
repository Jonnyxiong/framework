package com.jsmsframework.sms.send.util;

import com.alibaba.fastjson.JSON;
import com.jsmsframework.common.constant.SysConstant;
import com.jsmsframework.common.entity.JsmsParam;
import com.jsmsframework.common.service.JsmsParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class JsmsSendParam {
    private Logger logger = LoggerFactory.getLogger(JsmsSendParam.class);
    @Autowired
    private JsmsParamService jsmsParamService;

    public Integer getSendCFG(TimeSendCFG timeSendCFG) {
        if (timeSendCFG == null){
            throw new IllegalArgumentException("无效参数 TimeSendCFG 不能为null");
        }
        List<JsmsParam> paramList=jsmsParamService.getByParamKey(SysConstant.TIMER_SEND_CFG);
        logger.debug("系统参数定时短信发送配置 --> {}", JSON.toJSONString(paramList));
        if(!paramList.isEmpty()){
            JsmsParam param=paramList.get(0);
            String[] paramArray=param.getParamValue().split(";");
            if(paramArray.length!=6){
                throw new IllegalArgumentException("系统参数定时短信发送配置错误");
            }
            return Integer.parseInt(paramArray[timeSendCFG.getValue()]);
        }
        throw new IllegalArgumentException("系统参数定时短信发送配置错误：未找到相关配置");
    }

    public enum TimeSendCFG{
        发送最小间隔(4),
        取消发送最小间隔(5);
        private int value;
        TimeSendCFG(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
