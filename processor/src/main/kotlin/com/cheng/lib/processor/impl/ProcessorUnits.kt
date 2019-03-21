package com.cheng.lib.processor.impl

import com.cheng.lib.processor.impl.java.JavaActivityProcessorUnit
import com.cheng.lib.processor.impl.java.JavaConductorProcessorUnit
import com.cheng.lib.processor.impl.java.JavaFragmentProcessorUnit
import com.cheng.lib.processor.impl.kotlin.KotlinActivityProcessorUnit
import com.cheng.lib.processor.impl.kotlin.KotlinConductorProcessorUnit
import com.cheng.lib.processor.impl.kotlin.KotlinFragmentProcessorUnit

val javaProcessorUnits = listOf(JavaActivityProcessorUnit(), JavaFragmentProcessorUnit(), JavaConductorProcessorUnit())
val kotlinProcessorUnits = listOf(KotlinActivityProcessorUnit(), KotlinFragmentProcessorUnit(), KotlinConductorProcessorUnit())
