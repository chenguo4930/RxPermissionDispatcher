package com.cheng.lib.processor.impl.kotlin

import com.cheng.lib.processor.util.typeMirrorOf
import com.squareup.kotlinpoet.FunSpec
import javax.lang.model.type.TypeMirror

/**
 * [permissions.dispatcher.processor.KtProcessorUnit] implementation for Fragments.
 */
class KotlinFragmentProcessorUnit : KotlinBaseProcessorUnit() {

    override fun getTargetType(): TypeMirror = typeMirrorOf("android.support.v4.app.Fragment")

    override fun getActivityName(targetParam: String): String = "$targetParam.requireActivity()"

    override fun addShouldShowRequestPermissionRationaleCondition(builder: FunSpec.Builder, permissionField: String, isPositiveCondition: Boolean) {
        val condition = if (isPositiveCondition) "" else "!"
        builder.beginControlFlow("if (%N%T.shouldShowRequestPermissionRationale(%L, *%N))", condition, PERMISSION_UTILS, "this" /* Fragment */, permissionField)
    }

    override fun addRequestPermissionsStatement(builder: FunSpec.Builder, targetParam: String, permissionField: String, requestCodeField: String) {
        builder.addStatement("%L.requestPermissions(%L, %N)", targetParam, permissionField, requestCodeField)
    }
}
