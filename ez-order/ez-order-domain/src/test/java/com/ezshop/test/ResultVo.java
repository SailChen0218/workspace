package com.ezshop.test;

import java.io.Serializable;
import java.util.HashMap;

public class ResultVo implements Serializable {

    private static final long serialVersionUID = -4214236501903574966L;
    private String message;
    private Object value;
    private boolean success;
    private int msgCode;
    private HashMap resultMap;

    private ResultVo() {
    }

    public static ResultVo valueOfSuccess(Object value) {
        ResultVo vo = new ResultVo();
        vo.value = value;
        vo.success = true;
        return vo;
    }

    public static ResultVo valueOfSuccess() {
        return valueOfSuccess(null);
    }

    public static ResultVo valueOfError(String msg, Object value) {
        return valueOfError(msg, 0, null, value);
    }

    /**
     * 返回一个用于容纳错误信息的ResultVo
     *
     * @param msg
     *            错误信息内容
     * @param msgCode
     *            错误信息代码
     * @param source
     *            错误来源的类名，如果传递了这个参数，会调用Log4j记录一个warn信息
     * @param value
     *            其它要返回的类实例
     * @return
     */
    public static ResultVo valueOfError(String msg, int msgCode, Class source, Object value) {
//        if (source != null) {
//            Log log = LogMgr.getLog(source);
//            log.warn(msg);
//        }
        ResultVo vo = new ResultVo();
        vo.value = value;
        vo.message = msg;
        vo.success = false;
        vo.msgCode = msgCode;
        return vo;
    }

    public static ResultVo valueOfError(String msg) {
        return valueOfError(msg, 0, null, null);
    }

    public static ResultVo valueOfError(String msg, int msgCode) {
        return valueOfError(msg, msgCode, null, null);
    }

    public String getMessage() {
        return message;
    }

    public ResultVo setMessage(String message) {
        this.message = message;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public ResultVo setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public ResultVo setValue(Object value) {
        this.value = value;
        return this;
    }

    public int getMsgCode() {
        return msgCode;
    }

    public ResultVo setMsgCode(int msgCode) {
        this.msgCode = msgCode;
        return this;
    }

    public HashMap getResultMap() {
        return resultMap;
    }

    public void setResultMap(HashMap resultMap) {
        this.resultMap = resultMap;
    }
}
