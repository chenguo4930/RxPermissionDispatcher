package com.cheng.lib.annotatioin;

/**
 *
 * @author ChengGuo
 * @date 2019/3/14
 */
public interface GrantableRequest extends PermissionRequest {

    void grant();
}
