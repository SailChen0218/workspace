package com.ezddd.core.utils;

import java.lang.annotation.*;

/**
 * <p>标题: </p>
 * <p>功能描述: 用于BeanUtilsHelper进行对象clone时指定dto日期属性格式</p>
 * <p>
 * <p>版权: 税友软件集团股份有限公司</p>
 * <p>创建时间: 2018/10/15</p>
 * <p>作者：cqf</p>
 * <p>修改历史记录：</p>
 * ====================================================================<br>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
@Documented
public @interface ConvertDate {
    String targetFormat() default "yyyy-MM-dd HH:mm:ss";
    String sourceFormat() default "yyyy-MM-dd HH:mm:ss";
}
