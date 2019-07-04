package com.ezshop.desensitize;

import com.ezshop.desensitize.dto.MethodResovingDto;

import java.lang.reflect.Method;

public interface DesensitizeMethodHolder {
    /**
     * 根据接口方法名称查找对应的脱敏方法对象
     *
     * @param interfaceMethodName 接口方法名称，例：gov.etax.dzswj.nsrzx.service.dj.INsrxxService.queryNsrxxByNsrsbh
     * @return
     */
    Method findMethodByName(String interfaceMethodName);

    /**
     * 添加接口方法对象
     *
     * @param interfaceMethodName
     * @param method
     * @return true:添加成功 false:方法重复
     */
    boolean addMethod(String interfaceMethodName, Method method);

    /**
     * 解析接口方法获取返回值类型和参数信息
     *
     * @param interfaceMethodName
     * @return
     */
    MethodResovingDto resolveMethod(String interfaceMethodName);
}
