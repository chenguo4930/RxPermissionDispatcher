package com.cheng.lib.processor

import com.cheng.lib.annotatioin.NeedsPermission
import com.cheng.lib.annotatioin.OnNeverAskAgain
import com.cheng.lib.annotatioin.OnPermissionDenied
import com.cheng.lib.annotatioin.OnShowRationale
import com.cheng.lib.processor.util.*
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeVariableName
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.asTypeVariableName
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement

class RuntimePermissionsElement(val e: TypeElement) {
    val typeName: TypeName = TypeName.get(e.asType())
    val ktTypeName = e.asType().asTypeName()
    val typeVariables = e.typeParameters.map { TypeVariableName.get(it) }
    val ktTypeVariables = e.typeParameters.map { it.asTypeVariableName() }
    val packageName = e.packageName()
    val inputClassName = e.simpleString()
    val generatedClassName = inputClassName + GEN_CLASS_SUFFIX
    val needsElements = e.childElementsAnnotatedWith(NeedsPermission::class.java)
    private val onRationaleElements = e.childElementsAnnotatedWith(OnShowRationale::class.java)
    private val onDeniedElements = e.childElementsAnnotatedWith(OnPermissionDenied::class.java)
    private val onNeverAskElements = e.childElementsAnnotatedWith(OnNeverAskAgain::class.java)

    init {
        validateNeedsMethods()
        validateRationaleMethods()
        validateDeniedMethods()
        validateNeverAskMethods()
    }

    private fun validateNeedsMethods() {
        checkNotEmpty(needsElements, this, NeedsPermission::class.java)
        checkPrivateMethods(needsElements, NeedsPermission::class.java)
        checkMethodSignature(needsElements)
        //检查混合权限类型
        checkMixPermissionType(needsElements, NeedsPermission::class.java)
        //检查重复的方法名称
        checkDuplicatedMethodName(needsElements)
    }

    private fun validateRationaleMethods() {
        //检查重复值
        checkDuplicatedValue(onRationaleElements, OnShowRationale::class.java)
        //检查私人方法
        checkPrivateMethods(onRationaleElements, OnShowRationale::class.java)
        //检查方法签名
        checkMethodSignature(onRationaleElements)
        //检查方法参数
        checkMethodParameters(onRationaleElements, 1, typeMirrorOf("com.cheng.lib.annotatioin.PermissionRequest"))
    }

    private fun validateDeniedMethods() {
        checkDuplicatedValue(onDeniedElements, OnPermissionDenied::class.java)
        checkPrivateMethods(onDeniedElements, OnPermissionDenied::class.java)
        checkMethodSignature(onDeniedElements)
        checkMethodParameters(onDeniedElements, 0)
    }

    private fun validateNeverAskMethods() {
        checkDuplicatedValue(onNeverAskElements, OnNeverAskAgain::class.java)
        checkPrivateMethods(onNeverAskElements, OnNeverAskAgain::class.java)
        checkMethodSignature(onNeverAskElements)
        checkMethodParameters(onNeverAskElements, 0)
        checkSpecialPermissionsWithNeverAskAgain(onNeverAskElements)
    }

    fun findOnRationaleForNeeds(needsElement: ExecutableElement): ExecutableElement? {
        return findMatchingMethodForNeeds(needsElement, onRationaleElements, OnShowRationale::class.java)
    }

    fun findOnDeniedForNeeds(needsElement: ExecutableElement): ExecutableElement? {
        return findMatchingMethodForNeeds(needsElement, onDeniedElements, OnPermissionDenied::class.java)
    }

    fun findOnNeverAskForNeeds(needsElement: ExecutableElement): ExecutableElement? {
        return findMatchingMethodForNeeds(needsElement, onNeverAskElements, OnNeverAskAgain::class.java)
    }
}