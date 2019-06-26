package com.ezshop.desensitize.impl;

import com.ezshop.desensitize.DesensitizeFailedException;
import com.ezshop.desensitize.DesensitizeProcessor;
import com.ezshop.desensitize.DesensitizedField;
import com.ezshop.desensitize.type.SensitiveType;
import com.ezshop.desensitize.type.SentitiveTypeFactory;
import com.ezshop.desensitize.util.ReflectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

@Component
public class DesensitizeProcessorImpl implements DesensitizeProcessor{

    @Autowired
    SentitiveTypeFactory sentitiveTypeFactory;

    /**
     * @param target
     * @throws DesensitizeFailedException
     */
    @Override
    public void desensitize(Object target, String qdbh, String jyfwbh) throws DesensitizeFailedException {
        processDesensitization("", "ROOT", target, qdbh, jyfwbh);
    }

    /**
     * 执行脱敏处理
     *
     * @param parentPath      父节点路径
     * @param currentNodeName 当前节点名称
     * @param currentNode     当前节点对象
     * @param qdbh            渠道编号
     * @param jyfwbh          交易服务编号
     * @throws DesensitizeFailedException
     */
    private void processDesensitization(String parentPath,
                                        String currentNodeName,
                                        Object currentNode,
                                        String qdbh,
                                        String jyfwbh) throws DesensitizeFailedException {
        try {
            String currentPath = "".equals(parentPath) ? currentNodeName : parentPath + "/" + currentNodeName;
            String currentFieldPath = null;
            Class<?> targetType = currentNode.getClass();
            List<Field> fieldList = ReflectionUtils.getFieldsFrom(targetType);
            for (Field field : fieldList) {
                currentFieldPath = currentPath + "/" + field.getName();
                System.out.println(currentFieldPath);
                Class<?> fieldType = field.getType();
                // 判断是否为引用类型
                if (!ReflectionUtils.isReferenceType(fieldType)) {
                    // 获取脱敏配置信息(0:不脱敏输出  1:脱敏输出  2:不输出)
                    int desensitizationConfig = getDesensitizationConfig(qdbh, jyfwbh, currentFieldPath);
                    if (desensitizationConfig == 1) {
                        DesensitizedField desensitizedField = field.getAnnotation(DesensitizedField.class);
                        // 判断是否带有DesensitizedField注解，且为String类型。
                        if (desensitizedField != null && String.class.equals(fieldType)) {
                            SensitiveType sensitiveType = sentitiveTypeFactory.getSensitiveType(desensitizedField.value());
                            if (sensitiveType != null) {
                                field.setAccessible(true);
                                Object value = field.get(currentNode);
                                if (value != null) {
                                    String desensitizedValue = sensitiveType.desensitized(value.toString());
                                    field.set(currentNode, desensitizedValue);
                                }
                            }
                        }
                    } else if (desensitizationConfig == 2) {
                        field.setAccessible(true);
                        field.set(currentNode, null);
                    }
                } else {
                    field.setAccessible(true);
                    Object value = field.get(currentNode);
                    if (value != null) {
                        processDesensitization(currentPath, field.getName(), value, qdbh, jyfwbh);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new DesensitizeFailedException(e);
        } catch (BeansException e) {
            throw new DesensitizeFailedException(e);
        }
    }

    /**
     * 根据渠道编号、交易服务编号、当前字段路径获取脱敏配置信息
     *
     * @param qdbh
     * @param jyfwbh
     * @param currentFieldPath
     * @return 0:不脱敏输出  1:脱敏输出  2:不输出
     */
    private int getDesensitizationConfig(String qdbh, String jyfwbh, String currentFieldPath) {
        //TODO
        return 1;
    }
}
