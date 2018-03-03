package com.jsmsframework.sms.send.dto;

/**
 * Created by xiongfenglin on 2018/1/19.
 *
 * @author: xiongfenglin
 */
public class JsmsAccessSmsDTO {
    //文件类型，0：号码池(页面输入)，1：xlsx或xls格式导入，2：txt格式导入,3：csv格式导入
    private Integer fileType;
    //计费条数，签名+短信内容）对应的计费条数，小于等于70字算一条，大于70字按67字拆分计算条数
    private Integer chargeNum;
    //签名
    private String sign;
    //总提交号码数
    private Integer submitTotal;
    //格式错误号码数
    private Integer errNum;
    //重复号码数
    private Integer repeatNum;
    //导入的文件路径，对应文件服务器中保存的路径file_type为1、2、3时必填
    private String importFilePath;
    //内容
    private String content;
    // 短信类型
    private String smstype;
    // 客户id
    private String clientId;
    // 手机号
    private String mobile;

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public Integer getChargeNum() {
        return chargeNum;
    }

    public void setChargeNum(Integer chargeNum) {
        this.chargeNum = chargeNum;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getSubmitTotal() {
        return submitTotal;
    }

    public void setSubmitTotal(Integer submitTotal) {
        this.submitTotal = submitTotal;
    }

    public Integer getErrNum() {
        return errNum;
    }

    public void setErrNum(Integer errNum) {
        this.errNum = errNum;
    }

    public Integer getRepeatNum() {
        return repeatNum;
    }

    public void setRepeatNum(Integer repeatNum) {
        this.repeatNum = repeatNum;
    }

    public String getImportFilePath() {
        return importFilePath;
    }

    public void setImportFilePath(String importFilePath) {
        this.importFilePath = importFilePath;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSmstype() {
        return smstype;
    }

    public void setSmstype(String smstype) {
        this.smstype = smstype;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
