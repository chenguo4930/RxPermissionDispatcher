package com.cheng.lib.processor.exception

import com.cheng.lib.processor.util.simpleString
import javax.lang.model.element.ExecutableElement


class DuplicatedMethodNameException(e: ExecutableElement)
    : RuntimeException("'${e.simpleString()}()' has duplicated '@NeedsPermission' method. The method annotated with '@NeedsPermission' must has the unique name.")