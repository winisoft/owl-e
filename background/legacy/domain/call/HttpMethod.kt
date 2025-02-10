package com.wini.owle.air.domain.call

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.wini.owle.air.db.backoffice.call.HttpMethod as DbHttpMethod

sealed interface HttpMethod {

    abstract val key: String
    val enabledIcon: ImageVector
    val disabledIcon: ImageVector

    object Get: HttpMethod {
        override val key: String = "GET"
        override val enabledIcon: ImageVector
            get() = Icons.Filled.Wysiwyg
        override val disabledIcon: ImageVector
            get() = Icons.Outlined.Wysiwyg
    }

    object Post: HttpMethod {
        override val key: String = "POST"
        override val enabledIcon: ImageVector
            get() = Icons.Filled.PostAdd
        override val disabledIcon: ImageVector
            get() = Icons.Outlined.PostAdd
    }

    object Put: HttpMethod {
        override val key: String = "PUT"
        override val enabledIcon: ImageVector
            get() = Icons.Filled.ReadMore
        override val disabledIcon: ImageVector
            get() = Icons.Outlined.ReadMore
    }

    object Delete: HttpMethod {
        override val key: String = "DELETE"
        override val enabledIcon: ImageVector
            get() = Icons.Filled.DeleteSweep
        override val disabledIcon: ImageVector
            get() = Icons.Outlined.DeleteSweep
    }

    object Head: HttpMethod {
        override val key: String = "HEAD"
        override val enabledIcon: ImageVector
            get() = Icons.Filled.Segment
        override val disabledIcon: ImageVector
            get() = Icons.Outlined.Segment
    }

    object Connect: HttpMethod {
        override val key: String = "CONNECT"
        override val enabledIcon: ImageVector
            get() = Icons.Filled.ConnectWithoutContact
        override val disabledIcon: ImageVector
            get() = Icons.Outlined.ConnectWithoutContact
    }

    object Options: HttpMethod {
        override val key: String = "OPTIONS"
        override val enabledIcon: ImageVector
            get() = Icons.Filled.EventNote
        override val disabledIcon: ImageVector
            get() = Icons.Outlined.EventNote
    }

    object Trace: HttpMethod {
        override val key: String = "TRACE"
        override val enabledIcon: ImageVector
            get() = Icons.Filled.AltRoute
        override val disabledIcon: ImageVector
            get() = Icons.Outlined.AltRoute
    }

    fun toEntity(): DbHttpMethod = when(this) {
        Get -> DbHttpMethod.Get
        Post -> DbHttpMethod.Post
        Put -> DbHttpMethod.Put
        Delete -> DbHttpMethod.Delete
        Head -> DbHttpMethod.Head
        Connect -> DbHttpMethod.Connect
        Options -> DbHttpMethod.Options
        Trace -> DbHttpMethod.Trace
    }

    companion object {
        fun fromString(value: String): HttpMethod = when(value) {
            Get.key -> Get
            Post.key -> Post
            Put.key -> Put
            Delete.key -> Delete
            Head.key -> Head
            Connect.key -> Connect
            Options.key -> Options
            Trace.key -> Trace
            else -> error("Unanticipated HttpMethod key")
        }
    }
}