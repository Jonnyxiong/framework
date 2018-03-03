package com.jsmsframework.access.enums;

/**
  * @Description:
  * @Author: tanjiangqiang
  * @Date: 2017/12/6 - 21:00
  *
  */
public enum AccessStateEnum {

    //'状态，0：未发送，1：提交成功，3：明确成功，4：提交失败，5：提交审核模块失败，6：明确失败，7：审核不通过，8：网关接收拦截，9：网关发送拦截，10：超频拦截',

    未发送(0,"未发送"),
    提交成功(1,"提交成功"),
    明确成功(3,"明确成功"),
    提交失败(4,"提交失败"),
    提交审核模块失败(5,"提交审核模块失败"),
    明确失败(6,"明确失败"),
    审核不通过(7,"审核不通过"),
    网关接收拦截(8,"网关接收拦截"),
    网关发送拦截(9,"网关发送拦截"),
    超频拦截(10,"超频拦截");


    private Integer value;
    private String desc;

    AccessStateEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
