package com.wini.owle.ksp.writer

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.wini.owle.content.doc.DtoModelDoc
import com.wini.owle.schema.wrapper.declaration.error.ErrorDeclaration


/**
 * The error reference, written as one page rather than one page per error.
 *
 * An individual error is a code, a sentence, and the list of fields that raise it — too little to
 * carry a page of its own, and spreading them out would hide the thing a reader actually wants,
 * which is the shape of the whole set. So they are grouped into the families their
 * `@ErrorDefinition` keys already describe (`errors.constraint.*`, `errors.db.*`, and so on) and
 * laid out as a tree.
 *
 * The "raised by" lines are the part that cannot be read off any single source file: they are
 * inverted from the models, so the page answers "what trips this?" as well as "what does this
 * mean?".
 *
 * Two renderings come off the same model: Markdown for reading in place, and a self-contained
 * Darcula-styled HTML page for the docs site.
 */
class KnowledgeBaseErrorFileWriter(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) {

    private data class Family(val prefix: String, val title: String, val blurb: String)

    private data class Section(
        val title: String,
        val blurb: String,
        val members: List<ErrorDeclaration>
    ) {
        /** Stable anchor/id for the section, e.g. "Request handling" -> "request-handling". */
        val slug: String get() = title.lowercase().replace(Regex("[^a-z0-9]+"), "-").trim('-')
    }

    private val families = listOf(
        Family(
            "errors.apiRequest.",
            "Request handling",
            "Raised before a request reaches the domain at all — the body, the parameters, or the " +
                "caller's identity was not usable."
        ),
        Family(
            "errors.constraint.",
            "Field constraints",
            "Raised by validation against a DTO. Each names the rule that was broken; the offending " +
                "value and the field it came from arrive in the error's detail."
        ),
        Family(
            "errors.db.",
            "Database",
            "Raised when the store itself refuses or fails to complete the work. These describe the " +
                "condition, not the statement that provoked it."
        )
    )

    private val lede =
        "Every failure Owl-E reports carries a stable code, a message stating the rule in general " +
            "terms, and a detail naming the specific value that broke it. Codes are the contract: " +
            "they are safe to branch on and will not change wording underneath you."

    fun generate(errors: List<ErrorDeclaration>, models: List<DtoModelDoc>) {

        // code -> the fields that can raise it, inverted from every model's property constraints
        val raisedBy: Map<String, List<String>> = buildMap<String, MutableList<String>> {
            models.forEach { model ->
                model.properties.forEach { prop ->
                    prop.constraints.forEach { c ->
                        getOrPut(c.errorCode) { mutableListOf() } += "${model.name}.${prop.name}"
                    }
                }
            }
        }.mapValues { (_, v) -> v.distinct().sorted() }

        val ungrouped = errors.filter { e ->
            families.none { e.errorAnnotation.errorResKey.startsWith(it.prefix) }
        }.sortedBy { it.code }

        if (ungrouped.isNotEmpty()) {
            logger.warn(
                "[error-doc] ${ungrouped.size} error(s) sit outside every known family and were " +
                    "filed under Other: ${ungrouped.joinToString { it.simpleName.asString() }}"
            )
        }

        val sections: List<Section> = buildList {
            families.forEach { family ->
                val members = errors
                    .filter { it.errorAnnotation.errorResKey.startsWith(family.prefix) }
                    .sortedBy { it.code }
                if (members.isNotEmpty()) add(Section(family.title, family.blurb, members))
            }
            if (ungrouped.isNotEmpty()) {
                add(Section("Other", "Errors not yet filed into a family.", ungrouped))
            }
        }

        writeMarkdown(errors, sections, raisedBy)
        writeHtml(errors, sections, raisedBy)

        logger.warn("[error-doc] ${errors.size} errors across ${sections.size} families")
    }

    /** KDoc flattened to one line, dropped when it only restates the resolved message. */
    private fun ErrorDeclaration.notes(): String? = docString?.trim()
        ?.lineSequence()
        ?.map { it.trim().removePrefix("*").trim() }
        ?.filter { it.isNotBlank() }
        ?.joinToString(" ")
        ?.takeIf { it.isNotBlank() && it != message }

    // ------------------------------------------------------------------ markdown

