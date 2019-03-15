package com.cheng.lib.processor.exception

import com.cheng.lib.processor.util.simpleString
import javax.lang.model.element.ExecutableElement
import javax.lang.model.type.TypeMirror

class WrongParametersException(e: ExecutableElement, numParams: Int, requiredType: TypeMirror)
    : RuntimeException("Method '${e.simpleString()}()' must has less than or equal to $numParams size parameter and type of it is supposed to be '${requiredType.simpleString()}'")