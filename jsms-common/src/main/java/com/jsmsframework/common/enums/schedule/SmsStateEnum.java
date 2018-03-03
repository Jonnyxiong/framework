package com.jsmsframework.common.enums.schedule;

/**
 * 短信发送状态
 * @author yeshiyuan
 * @create 2018/1/12
 */
public enum SmsStateEnum {

    //0：未发送，1：提交成功，2：发送成功，3：明确成功，4：提交失败，5：发送失败，6：明确失败
    未发送(0,"未发送"),
    提交成功(1,"提交成功"),
    发送成功(2,"发送成功"),
    明确成功(3,"明确成功"),
    提交失败(4,"提交失败"),
    发送失败(5,"发送失败"),
    明确失败(6,"明确失败");

    private Integer value;
    private String name;

    SmsStateEnum(Integer value, String name){
        this.value = value;
        this.name = name;
    }

    public static String getDescByValue(Integer value) {
        if(value == null){ return null;}
        String result = null;
        for (SmsStateEnum s : SmsStateEnum.values()) {
            if (value.equals(s.getValue())) {
                result = s.getName();
                break;
            }
        }
        return result;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
