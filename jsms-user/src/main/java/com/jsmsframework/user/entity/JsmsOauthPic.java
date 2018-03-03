package com.jsmsframework.user.entity;

import java.util.Date;

/**
 * @description 代理商和客户证件表
 * @author huangwenjie
 * @date 2017-08-16
 */
public class JsmsOauthPic {
    
    // 唯一id ,自增长
    private Integer id;
    // 代理商id
    private Integer agentId;
    // 用户帐号
    private String clientId;
    // 证件号码
    private String idNbr;
    // 证件照图片
    private String imgUrl;
    // 证件类型，1：身份证(11：身份证正面,10：身份证背面)，2：护照，3：组织机构证，4：税务登记证，5：营业执照，6：三证合一(企业)，7：四证合一(企业)，8：登记证书号
    private Integer idType;
    // 显示顺序
    private Integer idOrder;
    // 描述
    private String idDesc;
    // 创建时间
    private Date createDate;
    // 更新时间
    private Date updateDate;
    // 状态，0：失效，1：有效
    private Integer status;
    // 组织机构代码(仅三证合一有)
    private String idNbr2;
    // 税务登记证件号码(仅三证合一有)
    private String idNbr3;
    // 远程路径
    private String remotePath;
    // 认证类型： 1、代理商资质认证 2、客户资质认证
    private Integer oauthType;
    
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
    
    public String getIdNbr() {
        return idNbr;
    }
    
    public void setIdNbr(String idNbr) {
        this.idNbr = idNbr ;
    }
    
    public String getImgUrl() {
        return imgUrl;
    }
    
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl ;
    }
    
    public Integer getIdType() {
        return idType;
    }
    
    public void setIdType(Integer idType) {
        this.idType = idType ;
    }
    
    public Integer getIdOrder() {
        return idOrder;
    }
    
    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder ;
    }
    
    public String getIdDesc() {
        return idDesc;
    }
    
    public void setIdDesc(String idDesc) {
        this.idDesc = idDesc ;
    }
    
    public Date getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate ;
    }
    
    public Date getUpdateDate() {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate ;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status ;
    }
    
    public String getIdNbr2() {
        return idNbr2;
    }
    
    public void setIdNbr2(String idNbr2) {
        this.idNbr2 = idNbr2 ;
    }
    
    public String getIdNbr3() {
        return idNbr3;
    }
    
    public void setIdNbr3(String idNbr3) {
        this.idNbr3 = idNbr3 ;
    }
    
    public String getRemotePath() {
        return remotePath;
    }
    
    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath ;
    }
    
    public Integer getOauthType() {
        return oauthType;
    }
    
    public void setOauthType(Integer oauthType) {
        this.oauthType = oauthType ;
    }
    
}