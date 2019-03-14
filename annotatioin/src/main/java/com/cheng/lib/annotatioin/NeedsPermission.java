package com.cheng.lib.annotatioin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  在需要权限的方法上注册
 *
 * @author ChengGuo
 * @date 2019/3/14
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface NeedsPermission {

    String[] value();

    int maxSdkVersion() default 0;
}
