package com.wini.owle.ksp.utils

import java.util.Locale
import java.util.MissingResourceException
import java.util.ResourceBundle


object LocalizedStringResolver {

    /**
     * Deliberately fixed rather than [Locale.getDefault]. This resolver feeds code generation, and
     * `docres_en_US.properties` is the only variant carrying content — resolving against the build
     * machine's locale meant a developer outside the US generated a different `ErrorResourceExtensions`
     * (every lookup falling through to the sparser base bundle and emitting raw keys as values).
     * Generated sources must not depend on where they were generated.
     */
    private val GENERATION_LOCALE: Locale = Locale.US

    private val bundle: ResourceBundle by lazy {
        ResourceBundle.getBundle("docres", GENERATION_LOCALE)
    }

    /** The resolved string, or the key itself when nothing is bound to it. */
    fun resolve(key: String?): String {
        if (key.isNullOrBlank()) return ""
        return try {
            bundle.getString(key).trim('"')
        } catch (_: MissingResourceException) {
            key
        }
    }

    /** Whether [key] is actually bound, so callers can report a gap instead of echoing the key. */
    fun isBound(key: String?): Boolean {
        if (key.isNullOrBlank()) return false
        return try {
            bundle.getString(key); true
        } catch (_: MissingResourceException) {
            false
        }
    }
}
