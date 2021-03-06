package com.cheng.lib.processor.impl.java

import com.cheng.lib.processor.util.typeMirrorOf
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.MethodSpec
import javax.lang.model.type.TypeMirror

/**
 * ProcessorUnit implementation for Activity.
 */
//class JavaActivityProcessorUnit : JavaBaseProcessorUnit() {
class JavaActivityProcessorUnit : JavaBaseRxProcessorUnit() {

    override fun getTargetType(): TypeMirror = typeMirrorOf("android.app.Activity")

    override fun getActivityName(targetParam: String): String = targetParam

    override fun addShouldShowRequestPermissionRationaleCondition(builder: MethodSpec.Builder, targetParam: String, permissionField: String, isPositiveCondition: Boolean) {
        builder.beginControlFlow("if (\$N\$T.shouldShowRequestPermissionRationale(\$N, \$N))", if (isPositiveCondition) "" else "!", PERMISSION_UTILS, targetParam, permissionField)
    }

    override fun addRequestPermissionsStatement(builder: MethodSpec.Builder, targetParam: String, permissionField: String, requestCodeField: String) {
        builder.addStatement("\$T.requestPermissions(\$N, \$N, \$N)", ClassName.get("android.support.v4.app", "ActivityCompat"), targetParam, permissionField, requestCodeField)
    }
}
