package com.cheng.lib.annotatioin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 当用户拒绝权限之后，再次提醒
 *
 * @author ChengGuo
 * @date 2019/3/14
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface OnNeverAskAgain {
    String[] value();
}
