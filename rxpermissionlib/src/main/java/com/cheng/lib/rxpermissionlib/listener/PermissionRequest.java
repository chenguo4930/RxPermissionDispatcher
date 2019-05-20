package com.cheng.lib.rxpermissionlib.listener;

/**
 * Interface used by methods to allow for continuation or cancellation of a permission request.
 *
 * @author ChengGuo
 * @date 2019/3/14
 */
public interface PermissionRequest {

    /**
     * 请求权限
     */
    void request(String[] strings);

    /**
     * 权限申请前，需要显示解释为何需要许可的理由
     */
    void showRationale(String[] strings);
}
