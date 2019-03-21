package com.cheng.lib.processor.impl.java

import com.cheng.lib.processor.util.typeMirrorOf
import com.squareup.javapoet.MethodSpec
import javax.lang.model.type.TypeMirror

/**
 * ProcessorUnit implementation for Fragment.
 */
class JavaFragmentProcessorUnit : JavaBaseProcessorUnit() {

//    override fun getTargetType(): TypeMirror = typeMirrorOf("androidx.fragment.app.Fragment")
    override fun getTargetType(): TypeMirror = typeMirrorOf("android.support.v4.app.Fragment")

    override fun getActivityName(targetParam: String): String = "$targetParam.requireActivity()"

    override fun addShouldShowRequestPermissionRationaleCondition(builder: MethodSpec.Builder, targetParam: String, permissionField: String, isPositiveCondition: Boolean) {
        builder.beginControlFlow("if (\$N\$T.shouldShowRequestPermissionRationale(\$N, \$N))", if (isPositiveCondition) "" else "!", PERMISSION_UTILS, targetParam, permissionField)
    }

    override fun addRequestPermissionsStatement(builder: MethodSpec.Builder, targetParam: String, permissionField: String, requestCodeField: String) {
        builder.addStatement("\$N.requestPermissions(\$N, \$N)", targetParam, permissionField, requestCodeField)
    }
}
