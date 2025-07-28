package com.wini.owle.ksp.utils

import java.util.Locale
import java.util.MissingResourceException
import java.util.ResourceBundle


object LocalizedStringResolver {
    fun resolve(key: String?): String {
        if (key.isNullOrBlank()) return ""
        return try {
            ResourceBundle.getBundle("docres", Locale.getDefault()).getString(key).trim('"')
        } catch (_: MissingResourceException) {
            key
        }
    }
}
