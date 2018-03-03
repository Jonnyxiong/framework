package com.jsmsframework.common.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.jsmsframework.common.enums.Code;

/**
 * 结果传递类
 *
 * @author huangwenjie
 * @create 2016-12-31-16:48
 */
public class ResultVO<T> {
	private boolean success;
	private boolean fail;
	/**
	 * code --> 0:成功
	 */
	private Integer code;
	/**
	 * 携带的数据，可能是列表、分页对象等
	 */
	private T data;
	/**
	 * 携带的消息，包括提示、日志之类的
	 */
	private String msg;

	/**
	 * 初始化时，默认是失败
	 */
	public ResultVO() {
		setFail(true);
	}

	public String getMsg() {
		return msg;
	}

	public ResultVO setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public ResultVO setSuccess(boolean success) {
		this.success = success;
		if (success){
			this.fail = false;
			this.code = 0;

		}
		return this;
	}

	public boolean isFail() {
		return fail;
	}

	@Deprecated
	public void setFail(boolean fail) {
		this.fail = fail;
		if (fail){
			this.success = false;
			this.code = -1;
		}
	}

	public void setFail(Code code,boolean fail) {
		this.fail = fail;
		if (fail){
			this.success = false;
			setCode(code);

		}
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public static ResultVO successDefault(Object data) {
		ResultVO resultVO = new ResultVO();
		resultVO.setSuccess(true);
		resultVO.setData(data);
		resultVO.setMsg("操作成功！");
		resultVO.setCode(Code.SUCCESS);
		return resultVO;
	}

	public static ResultVO successDefault() {
		ResultVO resultVO = new ResultVO();
		resultVO.setSuccess(true);
		resultVO.setMsg("操作成功！");
		resultVO.setCode(Code.SUCCESS);
		return resultVO;
	}

	public static ResultVO successDefault(String msg) {
		ResultVO resultVO = new ResultVO();
		resultVO.setSuccess(true);
		resultVO.setMsg(msg);
		resultVO.setCode(Code.SUCCESS);
		return resultVO;
	}
	public static ResultVO successDefault(Code code,String msg) {
		ResultVO resultVO = new ResultVO();
		resultVO.setSuccess(true);
		resultVO.setMsg(msg);
		resultVO.setCode(code);
		return resultVO;
	}
	public static ResultVO successDefault(Code code,Object data) {
		ResultVO resultVO = new ResultVO();
		resultVO.setSuccess(true);
		resultVO.setData(data);
		resultVO.setMsg("操作成功！");
		resultVO.setCode(code);
		return resultVO;
	}

	@Deprecated
	public static ResultVO failure() {
		ResultVO resultVO = new ResultVO();
		resultVO.setSuccess(false);
		resultVO.setMsg("操作失败！");
		resultVO.setCode(-1);

		return resultVO;
	}

	@Deprecated
	public static ResultVO failure(String msg) {
		ResultVO resultVO = new ResultVO();
		resultVO.setSuccess(false);
		resultVO.setMsg(msg);
		resultVO.setCode(-1);
		return resultVO;
	}

	public static ResultVO failure(Code code,String msg) {
		ResultVO resultVO = new ResultVO();
		resultVO.setSuccess(false);
		resultVO.setMsg(msg);
		resultVO.setCode(code);
		return resultVO;
	}

	public boolean isFailure() {
		return fail;
	}

	public Integer getCode() {
		return code;
	}
	@Deprecated
	public void setCode(Integer code) {
		this.code = code;
	}
	public void setCode(Code code) {
		if(code != null){
			this.code = code.getValue();
		}
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("success", success)
				.append("fail", fail)
				.append("code", code)
				.append("data", data)
				.append("msg", msg)
				.toString();
	}
}
