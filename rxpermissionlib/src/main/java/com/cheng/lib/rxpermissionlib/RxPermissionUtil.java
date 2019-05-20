package com.cheng.lib.rxpermissionlib;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import com.cheng.lib.rxpermissionlib.listener.PermissionRequest;
import com.cheng.lib.rxpermissionlib.listener.RxPermissionCallBack;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.ref.WeakReference;

/**
 * 权限申请库，对 RxPermission 和 UI(Dialog) 进行封装
 *
 * @author ChengGuo
 * @date 2019/5/16
 */
public final class RxPermissionUtil {

    /**
     * 请求权限
     *
     * @param activity             FragmentActivity和Fragment
     * @param permissions          权限数组
     * @param rxPermissionCallBack 授权回调
     */
    public static void requestPermission(@NonNull FragmentActivity activity, @NonNull String[] permissions, RxPermissionCallBack rxPermissionCallBack) {
        new PermissionRequestWeakReference(activity, rxPermissionCallBack).request(permissions);
    }

    /**
     * 请求权限, 要求显示dialog UI
     *
     * @param activity             FragmentActivity和Fragment
     * @param permissions          权限数组
     * @param rxPermissionCallBack 授权回调
     */
    public static void requestPermissionShowRationale(@NonNull FragmentActivity activity, @NonNull String[] permissions, RxPermissionCallBack rxPermissionCallBack) {
        new PermissionRequestWeakReference(activity, rxPermissionCallBack).showRationale(permissions);
    }


    /**
     * 判断是否具有某权限
     */
    public static boolean hasPermissions(@NonNull Context context, @NonNull String... perms) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String perm : perms) {
            boolean hasPerm = (ContextCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_GRANTED);
            if (!hasPerm) {
                return false;
            }
        }
        return true;
    }

    /**
     * 兼容fragment
     */
    @TargetApi(23)
    private static boolean shouldShowRequestPermissionRationale(@NonNull Object object, @NonNull String perm) {
        if (object instanceof Activity) {
            return ActivityCompat.shouldShowRequestPermissionRationale((Activity) object, perm);
        } else if (object instanceof android.support.v4.app.Fragment) {
            return ((android.support.v4.app.Fragment) object).shouldShowRequestPermissionRationale(perm);
        } else {
            return false;
        }
    }

    private static final class PermissionRequestWeakReference implements PermissionRequest {
        private WeakReference<Object> weakActivity;
        private RxPermissionCallBack callBack;

        private PermissionRequestWeakReference(@NonNull Object activity, RxPermissionCallBack callBack) {
            this.weakActivity = new WeakReference<>(activity);
            this.callBack = callBack;
        }

        @SuppressLint("CheckResult")
        @Override
        public void request(String[] permissions) {
            Object target = weakActivity.get();
            if (target == null) {
                callBack = null;
                return;
            }
            RxPermissions rxPermissions = null;
            if (target instanceof FragmentActivity) {
                rxPermissions = new RxPermissions((FragmentActivity) target);
            } else if (target instanceof android.support.v4.app.Fragment) {
                rxPermissions = new RxPermissions((android.support.v4.app.Fragment) target);
            }
            if (rxPermissions == null) {
                throw new IllegalArgumentException(" rxPermissions's context should be an FragmentActivity or Fragment instance");
            }

            rxPermissions.request(permissions)
                    .subscribe(granted -> {
                        Object activity = weakActivity.get();
                        if (activity == null) {
                            callBack = null;
                            return;
                        }
                        if (granted) {
                            // All requested permissions are granted
                            callBack.onGranted();
                        } else {
                            // At least one permission is denied
                            callBack.onDenied();
                        }
                    });
        }

        @Override
        public void showRationale(String[] strings) {
            Object target = weakActivity.get();
            if (target == null) {
                callBack = null;
                return;
            }
            //FIXME : 显示dialog UI ,点击确定调用request(String[] strings)，点击取消调用  callBack.onDenied();

        }
    }
}
