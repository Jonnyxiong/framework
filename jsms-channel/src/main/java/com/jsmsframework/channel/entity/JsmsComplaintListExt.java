package com.jsmsframework.channel.entity;

/**
 * created by xiaoqingwen on 2018/1/10 12:15
 */
public class JsmsComplaintListExt extends JsmsComplaintList {
    //客户名称
    private String name;
    //用户实际名称
    private String realname;
    //通道名称
    private String channelname;
    //操作者
    private String operatorStr;
    //发送时间
    private String sendTimeStr;
    //创建时间
    private String createTimeStr;
    //短信类型，0：通知短信，4：验证码短信，5：营销短信，6：告警短信，7：USSD，8：闪信,投诉记录类型type为通道投诉的时候可为空
    private String smsTypeStr;
    //通道运营商类型0：全网1：移动2：联通3：电信4：国际,投诉记录类型type为通道投诉的时候可为空
    private String operatorstypeStr;

    public String getOperatorstypeStr() {
        return operatorstypeStr;
    }

    public void setOperatorstypeStr(String operatorstypeStr) {
        this.operatorstypeStr = operatorstypeStr;
    }

    public String getSmsTypeStr() {
        return smsTypeStr;
    }

    public void setSmsTypeStr(String smsTypeStr) {
        this.smsTypeStr = smsTypeStr;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getSendTimeStr() {
        return sendTimeStr;
    }

    public void setSendTimeStr(String sendTimeStr) {
        this.sendTimeStr = sendTimeStr;
    }

    public String getOperatorStr() {
        return operatorStr;
    }

    public void setOperatorStr(String operatorStr) {
        this.operatorStr = operatorStr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getChannelname() {
        return channelname;
    }

    public void setChannelname(String channelname) {
        this.channelname = channelname;
    }
}
