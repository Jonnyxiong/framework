package com.jsmsframework.sms.send.entity;

import java.util.Date;

/**
 * @description 短信提交进度表
 * @author Don
 * @date 2018-01-18
 */
public class JsmsSubmitProgress {
    
    // 业务单号，自增序列id
    private Integer id;
    // 代理商id
    private Integer agentId;
    // 客户id
    private String clientId;
    // 文件类型，0：号码池(页面输入)，1：xlsx或xls格式导入，2：txt格式导入,3：csv格式导入
    private Integer fileType;
    // 提交状态，0：未提交(初始状态)，1：提交中，2：提交完成，3：提交失败(文件解析失败、账号状态异常、账户可用额度不足)
    private Integer submitState;
    // 计费条数，签名+短信内容）对应的计费条数，小于等于70字算一条，大于70字按67字拆分计算条数
    private Integer chargeNum;
    // 短信类型，0：通知短信，4：验证码短信，5：营销短信，6：告警短信，7：USSD，8：闪信
    private Integer smstype;
    // 签名
    private String sign;
    // 短信内容
    private String content;
    // 总提交号码数(正确号码)
    private Integer submitTotal;
    // 实际提交号码数(提交到后台)
    private Integer actualSubmit;
    // 格式错误号码数
    private Integer errNum;
    // 重复号码数
    private Integer repeatNum;
    // 提交开始时间
    private Date submitBeginTime;
    // 提交结束时间
    private Date submitEndTime;
    // 导入的文件路径，对应文件服务器中保存的路径file_type为1、2、3时必填
    private String importFilePath;
    // 提交类型，0：子客户提交，1：代理商提交
    private Integer submitType;
    // 创建时间
    private Date createTime;
    //创建者
    private String adminId;
    // 备注
    private String remark;

    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
    }
    
    public Integer getAgentId() {
        return agentId;
    }
    
    public void setAgentId(Integer agentId) {
        this.agentId = agentId ;
    }
    
    public String getClientId() {
        return clientId;
    }
    
    public void setClientId(String clientId) {
        this.clientId = clientId ;
    }
    
    public Integer getFileType() {
        return fileType;
    }
    
    public void setFileType(Integer fileType) {
        this.fileType = fileType ;
    }
    
    public Integer getSubmitState() {
        return submitState;
    }
    
    public void setSubmitState(Integer submitState) {
        this.submitState = submitState ;
    }

    public Integer getChargeNum() {
        return chargeNum;
    }

    public void setChargeNum(Integer chargeNum) {
        this.chargeNum = chargeNum;
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
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content ;
    }
    
    public Integer getSubmitTotal() {
        return submitTotal;
    }
    
    public void setSubmitTotal(Integer submitTotal) {
        this.submitTotal = submitTotal ;
    }
    
    public Integer getActualSubmit() {
        return actualSubmit;
    }
    
    public void setActualSubmit(Integer actualSubmit) {
        this.actualSubmit = actualSubmit ;
    }
    
    public Integer getErrNum() {
        return errNum;
    }
    
    public void setErrNum(Integer errNum) {
        this.errNum = errNum ;
    }
    
    public Integer getRepeatNum() {
        return repeatNum;
    }
    
    public void setRepeatNum(Integer repeatNum) {
        this.repeatNum = repeatNum ;
    }
    
    public Date getSubmitBeginTime() {
        return submitBeginTime;
    }
    
    public void setSubmitBeginTime(Date submitBeginTime) {
        this.submitBeginTime = submitBeginTime ;
    }
    
    public Date getSubmitEndTime() {
        return submitEndTime;
    }
    
    public void setSubmitEndTime(Date submitEndTime) {
        this.submitEndTime = submitEndTime ;
    }
    
    public String getImportFilePath() {
        return importFilePath;
    }
    
    public void setImportFilePath(String importFilePath) {
        this.importFilePath = importFilePath ;
    }
    
    public Integer getSubmitType() {
        return submitType;
    }
    
    public void setSubmitType(Integer submitType) {
        this.submitType = submitType ;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime ;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark ;
    }
    
}