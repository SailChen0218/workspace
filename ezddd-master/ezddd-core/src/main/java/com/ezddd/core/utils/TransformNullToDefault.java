package com.ezddd.core.utils;

import java.lang.annotation.*;

/**
 * <p>标题: </p>
 * <p>功能描述: </p>
 * <p>
 * <p>版权: 税友软件集团股份有限公司</p>
 * <p>创建时间: 2018/10/20</p>
 * <p>作者：cqf</p>
 * <p>修改历史记录：</p>
 * ====================================================================<br>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@Documented
public @interface TransformNullToDefault {
    Class<?>[] clazz() default String.class;
    String[] defaultValue() default "";
}
