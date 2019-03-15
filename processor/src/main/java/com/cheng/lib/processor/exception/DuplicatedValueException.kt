package com.cheng.lib.processor.exception

import com.cheng.lib.processor.util.simpleString
import javax.lang.model.element.ExecutableElement


class DuplicatedValueException(value: List<String>, e: ExecutableElement, annotation: Class<*>)
    : RuntimeException("$value is duplicated in '${e.simpleString()}()' annotated with '@${annotation.simpleName}'")