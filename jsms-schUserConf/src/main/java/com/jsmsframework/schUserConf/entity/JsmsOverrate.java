package com.jsmsframework.schUserConf.entity;

import java.util.Date;

/**
 * @description 模板超频表
 * @author huangwenjie
 * @date 2017-09-27
 */
public class JsmsOverrate {
    
    // 主键
    private Integer id;
    // 用户id
    private String userid;
    // 超频统计方式，0：按短信类型统计，1：按关键字统计
    private Integer overrateMode;
    // 短信类型，-1：不区分短信类型（overrate_mode=1时使用），0：通知短信，4：验证码短信，5：营销短信，6：告警短信，7：USSD，8：闪信
    private Integer smstype;
    // 短信签名，签名为*，此时为用户账号关键字超频处理逻辑,用户账号级别
    private String sign;
    // 秒频率阈值的次数
    private Integer overRateNumS;
    // 秒频率阈值的时间
    private Integer overRateTimeS;
    // 分钟频率阈值的次数
    private Integer overRateNumM;
    // 分钟频率阈值的时间
    private Integer overRateTimeM;
    // 小时频率阈值的次数
    private Integer overRateNumH;
    // 小时频率阈值的时间
    private Integer overRateTimeH;
    // 超频参数修改时间
    private Date overRateUpdatetime;

    private Integer state;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
    }
    
    public String getUserid() {
        return userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid ;
    }
    
    public Integer getOverrateMode() {
        return overrateMode;
    }
    
    public void setOverrateMode(Integer overrateMode) {
        this.overrateMode = overrateMode ;
    }
    
    public Integer getSmstype() {
        return smstype;
    }
    
    public void setSmstype(Integer smstype) {
        this.smstype = smstype ;
    }
    
    public String getSign() {
        return sign;
    }
    
    public void setSign(String sign) {
        this.sign = sign ;
    }
    
    public Integer getOverRateNumS() {
        return overRateNumS;
    }
    
    public void setOverRateNumS(Integer overRateNumS) {
        this.overRateNumS = overRateNumS ;
    }
    
    public Integer getOverRateTimeS() {
        return overRateTimeS;
    }
    
    public void setOverRateTimeS(Integer overRateTimeS) {
        this.overRateTimeS = overRateTimeS ;
    }
    
    public Integer getOverRateNumM() {
        return overRateNumM;
    }
    
    public void setOverRateNumM(Integer overRateNumM) {
        this.overRateNumM = overRateNumM ;
    }
    
    public Integer getOverRateTimeM() {
        return overRateTimeM;
    }
    
    public void setOverRateTimeM(Integer overRateTimeM) {
        this.overRateTimeM = overRateTimeM ;
    }
    
    public Integer getOverRateNumH() {
        return overRateNumH;
    }
    
    public void setOverRateNumH(Integer overRateNumH) {
        this.overRateNumH = overRateNumH ;
    }
    
    public Integer getOverRateTimeH() {
        return overRateTimeH;
    }
    
    public void setOverRateTimeH(Integer overRateTimeH) {
        this.overRateTimeH = overRateTimeH ;
    }
    
    public Date getOverRateUpdatetime() {
        return overRateUpdatetime;
    }
    
    public void setOverRateUpdatetime(Date overRateUpdatetime) {
        this.overRateUpdatetime = overRateUpdatetime ;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }
}