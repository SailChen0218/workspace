package com.ezddd.core.response;

import com.ezddd.core.utils.MessageUtil;

import java.io.Serializable;
import java.text.MessageFormat;

public class AppResult<T> implements Serializable {
    private static final long serialVersionUID = -4144208384093622174L;

    /**
     * 返回值
     */
    private T value;

    /**
     * 消息代码
     */
    private String msgCode;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 错误内容
     */
    private String exceptionContent;

    /**
     * 成功失败标识(true/false)
     */
    private boolean success;

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @Title: valueOfSuccess
     * @Description: 生成成功结果
     * @param value 结果值
     * @return 结果Dto对象
     */
    public static <T> AppResult<T> valueOfSuccess(T value, String msgCode, Object... params) {
        AppResult<T> result = new AppResult<>();
        result.value = value;
        result.success = true;
        result.message = AppResult.getMessageByCode(msgCode, params);
        return result;
    }

//    /**
//     * @Title: valueOfSuccess
//     * @Description: 生成成功结果
//     * @param value 结果值
//     * @return 结果Dto对象
//     */
//    public static <T> AppResult<T> valueOfSuccess(T value, String msgCode) {
//        return AppResult.valueOfSuccess(value, msgCode, null);
//    }

    /**
     * @Title: valueOfSuccess
     * @Description: 生成成功结果
     * @return 结果Dto对象
     */
    public static <T> AppResult<T> valueOfSuccess(String msgCode, Object... params) {
        return AppResult.valueOfSuccess(null, msgCode, params);
    }

//    /**
//     * @Title: valueOfSuccess
//     * @Description: 生成成功结果
//     * @return 结果Dto对象
//     */
//    public static <T> AppResult<T> valueOfSuccess(String msgCode) {
//        return AppResult.valueOfSuccess(null, msgCode, null);
//    }


    /**
     * @Title: valueOfError
     * @Description: 生成失败结果
     * @param msgCode 消息代码
     * @param params 消息参数
     * @return 结果Dto对象
     */
    public static <T> AppResult<T> valueOfError(String msgCode, Object[] params) {
        AppResult<T> result = new AppResult<T>();
        result.success = false;
        result.msgCode = msgCode;
        result.message = getMessageByCode(msgCode, params);
        return result;
    }

    /**
     * @Title: valueOfError
     * @Description: 生成失败结果
     * @param msgCode 消息代码
     * @return 结果Dto对象
     */
    public static <T> AppResult<T> valueOfError(String msgCode) {
        AppResult<T> result = new AppResult<T>();
        result.success = false;
        result.msgCode = msgCode;
        result.message = getMessageByCode(msgCode, null);
        return result;
    }

    /**
     * @Title: valueOfError
     * @Description: 生成失败结果
     * @param msgCode 消息代码
     * @param params 消息参数
     * @return 结果Dto对象
     */
    public static <T> AppResult<T> valueOfError(String exceptionContent, String msgCode, Object... params) {
        AppResult<T> result = new AppResult<>();
        result.exceptionContent = exceptionContent;
        result.success = false;
        result.msgCode = msgCode;
        result.message = getMessageByCode(msgCode, params);
        return result;
    }

//    /**
//     * @Title: valueOfError
//     * @Description: 生成失败结果
//     * @param msgCode 消息代码
//     * @return 结果Dto对象
//     */
//    public static <T> AppResult<T> valueOfError(String exceptionContent, String msgCode) {
//        AppResult<T> result = new AppResult<>();
//        result.exceptionContent = exceptionContent;
//        result.success = false;
//        result.msgCode = msgCode;
//        result.message = getMessageByCode(msgCode, null);
//        return result;
//    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getExceptionContent() {
        return exceptionContent;
    }

    public void setExceptionContent(String exceptionContent) {
        this.exceptionContent = exceptionContent;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    private static String getMessageByCode(String msgCode, Object... params) {
        return MessageUtil.getMessage(msgCode, params);
    }
}
