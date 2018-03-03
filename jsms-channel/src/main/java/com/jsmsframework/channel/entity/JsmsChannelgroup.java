package com.jsmsframework.channel.entity;

import java.util.Date;

/**
 * @description 通道组明细表
 * @author huangwenjie
 * @date 2017-09-20
 */
public class JsmsChannelgroup {
    
    // 通道组id
    private Integer channelgroupid;
    // 通道组名称
    private String channelgroupname;
    // 运营商类型，0：全网，1：移动，2：联通，3：电信，4：国际
    private Integer operater;
    // 标志，0：云通道对应通道组 1：普通通道组
    private String flag;
    // 状态：1:正常, 2:关闭
    private Integer status;
    // 备注
    private String remarks;
    // 更新时间
    private Date updateDate;
    
    public Integer getChannelgroupid() {
        return channelgroupid;
    }
    
    public void setChannelgroupid(Integer channelgroupid) {
        this.channelgroupid = channelgroupid ;
    }
    
    public String getChannelgroupname() {
        return channelgroupname;
    }
    
    public void setChannelgroupname(String channelgroupname) {
        this.channelgroupname = channelgroupname ;
    }
    
    public Integer getOperater() {
        return operater;
    }
    
    public void setOperater(Integer operater) {
        this.operater = operater ;
    }
    
    public String getFlag() {
        return flag;
    }
    
    public void setFlag(String flag) {
        this.flag = flag ;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status ;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks ;
    }
    
    public Date getUpdateDate() {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate ;
    }
    
}