package com.jsmsframework.common.enums;

/**
 * dto通用状态码
 */
public enum Code {
    /**通用错误码**/
    //操作成功
    SUCCESS(0),
    /**
     * @attention 请求重定向 3XX
     * @attention 使用该状态码必须返回重定向的URL
     */
    REDIRECT(300),
    /**
     * @attention 操作错误 4XX
     * 业务异常
     */
    OPT_ERR(400),
    /**
     * 未授权
     */
    OPT_ERR_UNAUTHORIZED(401),
    /**
     * 操作不被允许
     */
    OPT_ERR_FORBIDDEN(403),
    /**
     * 未找到记录|数据
     */
    OPT_ERR_NOT_FOUND(404),
    /**
     * 方法被禁止
     */
    OPT_ERR_NOT_ALLOWED(405),
    /**
     * 超时
     */
    OPT_ERR_TIMEOUT(408),
    /**
     * @attention 系统内部错误 5XX
     * 程序内部异常
     */
    SYS_ERR(500),
    /**
     * @attention 系统解析错误 501
     */
    SYS_ERR_PARSE(501);

    private Integer value;


    Code(Integer value) {
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
