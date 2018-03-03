package com.jsmsframework.common.enums;

public enum CodeEnum {
    /**通用错误码**/
    //操作成功
    SUCCESS(0),
    //操作失败
    FAIL(1),

    /**用户模块错误码，1开头五位数**/
    //用户创建失败
    USER_CREATE_FAIL(10000),
    //用户删除失败
    USER_DELETE_FAIL(10001),;

    private Integer value;


    CodeEnum(Integer value) {
        this.value = value;
    }


    public Integer getValue() {
        return value;
    }


    @Override
    public String toString() {
        return value.toString();
    }
}
