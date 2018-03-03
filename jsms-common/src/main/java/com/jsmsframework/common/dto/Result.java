package com.jsmsframework.common.dto;


import com.jsmsframework.common.enums.CodeEnum;
import com.jsmsframework.common.util.JsonUtil;

/**
 * 结果传递类Result，传递类不做改变
 *
 * @author huangwenjie
 * @create 2016-12-31-16:48
 */
public class Result<T> {
    private final boolean success;
    private final boolean fail;
    private final Integer code;
    /**
     * 携带的数据，可能是列表、分页对象等
     */
    private final T data;
    /**
     * 携带的消息，包括提示、日志之类的
     */
    private final String msg;

    public Result(boolean success, CodeEnum code, T data, String msg) {
        this.success = success;
        if(!success)
            this.fail = true;
        else
            this.fail = false;
        this.code = code.getValue();
        this.data = data;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isFail() {
        return fail;
    }

    public Integer getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public static void main(String[] args) {
        Result<String> result = new Result<>(true,CodeEnum.SUCCESS,"dataStr","操作成功");

        System.out.println(JsonUtil.toJson(result));
    }
}
