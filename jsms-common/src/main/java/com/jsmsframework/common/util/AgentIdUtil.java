package com.jsmsframework.common.util;

import com.jsmsframework.common.dto.JsmsStaticInitVariable;
import com.ucpaas.sms.common.util.DateUtils;

import java.util.Date;

/**
 * Created by xiongfenglin on 2017/11/25.
 *
 * @author: xiongfenglin
 */
public class AgentIdUtil {
    public static synchronized int getAgentId() {
        if(JsmsStaticInitVariable.AGENT_NUM == -1){
            throw  new IllegalStateException("给OEM代理商开户时未初始化ID");
        }
        if(JsmsStaticInitVariable.AGENTID_PRE == null){
            throw  new IllegalStateException("给OEM代理商开户时未初始化前缀");
        }
        Date date = new Date();
        int num = 0;
        String agentIdPre = DateUtils.formatDate(date, "yyyyMM");
        if (agentIdPre.equals(JsmsStaticInitVariable.AGENTID_PRE)) {
            num = JsmsStaticInitVariable.AGENT_NUM;
            JsmsStaticInitVariable.AGENT_NUM = num + 1;
        } else {
            JsmsStaticInitVariable.AGENTID_PRE = agentIdPre;
            num = 1;
            JsmsStaticInitVariable.AGENT_NUM = num + 1;
        }
        // 拼接AgentId
        String agentId = agentIdPre + StringUtils.addZeroForNum(num, 4, "0");
        return Integer.valueOf(agentId);
    }
}
