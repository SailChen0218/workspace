package com.ezshop.desensitize.impl;

import com.ezshop.desensitize.DesensitizeProcessor;
import com.ezshop.desensitize.DesensitizedField;
import com.ezshop.desensitize.SensitiveType;
import com.ezshop.desensitize.SentitiveTypeFactory;
import com.ezshop.desensitize.exception.DesensitizeFailedException;
import com.ezshop.desensitize.exception.SensitizeTypeNotExistsException;
import com.ezshop.desensitize.util.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class DesensitizeProcessorImpl implements DesensitizeProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(DesensitizeProcessorImpl.class);

    @Autowired
    SentitiveTypeFactory sentitiveTypeFactory;

//    @Autowired
//    DesensitizeConfigDao desensitizeConfigDao;

    /**
     * 脱敏处理入口
     *
     * @param target
     * @param channel
     * @param service
     * @throws DesensitizeFailedException
     */
    @Override
    public void desensitize(Object target, String channel, String service) throws DesensitizeFailedException {
        if (target == null) return;
        Map<String, Integer> dsensitizationConfigMap = getDesensitizationConfigMap(channel, service);
        Class<?> targetType = target.getClass();
        if (!ReflectionUtils.isReferenceType(targetType)) {
            return;
        }
        if (Collection.class.isAssignableFrom(targetType)) {
            Collection<Object> targetCollection = (Collection) target;
            if (targetCollection.size() > 0) {
                Iterator<Object> iterable = targetCollection.iterator();
                while (iterable.hasNext()) {
                    Object currentNode = iterable.next();
                    if (!ReflectionUtils.isReferenceType(currentNode.getClass())) {
                        return;
                    }
                    desensitizeNode("ROOT", currentNode, channel, service, dsensitizationConfigMap);
                }
            }
        } else {
            desensitizeNode("ROOT", target, channel, service, dsensitizationConfigMap);
        }
    }

    /**
     * 引用类型对象节点脱敏
     *
     * @param currentNodePath         当前节点路径
     * @param currentNode             当前节点对象
     * @param channel                 渠道编号
     * @param service                 交易服务编号
     * @param dsensitizationConfigMap 脱敏配置信息
     * @throws DesensitizeFailedException
     */
    private void desensitizeNode(String currentNodePath,
                                 Object currentNode,
                                 String channel,
                                 String service,
                                 Map<String, Integer> dsensitizationConfigMap) throws DesensitizeFailedException {
        if (currentNode == null) return;
        Class<?> targetType = currentNode.getClass();
        try {
            List<Field> fieldList = ReflectionUtils.getPropertyFieldsFrom(targetType);
            for (Field field : fieldList) {
                field.setAccessible(true);
                Object fieldValue = field.get(currentNode);
                if (fieldValue != null) {
                    String currentFieldPath = currentNodePath + "/" + field.getName();
                    Class<?> fieldType = field.getType();
                    if (Collection.class.isAssignableFrom(fieldType)) {
                        Collection<Object> targetCollection = (Collection) fieldValue;
                        if (targetCollection.size() > 0) {
                            Iterator<Object> iterable = targetCollection.iterator();
                            while (iterable.hasNext()) {
                                Object item = iterable.next();
                                desensitizeField(currentNode, field, item, item.getClass(),
                                        currentFieldPath, channel, service, dsensitizationConfigMap);
                            }
                        }
                    } else {
                        desensitizeField(currentNode, field, fieldValue, fieldType,
                                currentFieldPath, channel, service, dsensitizationConfigMap);
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("对象脱敏处理失败了。脱敏对象:{},脱敏路径:{}", targetType.getClass().getName(), currentNodePath, e);
            throw new DesensitizeFailedException("对象脱敏处理失败了。", e);
        }
    }

    /**
     * 对象属性字段脱敏
     *
     * @param currentNode
     * @param field
     * @param fieldValue
     * @param fieldType
     * @param currentFieldPath
     * @param channel
     * @param service
     * @param dsensitizationConfigMap
     * @throws IllegalAccessException
     */
    private void desensitizeField(Object currentNode,
                                  Field field,
                                  Object fieldValue,
                                  Class<?> fieldType,
                                  String currentFieldPath,
                                  String channel,
                                  String service,
                                  Map<String, Integer> dsensitizationConfigMap)
            throws IllegalAccessException, SensitizeTypeNotExistsException, DesensitizeFailedException {
        // 判断是否为引用类型
        if (!ReflectionUtils.isReferenceType(fieldType)) {
            // 获取脱敏配置信息(0:不脱敏输出  1:脱敏输出  2:不输出)
            int desensitizationConfig = getDesensitizationConfig(dsensitizationConfigMap, currentFieldPath);
            if (desensitizationConfig == 1) {
                DesensitizedField desensitizedField = field.getAnnotation(DesensitizedField.class);
                // 判断是否带有DesensitizedField注解，且为String类型。
                if (desensitizedField != null && String.class.equals(fieldType)) {
                    SensitiveType sensitiveType = sentitiveTypeFactory.getSensitiveType(desensitizedField.value());
                    if (sensitiveType != null) {
                        String desensitizedValue = sensitiveType.desensitized(fieldValue.toString());
                        field.setAccessible(true);
                        field.set(currentNode, desensitizedValue);
                    }
                }
            } else if (desensitizationConfig == 2) {
                field.setAccessible(true);
                field.set(currentNode, null);
            }
        } else {
            desensitizeNode(currentFieldPath, fieldValue, channel, service, dsensitizationConfigMap);
        }
    }

    /**
     * 获取脱敏配置信息
     *
     * @param channel
     * @param service
     * @return
     */
    private Map<String, Integer> getDesensitizationConfigMap(String channel, String service) {
//        List<DesensitizeConfigDto> desensitizeConfigDtos =
//                desensitizeConfigDao.queryDesensitizeConfig(channel, service);
//        if (desensitizeConfigDtos != null & desensitizeConfigDtos.size() > 0) {
//            Map<String, Integer> dsensitizationConfigMap = new HashMap<>(desensitizeConfigDtos.size());
//            for (DesensitizeConfigDto desensitizeConfigDto : desensitizeConfigDtos) {
//                dsensitizationConfigMap.put(desensitizeConfigDto.getNodePath(), desensitizeConfigDto.getConfigValue());
//            }
//            return dsensitizationConfigMap;
//        }
        return null;
    }

    /**
     * 获取当前字段路径获取脱敏配置信息
     *
     * @param dsensitizationConfigMap
     * @param currentFieldPath
     * @return 0:不脱敏输出  1:脱敏输出  2:不输出
     */
    private int getDesensitizationConfig(Map<String, Integer> dsensitizationConfigMap, String currentFieldPath) {
//        if (dsensitizationConfigMap != null && dsensitizationConfigMap.containsKey(currentFieldPath)) {
//            return dsensitizationConfigMap.get(currentFieldPath);
//        }
        return 1;
    }
}
