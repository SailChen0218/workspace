package com.ezshop.test;

import java.io.Serializable;
import java.util.*;

public class ResultDto<T> implements Serializable {

    private static final long serialVersionUID = -7178251882844874880L;

    private static final int DEFAULT_ERROR_CODE= 4433;
    /**
     * 提示信息
     */
    private String message;

    /**
     * 返回结果值
     */
    private T value;

    /**
     * 成功失败标识(true/false)
     */
    private boolean success;

    /**
     * 消息代码
     */
    private String msgCode;

    /**
     * 其他结果值Map
     */
    private Map<Object, Object> resultMap;

    /**
     * 消息提示参数
     */
    private List<String> msgParams;

    /**
     * 交易流水号
     */
    private String jylsh;


    private ResultDto() {

    }

    /**
         * @Title: valueOfSuccess
         * @Description: 生成成功结果
    	 * @param value 结果值
    	 * @return 结果Dto对象
         */
    public static <T> ResultDto<T> valueOfSuccess(T value) {
        ResultDto<T> vo = new ResultDto<T>();
        vo.value = value;
        vo.success = true;
        return vo;
    }

    /**
     * @Title: valueOfSuccess
     * @Description: 生成成功结果
     * @return 结果Dto对象
     */
    public static <T> ResultDto<T> valueOfSuccess() {
        return valueOfSuccess(null);
    }


    /**
     * @Title: valueOfError
     * @Description: 生成失败结果
     * @param msg 消息内容
     * @param msgCode 消息代码
     * @param msgParam
     * @return 结果Dto对象
     */
    public static <T> ResultDto<T> valueOfError(String msg, String msgCode, String... msgParam) {
        ResultDto<T> vo = new ResultDto<T>();
        vo.message = msg;
        vo.success = false;
        vo.msgCode = msgCode;
        vo.addMsgParams(msgParam);
        return vo;
    }

    /**
     * @Title: valueOfError
     * @Description: 生成失败结果
     * @param msg 消息内容
     * @return 结果Dto对象
     */
    public static <T> ResultDto<T> valueOfError(String msg) {
        return valueOfError(msg, DEFAULT_ERROR_CODE, null);
    }

    /**
     * @Title: valueOfError
     * @Description: 生成失败结果
     * @param msg 消息内容
     * @param msgCode 消息代码
     * @return 结果Dto对象
     */
    public static <T> ResultDto<T> valueOfError(String msg, int msgCode, String... msgParam) {
        return valueOfError(msg, msgCode + "", msgParam);
    }

    /**
     * @Title: valueOfError
     * @Description: 生成失败结果
     * @param msg 消息内容
     * @param msgCode 消息代码
     * @return 结果Dto对象
     */
    public static <T> ResultDto<T> valueOfError(String msg, String msgCode) {
        return valueOfError(msg, msgCode, null);
    }

    /**
         * @Title: addResult
         * @Description: 增加返回结果值
    	 * @param key 结果键值
    	 * @param value 结果值
         */
    public void addResult(String key, Object value) {
        this.getResultMap().put(key, value);
    }

    /**
         * @Title: getResult
         * @Description: 获取返回结果
    	 * @param key 结果键值
    	 * @return 结果值
         */
    @SuppressWarnings("unchecked")
    public <E> E getResult(String key) {
        return (E) this.getResultMap().get(key);
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the value
     */
    public T getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the msgCode
     */
    public String getMsgCode() {
        return msgCode;
    }

    /**
     * @param msgCode the msgCode to set
     */
    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    /**
     * @return the resultMap
     */
    public Map<Object, Object> getResultMap() {
        if (this.resultMap == null) {
            this.resultMap = new HashMap<Object, Object>();
        }
        return resultMap;
    }

    /**
     * @param resultMap the resultMap to set
     */
    public void setResultMap(Map<Object, Object> resultMap) {
        this.resultMap = resultMap;
    }

    public void addMsgParams(String... param) {
        if (msgParams == null) {
            msgParams = new ArrayList<String>();
        }
        if (param == null || param.length == 0) {
            return;
        }
        msgParams.addAll(Arrays.asList(param));
    }

    /**
     * @return the msgParams
     */
    public List<String> getMsgParams() {
        return msgParams;
    }

    /**
     * @param msgParams the msgParams to set
     */
    public void setMsgParams(List<String> msgParams) {
        this.msgParams = msgParams;
    }

}
