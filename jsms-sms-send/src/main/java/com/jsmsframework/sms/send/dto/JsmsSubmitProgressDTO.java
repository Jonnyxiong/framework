package com.jsmsframework.sms.send.dto;

import com.jsmsframework.common.enums.SmsTypeEnum;
import com.jsmsframework.sms.send.entity.JsmsSubmitProgress;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by xiongfenglin on 2018/1/18.
 *
 * @author: xiongfenglin
 */
public class JsmsSubmitProgressDTO extends JsmsSubmitProgress implements Serializable{
    //创建者
    private String creator;
    //任务进度
    private String taskSchedule;
    //短信类型
    private String  smstypeStr;

    public String getCreator() {
        return creator;
    }

    private Integer rowNum;
    //计费总数
    private Integer chargeNumTotal;
    //创建时间
    private String createTimeStr;
    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getTaskSchedule() {
        if(getSubmitTotal() != null && getSubmitTotal() > 0 && getActualSubmit() != null ){
            taskSchedule = new BigDecimal(getActualSubmit()).divide(new BigDecimal(getSubmitTotal()),2,BigDecimal.ROUND_DOWN).multiply(new BigDecimal("100")).setScale(0,BigDecimal.ROUND_DOWN).toString();
        }else{
            taskSchedule = "0";
        }
        return taskSchedule;
    }

    public void setTaskSchedule(String taskSchedule) {
        this.taskSchedule = taskSchedule;
    }

    public String getSmstypeStr() {
        smstypeStr = SmsTypeEnum.getDescByValue(getSmstype());
        return smstypeStr;
    }

    public void setSmstypeStr(String smstypeStr) {
        this.smstypeStr = smstypeStr;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public Integer getChargeNumTotal() {
        if(getChargeNum() != null && getSubmitTotal() != null){
            chargeNumTotal = getSubmitTotal() * getChargeNum();
        }else {
            chargeNumTotal = 0;
        }
        return chargeNumTotal;
    }

    public void setChargeNumTotal(Integer chargeNumTotal) {
        this.chargeNumTotal = chargeNumTotal;
    }

    public String getCreateTimeStr() {
        if(getCreateTime() != null){
            createTimeStr = DateFormatUtils.format(getCreateTime(), "yyyy-MM-dd HH:mm:ss");
        }
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
}
