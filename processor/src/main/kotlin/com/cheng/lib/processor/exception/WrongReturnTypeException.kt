package com.cheng.lib.processor.exception

import com.cheng.lib.processor.util.simpleString
import javax.lang.model.element.ExecutableElement

class WrongReturnTypeException(e: ExecutableElement)
    : RuntimeException("Method '${e.simpleString()}()' must specify return type 'void', not '${e.returnType}'")