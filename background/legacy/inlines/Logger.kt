package com.wini.owle.air.inlines

import java.util.logging.Logger
import java.util.logging.Level.INFO
import java.util.logging.Level.SEVERE
import kotlin.reflect.KClass

fun getLogger(): Logger = Logger.getGlobal().apply {

}


fun d(msg: String) = getLogger().log(INFO, msg)

fun d(clazz: KClass<*>, msg: String) = getLogger().log(INFO, "${clazz.simpleName}: $msg")

fun e(cause: Throwable? = null, msg: String) = getLogger().apply {
    logErrorText(cause, msg)
}
fun e(cause: Throwable? = null, clazz: KClass<*>? = null, msg: String) {
    StringBuilder().apply {

        appendLine(clazz?.run { "$simpleName: $msg" } ?: msg)
        cause?.message?.apply { appendLine(this) }
        cause?.stackTrace?.forEachIndexed { i, it ->
            appendLine("class: ${it.className}\t\t\tline ${it.lineNumber}: ${it.methodName}")
        }
    }.toString().apply {
        getLogger().log(SEVERE, this)
    }
}

private fun logErrorText(cause: Throwable?, msg: String): String {
    return StringBuilder().apply {
        appendLine(msg)
        cause?.message?.apply { appendLine(this) }
        cause?.stackTrace?.forEachIndexed { i, it ->
            appendLine("class: ${it.className}\t\t\tline ${it.lineNumber}: ${it.methodName}")
        }
    }.toString().apply {
        getLogger().log(SEVERE, this)
    }
}