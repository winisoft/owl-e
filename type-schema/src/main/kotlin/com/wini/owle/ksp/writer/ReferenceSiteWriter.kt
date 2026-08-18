package com.wini.owle.ksp.writer

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.wini.owle.content.doc.DtoModelDoc
import com.wini.owle.ksp.utils.LocalizedStringResolver
import com.wini.owle.schema.wrapper.declaration.error.ErrorDeclaration


/**
 * The whole reference as one page: models, endpoints, errors.
 *
 * They are one page because they are one subject read three ways. A reader looking at
 * `Step.color` wants to know what rule governs it; a reader looking at `TYPE_INVALID_HEX_COLOR`
 * wants to know what carries it; a reader looking at `POST /api/step` wants to know what shape the
 * body takes. Split across three documents each of those is a search; together each is a link.
 *
 * The API section is derived rather than transcribed: an endpoint's route comes from the literal
 * `path` on its `@ApiEndpointDefinition` run through the same scheme `IOwleApi` uses at runtime,
 * so the documented route and the served route cannot disagree. Prose comes from the resource
 * bundle where it is bound, and is simply absent where it is not — the page never prints a
 * resource key and calls it documentation.
 */
class ReferenceSiteWriter(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) {

    // ---------------------------------------------------------------- inputs

    data class ModelEntry(
        val declaration: KSClassDeclaration,
        val doc: DtoModelDoc
    ) {
        val name: String get() = doc.name

        /** Last meaningful package segment, e.g. `...dto.workflows` -> "workflows". */
        val group: String
            get() = declaration.packageName.asString()
                .removePrefix("com.wini.owle.")
                .removePrefix("dto.")
                .substringBefore('.')
                .ifBlank { "core" }
    }

    private data class Endpoint(
        val model: ModelEntry,
        val id: String,
        val basePath: String,
        val tags: List<String>
    )

    /** The eight routes [IOwleApi] derives from a base path, in the order they are declared there. */
    private data class Operation(
        val method: String,
        val path: String,
        val key: String,
        val fallback: String
    )

    private fun operationsFor(base: String): List<Operation> = listOf(
        Operation("GET", base, "get.many", "List every record."),
        Operation("GET", "$base/{id}", "get.one", "Fetch one record by id."),
        Operation("POST", base, "post.one", "Create one record."),
        Operation("POST", "$base/batch", "post.many", "Create several records in one request."),
        Operation("PUT", base, "put.one", "Replace one record."),
        Operation("PUT", "$base/batch", "put.many", "Replace several records in one request."),
        Operation("DELETE", "$base/{id}", "delete.one", "Delete one record by id."),
        Operation("DELETE", "$base/batch", "delete.many", "Delete several records in one request.")
    )

    // ---------------------------------------------------------------- families

    private data class Family(val prefix: String, val title: String, val blurb: String)

    private val errorFamilies = listOf(
        Family(
            "errors.apiRequest.", "Request handling",
            "Raised before a request reaches the domain at all — the body, the parameters, or the " +
                "caller's identity was not usable."
        ),
        Family(
            "errors.constraint.", "Field constraints",
            "Raised by validation against a DTO. Each names the rule that was broken; the offending " +
                "value and the field it came from arrive in the error's detail."
        ),
        Family(
            "errors.db.", "Database",
            "Raised when the store itself refuses or fails to complete the work. These describe the " +
                "condition, not the statement that provoked it."
        )
    )

    // ---------------------------------------------------------------- entry point

