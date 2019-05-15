package com.cheng.lib.annotatioin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Register some methods which explain why permissions are needed.
 * 权限申请前，需要显示解释为何需要许可的理由
 *
 * @author ChengGuo
 * @date 2019/3/14
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface OnShowRationale {
    String[] value();
}
