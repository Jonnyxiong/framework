package com.jsmsframework.common.entity;

import java.util.Date;

/**
 * @description 平台错误码对应表
 * @author huangwenjie
 * @date 2017-11-25
 */
public class JsmsSystemErrorDesc {
    
    // 序号
    private Integer id;
    // SMSP平台错误码，唯一
    private String syscode;
    // 返回客户端错误类型，1：应答，2：状态报告
    private Integer type;
    // 对应数据库状态
    private Integer state;
    // 平台错误描述
    private String errordesc;
    // 
    private String clientSideNote;
    // 适用协议，1：全部，2：四大直连，3：CMPP，4：SGIP，5：SMGP，7：SMPP，8：HTTP
    private Integer usreprotocol;
    // 组件类型，00：smsp_c2s，01：smsp_access，02：smsp_send
    private String componentType;
    // 备注
    private String remark;
    // 更新时间
    private Date updatetime;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id ;
    }
    
    public String getSyscode() {
        return syscode;
    }
    
    public void setSyscode(String syscode) {
        this.syscode = syscode ;
    }
    
    public Integer getType() {
        return type;
    }
    
    public void setType(Integer type) {
        this.type = type ;
    }
    
    public Integer getState() {
        return state;
    }
    
    public void setState(Integer state) {
        this.state = state ;
    }
    
    public String getErrordesc() {
        return errordesc;
    }
    
    public void setErrordesc(String errordesc) {
        this.errordesc = errordesc ;
    }
    
    public String getClientSideNote() {
        return clientSideNote;
    }
    
    public void setClientSideNote(String clientSideNote) {
        this.clientSideNote = clientSideNote ;
    }
    
    public Integer getUsreprotocol() {
        return usreprotocol;
    }
    
    public void setUsreprotocol(Integer usreprotocol) {
        this.usreprotocol = usreprotocol ;
    }
    
    public String getComponentType() {
        return componentType;
    }
    
    public void setComponentType(String componentType) {
        this.componentType = componentType ;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark ;
    }
    
    public Date getUpdatetime() {
        return updatetime;
    }
    
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime ;
    }
    
}