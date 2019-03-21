package com.cheng.lib.processor.exception

import com.cheng.lib.processor.util.simpleString
import javax.lang.model.element.ExecutableElement

class NoParametersAllowedException(e: ExecutableElement)
    : RuntimeException("Method '${e.simpleString()}()' must not have any parameters")