    private fun writeMarkdown(
        errors: List<ErrorDeclaration>,
        sections: List<Section>,
        raisedBy: Map<String, List<String>>
    ) {
        codeGenerator.createNewFile(
            dependencies = Dependencies(false),
            packageName = "com.wini.owle.error",
            fileName = "ErrorsDoc",
            extensionName = "md"
        ).bufferedWriter().use { w ->

            w.appendLine("Errors")
            w.appendLine("---")
            w.appendLine("> $lede")
            w.appendLine()
            w.appendLine("${errors.size} errors in ${sections.size} families.")
            w.appendLine()

            sections.forEach { section ->
                w.appendLine("## ${section.title}")
                w.appendLine()
                w.appendLine("> ${section.blurb}")
                w.appendLine()

                section.members.forEach { error ->
                    w.appendLine("- ### `${error.code}`")
                    w.appendLine("\t${error.message}")
                    w.appendLine("\t- type: `${error.simpleName.asString()}`")
                    error.notes()?.let { w.appendLine("\t- notes: $it") }
                    raisedBy[error.code]?.takeIf { it.isNotEmpty() }?.let { fields ->
                        w.appendLine("\t- raised by: ${fields.joinToString(", ") { "`$it`" }}")
                    }
                    w.appendLine()
                }
            }
        }
    }

    // ------------------------------------------------------------------ html

    private fun writeHtml(
        errors: List<ErrorDeclaration>,
        sections: List<Section>,
        raisedBy: Map<String, List<String>>
    ) {
        codeGenerator.createNewFile(
            dependencies = Dependencies(false),
            packageName = "com.wini.owle.error",
            fileName = "ErrorsDoc",
            extensionName = "html"
        ).bufferedWriter().use { w ->

            w.appendLine("<!doctype html>")
            w.appendLine("<html lang=\"en\">")
            w.appendLine("<head>")
            w.appendLine("<meta charset=\"utf-8\">")
            w.appendLine("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">")
            w.appendLine("<title>Owl-E Error Reference</title>")
            w.appendLine("<style>${DocStyle.CSS}</style>")
            w.appendLine("</head>")
            w.appendLine("<body>")
            w.appendLine("<div class=\"shell\">")

            // --- tool window ---
            w.appendLine("<aside class=\"rail\">")
            w.appendLine("<h2 class=\"rail-title\">Errors</h2>")
            w.appendLine("<nav aria-label=\"Error families\"><ul>")
            sections.forEach { section ->
                w.appendLine(
                    "<li><a href=\"#${section.slug}\">${section.title.esc()} " +
                        "<span class=\"count\">${section.members.size}</span></a><ul>"
                )
                section.members.forEach { error ->
                    w.appendLine("<li><a href=\"#${error.code.slug()}\">${error.code.esc()}</a></li>")
                }
                w.appendLine("</ul></li>")
            }
            w.appendLine("</ul></nav>")
            w.appendLine("</aside>")

            // --- editor pane ---
            w.appendLine("<main class=\"pane\">")
            w.appendLine("<h1 class=\"doc-title\">Error Reference</h1>")
            w.appendLine("<p class=\"lede\">${lede.esc()}</p>")
            w.appendLine(
                "<p class=\"tally\"><strong>${errors.size}</strong> errors across " +
                    "<strong>${sections.size}</strong> families</p>"
            )

            sections.forEach { section ->
                w.appendLine("<section class=\"family\" id=\"${section.slug}\">")
                w.appendLine("<h2>${section.title.esc()}</h2>")
                w.appendLine("<p class=\"blurb\">${section.blurb.esc()}</p>")
                w.appendLine("<div class=\"entries\">")

                section.members.forEach { error ->
                    w.appendLine("<article class=\"entry\" id=\"${error.code.slug()}\">")
                    w.appendLine("<header>")
                    w.appendLine("<span class=\"code\">${error.code.esc()}</span>")
                    w.appendLine("<span class=\"type\">${error.simpleName.asString().esc()}</span>")
                    w.appendLine("</header>")
                    w.appendLine("<p class=\"message\">${error.message.esc()}</p>")
                    error.notes()?.let { w.appendLine("<p class=\"notes\">${it.esc()}</p>") }

                    val fields = raisedBy[error.code].orEmpty()
                    if (fields.isEmpty()) {
                        w.appendLine("<p class=\"none\">Not raised by any annotated field.</p>")
                    } else {
                        val label = if (fields.size == 1) "1 field" else "${fields.size} fields"
                        w.appendLine("<details class=\"raised\">")
                        w.appendLine("<summary>Raised by $label</summary>")
                        w.appendLine("<ul class=\"fields\">")
                        fields.forEach { f ->
                            val owner = f.substringBefore('.')
                            val prop = f.substringAfter('.')
                            w.appendLine(
                                "<li><span class=\"owner\">${owner.esc()}</span>" +
                                    "<span class=\"dot\">.</span>${prop.esc()}</li>"
                            )
                        }
                        w.appendLine("</ul>")
                        w.appendLine("</details>")
                    }
                    w.appendLine("</article>")
                }

                w.appendLine("</div>")
                w.appendLine("</section>")
            }

            w.appendLine("</main>")
            w.appendLine("</div>")
            w.appendLine("</body>")
            w.appendLine("</html>")
        }
    }

    private fun String.esc(): String = this
        .replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;")

    private fun String.slug(): String =
        lowercase().replace(Regex("[^a-z0-9]+"), "-").trim('-')
}
