package com.wini.owle.air.api

import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension
import com.vladsch.flexmark.ext.definition.DefinitionExtension
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension
import com.vladsch.flexmark.ext.tables.TablesExtension
import com.vladsch.flexmark.ext.typographic.TypographicExtension
import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.util.data.MutableDataSet
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


fun commonMarkToHtml(markdown: String): String = MutableDataSet().apply {
        set(
            Parser.EXTENSIONS,
            listOf(
                TablesExtension.create(),
                TypographicExtension.create(),
                StrikethroughExtension.create(),
                AbbreviationExtension.create(),
                DefinitionExtension.create(),
                FootnoteExtension.create()
            )
        )
    }.let { options ->

        Parser.builder(options).build()
            .parse(markdown).run parser@ {
                HtmlRenderer.builder(this@parser).build()
                    .render(this)
            }
    }.run {
        Jsoup.parse("""<html><head></head><body>
            <div class="swagger-ui markdown">$this</div>
            </body></html>""".trimMargin())
            .ownerDocument()
            ?.applyTheme()
            ?.outerHtml()
            ?: "<html><body></body></html>"
    }

fun Document.applyTheme() = apply {
    head().append("""

        """.trimIndent())
}