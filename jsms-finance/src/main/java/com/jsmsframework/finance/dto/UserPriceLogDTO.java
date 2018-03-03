package com.jsmsframework.finance.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.jsmsframework.common.enums.SMSType;
import com.jsmsframework.finance.entity.JsmsUserPriceLog;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by smile on 2017/8/9.
 */
public class UserPriceLogDTO extends JsmsUserPriceLog {


    private String clientid; // 用户帐号，关联t_sms_account表中clientid字段
    private Integer smstype; // 短信类型，0：通知短信，4：验证码短信，5：营销短信，6：告警短信，7：USSD，8：闪信
    private Date effectDate; // 生效日期
    private Integer chargeMode; // 计费模式，0：提交成功计费（用户侧：1+3+6）1：发送成功计费（用户侧：1+3）2：明确成功计费（用户侧：3）
    private Date createTime; // 创建时间
    private Date updateTime; // 修改时间
    private String smstypeDesc;
    private String effectDateStr;

    public UserPriceLogDTO() {
        super();
    }

    public UserPriceLogDTO(Integer id) {
        this.setId(id);
    }


    @Length(min = 1, max = 6, message = "用户帐号，关联t_sms_account表中clientid字段长度必须介于 1 和 6 之间")
    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    @NotNull(message = "短信类型，0：通知短信，4：验证码短信，5：营销短信，6：告警短信，7：USSD，8：闪信不能为空")
    public Integer getSmstype() {
        return smstype;
    }

    public void setSmstype(Integer smstype) {
        this.smstype = smstype;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "生效日期不能为空")
    public Date getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(Date effectDate) {
        this.effectDate = effectDate;
    }

    @NotNull(message = "计费模式，0：提交成功计费（用户侧：1+3+6）1：发送成功计费（用户侧：1+3）2：明确成功计费（用户侧：3）不能为空")
    public Integer getChargeMode() {
        return chargeMode;
    }

    public void setChargeMode(Integer chargeMode) {
        this.chargeMode = chargeMode;
    }


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "创建时间不能为空")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getEffectDateStr() {
        return effectDateStr;
    }

    public void setEffectDateStr(String effectDateStr) {
        this.effectDateStr = effectDateStr;
    }

    public String getSmstypeDesc() {
        smstypeDesc = null;
        if (smstype != null) {
            smstypeDesc = SMSType.getDescByValue(smstype);
        }
        return smstypeDesc;
    }

    public void setSmstypeDesc(String smstypeDesc) {
        this.smstypeDesc = smstypeDesc;
    }
}
