package com.cheng.lib.rxpermissiondispatcher;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.cheng.lib.annotatioin.*;
import com.cheng.lib.rxpermissionlib.listener.RxPermissionCallBack;
import com.cheng.lib.rxpermissionlib.RxPermissionUtil;

/**
 * @author ChengGuo
 * @date 2019/5/14
 */
@RuntimePermissions
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        findViewById(R.id.btn_apply_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                SecondActivityPermissionsDispatcher.showCameraWithPermissionCheck(SecondActivity.this);
//                final RxPermissions rxPermissions = new RxPermissions(SecondActivity.this);

//                RxPermissionUtil.requestPermission(SecondActivity.this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA}, new RxPermissionCallBack() {
                RxPermissionUtil.requestPermission(SecondActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_CONTACTS}, new RxPermissionCallBack() {

                    @Override
                    public void onGranted() {
                        Toast.makeText(SecondActivity.this, R.string.show_camera, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDenied() {
                        Toast.makeText(SecondActivity.this, R.string.show_camera, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    void showCamera() {
        Toast.makeText(this, R.string.show_camera, Toast.LENGTH_SHORT).show();
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void onCameraDenied() {
        // NOTE: Deal with a denied permission, e.g. by showing specific UI
        // or disabling certain functionality
        Toast.makeText(this, R.string.permission_camera_denied, Toast.LENGTH_SHORT).show();
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void showRationaleForCamera(PermissionRequest request) {
        // NOTE: Show a rationale to explain why the permission is needed, e.g. with a dialog.
        // Call request() or cancel() on the provided PermissionRequest to continue or abort
        Toast.makeText(this, R.string.permission_camera_rationale, Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void onCameraNeverAskAgain() {
        Toast.makeText(this, R.string.permission_camera_never_ask_again, Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        SecondActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
//    }

}
