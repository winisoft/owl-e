package com.wini.owle.data.sources

import com.wini.owle.data.pypi.VersionInfo
import com.wini.owle.data.pypi.VersionedMetadata

class FetchVersionedMetadata(
    pkg: String,
    version: String
) {

    val url: String = "https://pypi.org/pypi/{$pkg}/{$version}/json"

    fun fetch(): VersionedMetadata {
        // request url
        // get response string
        val responseString = ""
        return VersionedMetadata(
            VersionInfo(
                name = "",
                version = "",
                dependencies = emptyList(),
                classifiers = emptyList(),
                project_urls = emptyMap()
            ),
            emptyList(),
            emptyMap(),
            0
        )
    }
}