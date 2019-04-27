package com.ezddd.common.bean;

import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
@Import(EzBeanScannerRegistrar.class)
public @interface EnableEzdddFramework {
}
