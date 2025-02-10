package com.wini.owle.air.ktor.cdn
import io.ktor.http.*
import io.ktor.http.ContentType.Text


object SwaggerDistFiles: (ClassLoader) -> List<SwaggerDistFile> {

    override fun invoke(classLoader: ClassLoader): List<SwaggerDistFile>  {
        return listOf(
            SwaggerDistFile("dark-swagger-user.css", Text.CSS, classLoader),
            SwaggerDistFile("oauth2-redirect.html", Text.Html, classLoader),
            SwaggerDistFile("swagger-ui.css", Text.CSS, classLoader),
            SwaggerDistFile("swagger-ui.css.map", Text.Any, classLoader),
            SwaggerDistFile("swagger-ui.js", Text.JavaScript, classLoader),
            SwaggerDistFile("swagger-ui.js.map", Text.Any, classLoader),
            SwaggerDistFile("swagger-ui-bundle.js", Text.JavaScript, classLoader),
            SwaggerDistFile("swagger-ui-bundle.js.map", Text.Any, classLoader),
            SwaggerDistFile("swagger-ui-bundle-core.js", Text.JavaScript, classLoader),
            SwaggerDistFile("swagger-ui-bundle-core.js.map", Text.Any, classLoader),
            SwaggerDistFile("swagger-ui-standalone-preset.js", Text.JavaScript, classLoader),
            SwaggerDistFile("swagger-ui-standalone-preset.js.map", Text.Any, classLoader),
        )
    }
}