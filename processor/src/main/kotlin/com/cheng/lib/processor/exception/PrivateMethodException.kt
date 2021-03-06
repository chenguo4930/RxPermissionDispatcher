package com.cheng.lib.processor.exception

import com.cheng.lib.processor.util.simpleString
import javax.lang.model.element.ExecutableElement

class PrivateMethodException(e: ExecutableElement, annotationType: Class<*>)
    : RuntimeException("Method '${e.simpleString()}()' annotated with '@${annotationType.simpleName}' must not be private")
//An exception occurred: com.cheng.lib.processor.exception.PrivateMethodException: Method 'showCamera()' annotated with '@NeedsPermission' must not be private