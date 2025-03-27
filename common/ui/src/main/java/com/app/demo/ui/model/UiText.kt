package com.app.demo.ui.model

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import kotlinx.serialization.Serializable
import java.io.Serializable as JavaSerializable

@Serializable
sealed class UiText {
    @Serializable
    data class Id(@StringRes val resId: Int, val args: List<JavaSerializable> = emptyList()) : UiText()
    @Serializable
    data class Text(val text: String) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is Id -> {
                if (args.isNotEmpty()) context.getString(resId, *args.toTypedArray())
                else context.getString(resId)
            }
            is Text -> text
        }
    }

    @Composable
    fun asString(): String {
        val context = LocalContext.current
        return asString(context)
    }
}
