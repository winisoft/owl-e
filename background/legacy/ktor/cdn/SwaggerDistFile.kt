package com.wini.owle.air.ktor.cdn

import io.ktor.http.*


class SwaggerDistFile(
    override val fileName: String,
    override val contentType: ContentType,
    classLoader: ClassLoader
) : CdnFile {

    override val resource: String? = classLoader.loadResource()

    override val sourceDirectory: String get() = "/dist"

}
