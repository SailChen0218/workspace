package com.ezddd.core.response;

public class AppResult<T> extends AbstractResult {
    private static final long serialVersionUID = -4144208384093622174L;
    /**
     * 业务编号
     */
    private String bizCode;

    /**
     * 业务明细编号
     */
    private String bizDetailCode;

    /**
     * 消息代码
     */
    private String msgCode;

    /**
     * 提示信息
     */
    private String message;

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
    public static <T> AppResult<T> valueOfSuccess(T value) {
        AppResult<T> result = new AppResult<>();
        result.value = value;
        result.success = true;
        return result;
    }

    /**
     * @Title: valueOfSuccess
     * @Description: 生成成功结果
     * @return 结果Dto对象
     */
    public static <T> AppResult<T> valueOfSuccess() {
        return valueOfSuccess(null);
    }

    /**
     * @Title: valueOfError
     * @Description: 生成失败结果
     * @param msgCode 消息代码
     * @param msgParam 消息参数
     * @return 结果Dto对象
     */
    public static <T> AppResult<T> valueOfError(String msgCode, String... msgParam) {
        AppResult<T> result = new AppResult<T>();
        result.success = false;
        result.msgCode = msgCode;
        result.message = getMessageByCode(msgCode, msgParam);
        return result;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getBizDetailCode() {
        return bizDetailCode;
    }

    public void setBizDetailCode(String bizDetailCode) {
        this.bizDetailCode = bizDetailCode;
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

    private static String getMessageByCode(String msgCode, String... msgParam) {
        //TODO: get message
        String message = "";
        return message;
    }
}
