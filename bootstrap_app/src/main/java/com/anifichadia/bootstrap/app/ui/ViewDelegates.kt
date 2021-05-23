package com.anifichadia.bootstrap.app.ui

import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author Aniruddh Fichadia
 * @date 2021-05-16
 */

fun View.enabledDelegate() = propertyDelegate(
    getter = { isEnabled },
    setter = { value -> isEnabled = value == true }
)

fun TextView.textDelegate(hideWhenBlank: Boolean = false) = propertyDelegate(
    getter = { text },
    setter = { value ->
        text = value
        isVisible = if (hideWhenBlank) !value.isNullOrBlank() else true
    }
)

fun View.contentDescriptionDelegate() = propertyDelegate(
    getter = { contentDescription },
    setter = { value -> contentDescription = value }
)


fun <TargetT : Any, ReturnT> TargetT.propertyDelegate(
    getter: TargetT.() -> ReturnT,
    setter: TargetT.(ReturnT?) -> Unit,
) = object : ReadWriteProperty<Any?, ReturnT?> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): ReturnT? = getter.invoke(this@propertyDelegate)

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: ReturnT?) =
        setter.invoke(this@propertyDelegate, value)
}
