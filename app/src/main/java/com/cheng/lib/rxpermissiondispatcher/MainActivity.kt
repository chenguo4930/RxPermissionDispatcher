package com.cheng.lib.rxpermissiondispatcher

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.cheng.lib.annotatioin.OnNeverAskAgain
import com.cheng.lib.annotatioin.RuntimePermissions
import kotlinx.android.synthetic.main.activity_main.*

@RuntimePermissions
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_apply_camera.setOnClickListener {
            showCamera()
        }
    }

    private fun showCamera() {

    }

    fun onCameraDenied() {

    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    fun onCameraNeverAskAgain() {
        Toast.makeText(this, R.string.permission_camera_never_ask_again, Toast.LENGTH_SHORT).show()
    }


}
