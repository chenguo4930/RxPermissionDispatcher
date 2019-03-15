package com.cheng.lib.processor.exception

import com.cheng.lib.processor.util.simpleString
import javax.lang.model.element.ExecutableElement

class MixPermissionTypeException(e: ExecutableElement, permissionName: String)
    : RuntimeException("Method '${e.simpleString()}()' defines '$permissionName' with other permissions at the same time.")