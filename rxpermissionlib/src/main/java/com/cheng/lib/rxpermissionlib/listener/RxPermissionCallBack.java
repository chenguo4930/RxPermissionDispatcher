package com.cheng.lib.rxpermissionlib.listener;

/**
 * RxPermission回调
 *
 * @author ChengGuo
 * @date 2019/5/16
 */
public interface RxPermissionCallBack {
    /**
     * 同意权限
     */
    void onGranted();

    /**
     * 拒绝权限
     */
    void onDenied();


}
