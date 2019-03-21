package com.cheng.lib.annotatioin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ChengGuo
 * @date 2019/3/14
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface NeedsPermission {

    String[] value();

    int maxSdkVersion() default 0;
}
