package com.ezshop.annotations;

import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.EnableAutoConfigurationImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@SuppressWarnings("deprecation")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
public @interface EzEnableAutoConfiguration {
}
