package com.cheng.lib.processor

import com.cheng.lib.annotatioin.RuntimePermissions
import com.cheng.lib.processor.impl.javaProcessorUnits
import com.cheng.lib.processor.impl.kotlinProcessorUnits
import com.cheng.lib.processor.util.findAndValidateProcessorUnit
import com.cheng.lib.processor.util.kotlinMetadataClass
import sun.rmi.runtime.Log
import java.io.File
import java.util.logging.Logger
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Filer
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.tools.Diagnostic
import kotlin.properties.Delegates

/** Element Utilities, obtained from the processing environment */
var ELEMENT_UTILS: Elements by Delegates.notNull()
/** Type Utilities, obtained from the processing environment */
var TYPE_UTILS: Types by Delegates.notNull()

class PermissionsProcessor : AbstractProcessor() {

    companion object {
        // processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME] returns generated/source/kaptKotlin/$sourceSetName
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

    /* Processing Environment helpers */
    private var filer: Filer by Delegates.notNull()

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        filer = processingEnv.filer
        ELEMENT_UTILS = processingEnv.elementUtils
        TYPE_UTILS = processingEnv.typeUtils
    }

    override fun getSupportedSourceVersion(): SourceVersion? {
        return SourceVersion.latestSupported()
    }

    override fun getSupportedAnnotationTypes(): Set<String> {
        return hashSetOf(RuntimePermissions::class.java.canonicalName)
    }

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
        //创建一个RequestCodeProvider，保证每个权限请求的唯一请求代码
        val requestCodeProvider = RequestCodeProvider()

        // 需要对已注释元素集进行排序、 为了实现确定性，可重复的构建
        roundEnv.getElementsAnnotatedWith(RuntimePermissions::class.java)
                .sortedBy { it.simpleName.toString() }
                .forEach {
                    val rpe = RuntimePermissionsElement(it as TypeElement)
                    val kotlinMetadata = it.getAnnotation(kotlinMetadataClass)
                    if (kotlinMetadata != null) {
                        processKotlin(it, rpe, requestCodeProvider)
                    } else {
                        processJava(it, rpe, requestCodeProvider)
                    }
                }
        return true
    }

    /**
     * 源文件为kotlin文件，则生产kotlin文件，使用kotlin的 扩展模式
     */
    private fun processKotlin(element: Element, rpe: RuntimePermissionsElement, requestCodeProvider: RequestCodeProvider) {
        //  奇怪的是，kaptKotlin文件在AS或IntelliJ上不被识别为源文件
        // 因此，我们在generated / source / kapt / $ sourceSetName中生成.kt文件
        // ref: https://github.com/hotchemi/PermissionsDispatcher/issues/320#issuecomment-316175775
        val kaptGeneratedDirPath = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]?.replace("kaptKotlin", "kapt")
                ?: run {
                    processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, "Can't find the target directory for generated Kotlin files.")
                    return
                }
        val kaptGeneratedDir = File(kaptGeneratedDirPath)
        if (!kaptGeneratedDir.parentFile.exists()) {
            kaptGeneratedDir.parentFile.mkdirs()
        }
        val processorUnit = findAndValidateProcessorUnit(kotlinProcessorUnits, element)
        val kotlinFile = processorUnit.createFile(rpe, requestCodeProvider)
        kotlinFile.writeTo(kaptGeneratedDir)
    }

    /**
     * 源文件为java文件，则生产java文件，使用java的 static静态方法模式
     */
    private fun processJava(element: Element, rpe: RuntimePermissionsElement, requestCodeProvider: RequestCodeProvider) {
        val processorUnit = findAndValidateProcessorUnit(javaProcessorUnits, element)
        val javaFile = processorUnit.createFile(rpe, requestCodeProvider)
        javaFile.writeTo(filer)
    }
}
