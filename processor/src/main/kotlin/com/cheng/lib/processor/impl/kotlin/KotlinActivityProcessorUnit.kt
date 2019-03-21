package com.cheng.lib.processor.impl.kotlin

import com.cheng.lib.processor.util.typeMirrorOf
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import javax.lang.model.type.TypeMirror

/**
 * [permissions.dispatcher.processor.KtProcessorUnit] implementation for Activity classes.
 */
class KotlinActivityProcessorUnit : KotlinBaseProcessorUnit() {

    override fun getTargetType(): TypeMirror = typeMirrorOf("android.app.Activity")

    override fun getActivityName(targetParam: String): String = targetParam

    override fun addShouldShowRequestPermissionRationaleCondition(builder: FunSpec.Builder, permissionField: String, isPositiveCondition: Boolean) {
        val condition = if (isPositiveCondition) "" else "!"
        builder.beginControlFlow("if (%N%T.shouldShowRequestPermissionRationale(%L, *%N))", condition, PERMISSION_UTILS, "this", permissionField)
    }

    override fun addRequestPermissionsStatement(builder: FunSpec.Builder, targetParam: String, permissionField: String, requestCodeField: String) {
        builder.addStatement("%T.requestPermissions(%L, %N, %N)", ClassName("android.support.v4.app", "ActivityCompat"), targetParam, permissionField, requestCodeField)
    }
}
