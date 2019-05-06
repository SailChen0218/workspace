package com.ezddd.core.utils;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ClassUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ClassUtil.class);

    public static final String LEFT_PARENTHESIS = "(";

    public static final String DOT = ".";

    public static final String COMMA = ",";

    private ClassUtil() {

    }

    private static List<String> baseMethod = new ArrayList<String>();

    static final Map<Type, Class<?>> typeMap = new HashMap<Type, Class<?>>();

    static final Map<String, Class<?>> primitiveMap = new HashMap<String, Class<?>>();

    static {
        baseMethod.add("equals");
        baseMethod.add("clone");
        baseMethod.add("hashCode");
        baseMethod.add("finalize");
        baseMethod.add("toString");

        typeMap.put(byte.class, byte[].class);
        typeMap.put(int.class, int[].class);
        typeMap.put(short.class, short[].class);
        typeMap.put(long.class, long[].class);
        typeMap.put(double.class, double[].class);
        typeMap.put(float.class, float[].class);
        typeMap.put(char.class, char[].class);
        typeMap.put(boolean.class, boolean[].class);

        primitiveMap.put(boolean.class.getName(), boolean.class);
        primitiveMap.put(short.class.getName(), short.class);
        primitiveMap.put(int.class.getName(), int.class);
        primitiveMap.put(long.class.getName(), long.class);
        primitiveMap.put(float.class.getName(), float.class);
        primitiveMap.put(double.class.getName(), double.class);
        primitiveMap.put(char.class.getName(), char.class);
        primitiveMap.put(byte.class.getName(), byte.class);
        primitiveMap.put(boolean[].class.getName(), boolean[].class);
        primitiveMap.put(short[].class.getName(), short[].class);
        primitiveMap.put(int[].class.getName(), int[].class);
        primitiveMap.put(long[].class.getName(), long[].class);
        primitiveMap.put(float[].class.getName(), float[].class);
        primitiveMap.put(double[].class.getName(), double[].class);
        primitiveMap.put(char[].class.getName(), char[].class);
        primitiveMap.put(byte[].class.getName(), byte[].class);

    }

    public static boolean isBaseMethod(String methodname) {
        return baseMethod.contains(methodname);
    }

    /**
     * @param methodname 由接口名.方法名(参数签名, ...)三部分组成
     * 如果methodname中没有接口名，则会尝试使用interfaceName作为接口名
     */
    public static Method getMethod(String interfaceName, String methodname, Object... params)
            throws NoSuchMethodException, ClassNotFoundException {
        Method methodName2 = parseMethodName(interfaceName, methodname);
        if (methodName2 == null) {
            // add by liwq 2016-02-06 获取没有参数签名的方法
            methodName2 = getUnsignedMethod(interfaceName, methodname, params);
        }
        if (params != null) {
            Object[] virtualParams = new Object[params.length];
            boolean successConvert = getActualParameters(methodName2, virtualParams, params);
            LOG.debug("virtulParams" + ReflectionToStringBuilder.toString(virtualParams));
            if (successConvert && virtualParams.length > 0) {
                for (int i = 0; i < virtualParams.length; i++) {
                    params[i] = virtualParams[i];
                }
            }
        }
        return methodName2;
    }

    /**
     * 根据方法的参数签名，尝试将远程传递过来的实参转换成正确的类型
     * @return 参数是否全部转换成功
     */
    private static boolean getActualParameters(Method methodName2, Object[] virtualParams, Object... params) {
        boolean result = true;
        try {
            JSONArray arr = new JSONArray();
            for (int i = 0, len = params.length; i < len; ++i) {
                Object param = params[i];
                Type paramType = methodName2.getGenericParameterTypes()[i];
                LOG.debug("参数类型：" + paramType + "|实参类型: " + (param != null ? param.getClass() : null));

                if (param == null || rawType(paramType) == param.getClass()) {
                    virtualParams[i] = params[i];
                    continue;
                }

                if (params[i] instanceof String || !(params[i] instanceof JSON)) {
                    virtualParams[i] = params[i];
                } else {
                    addTypedParam(arr, param);
                    String jsonString = arr.toJSONString();
                    LOG.debug("参数:" + i + ";" + param + "|参数类型:" + paramType + "|jsonArray:" + jsonString);
                    List<?> parseArray = JSONArray.parseArray(jsonString, new Type[] {fitFor(paramType)});
                    virtualParams[i] = parseArray.get(0);
                    arr.clear();
                }
            }
        } catch (Exception ex) {
            LOG.error(ex.toString(), ex);
            result = false;
        }
        return result;
    }

    /**
     * 基本数据类型签名时，保证能够返回正确的类型信息
     */
    private static Class<?> fit(String paramType) throws ClassNotFoundException {
        Class<?> clazz = primitiveMap.get(paramType);
        if (clazz != null)
            return clazz;
        else
            return Class.forName(paramType);
    }

    /**
     * 兼容Java6 转型为正解基本数据类型的数组类型
     */
    private static Type fitFor(Type arrType) {
        if (arrType instanceof GenericArrayType) {
            GenericArrayType gat = (GenericArrayType) arrType;
            Type genericComponentType = gat.getGenericComponentType();
            Class<?> c = typeMap.get(genericComponentType);
            return c != null ? c : arrType;
        }
        return arrType;
    }

    private static Type rawType(Type genericType) {
        if (genericType instanceof ParameterizedType) {
            ParameterizedType py = (ParameterizedType) genericType;
            return py.getRawType();
        } else {
            return genericType;
        }
    }

    private static void addTypedParam(JSONArray arr, Object param) {
        if (param != null && Number.class.isAssignableFrom(param.getClass())) {
            arr.add(JSONObject.toJSON(param).toString());
        } else {
            arr.add(param);
        }
    }

    private static Method parseMethodName(String interfaceName, String methodname) throws NoSuchMethodException,
            ClassNotFoundException {
        Method m2;
        int leftParenthesisPosition = methodname.indexOf(LEFT_PARENTHESIS);
        if (leftParenthesisPosition < 0) {
            return null;
        }
        StringBuilder method = new StringBuilder(methodname.substring(0, leftParenthesisPosition));

        pruneReturnType(method);
        Class<?> clazz = parseClazzName(interfaceName, method);
        String realMethod = method.toString();

        StringBuilder signedParams = new StringBuilder(methodname.substring(leftParenthesisPosition));
        signedParams.deleteCharAt(signedParams.length() - 1);
        signedParams.deleteCharAt(0);
        if (signedParams.length() > 0) {
            String[] split = StringUtils.split(signedParams.toString(), COMMA);
            Class<?>[] paramTypes = new Class<?>[split.length];
            try {
                for (int i = 0; i < split.length; i++) {
                    paramTypes[i] = fit(split[i]);
                }
            } catch (ClassNotFoundException e) {
                LOG.error(e.toString(), e);
                throw new NoSuchMethodException(e.toString());
            }
            m2 = getActualMethod(clazz, realMethod, paramTypes);
        } else {
            m2 = getActualMethod(clazz, realMethod);
        }
        return m2;
    }

    /**
     * @Title getUnsignedMethod
     * @Description 获取没有参数签名的方法
     * @param interfaceName
     * @param methodname
     * @param params
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException Method
     */
    private static Method getUnsignedMethod(String interfaceName, String methodname, Object... params)
            throws ClassNotFoundException, NoSuchMethodException {
        Class<?> clazz = Class.forName(interfaceName);
        // 1.先根据参数类型去获取
        boolean hasType = true;
        if (params != null && params.length > 0) {
            Class[] paramClazz = new Class[params.length];
            for (int i = 0; i < params.length; i++) {
                // 参数为空无法判断类型
                if (params[i] == null) {
                    hasType = false;
                    break;
                }
                paramClazz[i] = params[i].getClass();
            }
            Method method = null;
            if(hasType){
                try {
                    method = clazz.getMethod(methodname, paramClazz);
                } catch (Exception e) {

                }
            }
            if (method != null) {
                return method;
            }
        }

        // 2.参数类型获取不到，再根据参数个数获取。
        int paramlength = 0;
        if (params != null) {
            paramlength = params.length;
        }
        return getMethod(clazz, methodname, paramlength);
    }

    /**
     * 有返回类型声明，说明调用来自ServiceClient。但为了保证下文正确处理，这里只做移除操作
     */
    private static StringBuilder pruneReturnType(StringBuilder method) {
        int spacePos = method.indexOf(" ");
        if (spacePos != -1) {
            method.delete(0, spacePos + 1);
        }
        return method;
    }

    /**
     * 兼容处理方法签名中包含类全限定名声明
     * x.y.z.Clazz.methodName(...)
     */
    private static Class<?> parseClazzName(String interfaceName, StringBuilder method) throws ClassNotFoundException {
        Class<?> clazz;
        int lastDot = method.lastIndexOf(DOT);
        if (lastDot != -1) {
            String interfaceName0 = method.substring(0, lastDot);
            method.replace(0, lastDot + 1, "");
            clazz = Class.forName(interfaceName0);
        } else {
            clazz = Class.forName(interfaceName);
        }
        return clazz;
    }

    public static Method getActualMethod(Class<?> clazz, String method, Class<?>... paramTyps)
            throws NoSuchMethodException {
        Method m2;
        try {
            m2 = clazz.getMethod(method, paramTyps);
        } catch (NoSuchMethodException e) {
            m2 = clazz.getDeclaredMethod(method, paramTyps);
        }
        return m2;
    }

    public static Method getMethod(Class<?> clazz, String methodname, int paramSize) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodname) && method.getParameterTypes().length == paramSize) {
                return method;
            }
        }
        return null;
    }
}
