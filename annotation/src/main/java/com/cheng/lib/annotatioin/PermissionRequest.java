package com.cheng.lib.annotatioin;

/**
 * Interface used by methods to allow for continuation or cancellation of a permission request.
 *
 * @author ChengGuo
 * @date 2019/3/14
 */
public interface PermissionRequest {

    void proceed();

    void cancel();
}
