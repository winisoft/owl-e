package com.wini.owle


class JsModulePlatform: Platform {
    override val name: String = "Web with Kotlin/JS!"
}

actual fun getPlatform(): Platform = JsModulePlatform()