    fun generate(
        models: List<ModelEntry>,
        errors: List<ErrorDeclaration>,
        endpointAnnotationOf: (KSClassDeclaration) -> Triple<String, List<String>, String>?
    ) {
        val sortedModels = models.sortedWith(compareBy({ it.group }, { it.name }))

        val endpoints = sortedModels.mapNotNull { m ->
            endpointAnnotationOf(m.declaration)?.let { (id, tags, path) ->
                val trimmed = path.trim('/')
                if (trimmed.isBlank()) {
                    logger.warn("[reference] ${m.name} declares @ApiEndpointDefinition with no path; skipped")
                    return@let null
                }
                Endpoint(m, id, "/api/$trimmed", tags.toList())
            }
        }

        // an endpoint route serving two models is a collision at runtime, not just in the docs
        endpoints.groupBy { it.basePath }
            .filterValues { it.size > 1 }
            .forEach { (path, claimants) ->
                logger.warn(
                    "[reference] route $path is claimed by ${claimants.size} endpoints: " +
                        claimants.joinToString { it.model.name }
                )
            }

        val raisedBy: Map<String, List<Pair<String, String>>> =
            buildMap<String, MutableList<Pair<String, String>>> {
                sortedModels.forEach { m ->
                    m.doc.properties.forEach { p ->
                        p.constraints.forEach { c ->
                            getOrPut(c.errorCode) { mutableListOf() } += m.name to p.name
                        }
                    }
                }
            }.mapValues { (_, v) -> v.distinct().sortedWith(compareBy({ it.first }, { it.second })) }

        val errorSections = buildList {
            errorFamilies.forEach { f ->
                val members = errors.filter { it.errorAnnotation.errorResKey.startsWith(f.prefix) }
                    .sortedBy { it.code }
                if (members.isNotEmpty()) add(Triple(f.title, f.blurb, members))
            }
            val rest = errors.filter { e -> errorFamilies.none { e.errorAnnotation.errorResKey.startsWith(it.prefix) } }
                .sortedBy { it.code }
            if (rest.isNotEmpty()) add(Triple("Other", "Errors not yet filed into a family.", rest))
        }

        codeGenerator.createNewFile(
            dependencies = Dependencies(false),
            packageName = "com.wini.owle.reference",
            fileName = "Reference",
            extensionName = "html"
        ).bufferedWriter().use { w ->
            w.appendLine("<!doctype html>")
            w.appendLine("<html lang=\"en\">")
            w.appendLine("<head>")
            w.appendLine("<meta charset=\"utf-8\">")
            w.appendLine("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">")
            w.appendLine("<title>Owl-E Reference</title>")
            w.appendLine("<style>${DocStyle.CSS}</style>")
            w.appendLine("</head>")
            w.appendLine("<body>")
            w.appendLine("<div class=\"shell\">")
            writeRail(w, sortedModels, endpoints, errorSections)
            w.appendLine("<main class=\"pane\">")
            w.appendLine("<h1 class=\"doc-title\">Owl-E Reference</h1>")
            w.appendLine(
                "<p class=\"lede\">Three views of one system. A model describes the shape of a record, " +
                    "an endpoint describes how to move one across the wire, and an error describes the " +
                    "rule that turns one away. Every constraint on a model links to the error it raises, " +
                    "and every error lists the fields that raise it.</p>"
            )
            w.appendLine(
                "<p class=\"tally\"><strong>${sortedModels.size}</strong> models &middot; " +
                    "<strong>${endpoints.size}</strong> endpoints &middot; " +
                    "<strong>${errors.size}</strong> errors</p>"
            )
            writeModels(w, sortedModels)
            writeApi(w, endpoints)
            writeErrors(w, errorSections, raisedBy)
            w.appendLine("</main>")
            w.appendLine("</div>")
            w.appendLine("</body>")
            w.appendLine("</html>")
        }

        logger.warn(
            "[reference] ${sortedModels.size} models, ${endpoints.size} endpoints, ${errors.size} errors"
        )
    }

    // ---------------------------------------------------------------- rail

    private fun writeRail(
        w: Appendable,
        models: List<ModelEntry>,
        endpoints: List<Endpoint>,
        errorSections: List<Triple<String, String, List<ErrorDeclaration>>>
    ) {
        w.appendLine("<aside class=\"rail\">")
        w.appendLine("<h2 class=\"rail-title\">Reference</h2>")
        w.appendLine("<nav aria-label=\"Reference contents\"><ul>")

        w.appendLine("<li><a href=\"#models\">Models <span class=\"count\">${models.size}</span></a><ul>")
        models.groupBy { it.group }.toSortedMap().forEach { (group, members) ->
            w.appendLine(
                "<li><a href=\"#models-${group.slug()}\">${group.esc()} " +
                    "<span class=\"count\">${members.size}</span></a></li>"
            )
        }
        w.appendLine("</ul></li>")

        w.appendLine("<li><a href=\"#api\">API <span class=\"count\">${endpoints.size}</span></a><ul>")
        endpoints.groupBy { it.model.group }.toSortedMap().forEach { (group, members) ->
            w.appendLine(
                "<li><a href=\"#api-${group.slug()}\">${group.esc()} " +
                    "<span class=\"count\">${members.size}</span></a></li>"
            )
        }
        w.appendLine("</ul></li>")

        val errorCount = errorSections.sumOf { it.third.size }
        w.appendLine("<li><a href=\"#errors\">Errors <span class=\"count\">$errorCount</span></a><ul>")
        errorSections.forEach { (title, _, members) ->
            w.appendLine(
                "<li><a href=\"#errors-${title.slug()}\">${title.esc()} " +
                    "<span class=\"count\">${members.size}</span></a></li>"
            )
        }
        w.appendLine("</ul></li>")

        w.appendLine("</ul></nav>")
        w.appendLine("</aside>")
    }

    // ---------------------------------------------------------------- models

    private fun writeModels(w: Appendable, models: List<ModelEntry>) {
        w.appendLine("<section class=\"category\" id=\"models\">")
        w.appendLine("<h1 class=\"category-title\">Models</h1>")
        w.appendLine(
            "<p class=\"blurb\">Every persisted record type, with the rules each field carries. " +
                "A constraint links to the error it raises.</p>"
        )

        models.groupBy { it.group }.toSortedMap().forEach { (group, members) ->
            w.appendLine("<section class=\"family\" id=\"models-${group.slug()}\">")
            w.appendLine("<h2>${group.esc()}</h2>")
            w.appendLine("<div class=\"entries\">")

            members.forEach { m ->
                w.appendLine("<article class=\"entry\" id=\"model-${m.name.slug()}\">")
                w.appendLine("<header><span class=\"code model\">${m.name.esc()}</span></header>")
                val desc = m.doc.description.trim()
                if (desc.isNotBlank() && !desc.startsWith("No type-level")) {
                    w.appendLine("<p class=\"message\">${desc.esc()}</p>")
                }

                w.appendLine("<div class=\"scroller\"><table class=\"props\">")
                w.appendLine(
                    "<thead><tr><th>Field</th><th>Type</th><th>Notes</th><th>Rules</th></tr></thead><tbody>"
                )
                m.doc.properties.forEach { p ->
                    val pdesc = p.description.trim().takeUnless { it.startsWith("No documentation") }.orEmpty()
                    w.appendLine("<tr>")
                    w.appendLine("<td class=\"f-name\">${p.name.esc()}</td>")
                    w.appendLine("<td class=\"f-type\">${p.type.substringAfterLast('.').esc()}</td>")
                    w.appendLine("<td class=\"f-desc\">${pdesc.esc()}</td>")
                    w.append("<td class=\"f-rules\">")
                    if (p.constraints.isEmpty()) {
                        w.append("<span class=\"none\">&mdash;</span>")
                    } else {
                        p.constraints.map { it.errorCode }.distinct().sorted().forEach { code ->
                            w.append("<a class=\"rule\" href=\"#error-${code.slug()}\">${code.esc()}</a>")
                        }
                    }
                    w.appendLine("</td>")
                    w.appendLine("</tr>")
                }
                w.appendLine("</tbody></table></div>")
                w.appendLine("</article>")
            }

            w.appendLine("</div>")
            w.appendLine("</section>")
        }
        w.appendLine("</section>")
    }

    // ---------------------------------------------------------------- api

    private fun writeApi(w: Appendable, endpoints: List<Endpoint>) {
        w.appendLine("<section class=\"category\" id=\"api\">")
        w.appendLine("<h1 class=\"category-title\">API</h1>")
        w.appendLine(
            "<p class=\"blurb\">Every endpoint exposes the same eight routes over its model. The " +
                "routes below are derived from each endpoint's declared path through the same scheme " +
                "the server applies, so they match what is actually served.</p>"
        )

        endpoints.groupBy { it.model.group }.toSortedMap().forEach { (group, members) ->
            w.appendLine("<section class=\"family\" id=\"api-${group.slug()}\">")
            w.appendLine("<h2>${group.esc()}</h2>")
            w.appendLine("<div class=\"entries\">")

            members.forEach { e ->
                w.appendLine("<article class=\"entry\" id=\"api-${e.model.name.slug()}\">")
                w.appendLine("<header>")
                w.appendLine("<span class=\"code\">${e.basePath.esc()}</span>")
                w.appendLine(
                    "<a class=\"type\" href=\"#model-${e.model.name.slug()}\">${e.model.name.esc()}</a>"
                )
                w.appendLine("</header>")

                LocalizedStringResolver.resolve("${e.id}.description")
                    .takeIf { it.isNotBlank() && it != "${e.id}.description" }
                    ?.let { w.appendLine("<p class=\"message\">${it.esc()}</p>") }

                if (e.tags.isNotEmpty()) {
                    w.appendLine(
                        "<p class=\"tags\">${e.tags.joinToString("") { "<span class=\"tag\">${it.esc()}</span>" }}</p>"
                    )
                }

                w.appendLine("<div class=\"scroller\"><table class=\"routes\">")
                w.appendLine("<thead><tr><th>Method</th><th>Route</th><th>Does</th></tr></thead><tbody>")
                operationsFor(e.basePath).forEach { op ->
                    val summary = LocalizedStringResolver.resolve("${e.id}.${op.key}.description")
                        .takeIf { it.isNotBlank() && it != "${e.id}.${op.key}.description" }
                        ?: op.fallback
                    w.appendLine("<tr>")
                    w.appendLine("<td><span class=\"verb v-${op.method.lowercase()}\">${op.method}</span></td>")
                    w.appendLine("<td class=\"f-type\">${op.path.esc()}</td>")
                    w.appendLine("<td class=\"f-desc\">${summary.esc()}</td>")
                    w.appendLine("</tr>")
                }
                w.appendLine("</tbody></table></div>")
                w.appendLine("</article>")
            }

            w.appendLine("</div>")
            w.appendLine("</section>")
        }
        w.appendLine("</section>")
    }

    // ---------------------------------------------------------------- errors

    private fun writeErrors(
        w: Appendable,
        sections: List<Triple<String, String, List<ErrorDeclaration>>>,
        raisedBy: Map<String, List<Pair<String, String>>>
    ) {
        w.appendLine("<section class=\"category\" id=\"errors\">")
        w.appendLine("<h1 class=\"category-title\">Errors</h1>")
        w.appendLine(
            "<p class=\"blurb\">Every failure carries a stable code, a message stating the rule in " +
                "general terms, and a detail naming the specific value that broke it. Codes are the " +
                "contract: they are safe to branch on and will not change wording underneath you.</p>"
        )

        sections.forEach { (title, blurb, members) ->
            w.appendLine("<section class=\"family\" id=\"errors-${title.slug()}\">")
            w.appendLine("<h2>${title.esc()}</h2>")
            w.appendLine("<p class=\"blurb\">${blurb.esc()}</p>")
            w.appendLine("<div class=\"entries\">")

            members.forEach { error ->
                w.appendLine("<article class=\"entry\" id=\"error-${error.code.slug()}\">")
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
                    fields.forEach { (owner, prop) ->
                        w.appendLine(
                            "<li><a href=\"#model-${owner.slug()}\">" +
                                "<span class=\"owner\">${owner.esc()}</span>" +
                                "<span class=\"dot\">.</span>${prop.esc()}</a></li>"
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
        w.appendLine("</section>")
    }

    // ---------------------------------------------------------------- helpers

    private fun ErrorDeclaration.notes(): String? = docString?.trim()
        ?.lineSequence()
        ?.map { it.trim().removePrefix("*").trim() }
        ?.filter { it.isNotBlank() }
        ?.joinToString(" ")
        ?.takeIf { it.isNotBlank() && it != message }

    private fun String.esc(): String = this
        .replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;")

    private fun String.slug(): String =
        lowercase().replace(Regex("[^a-z0-9]+"), "-").trim('-')
}
