package com.anifichadia.app.shared

import java.lang.ref.WeakReference
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-27
 */


inline fun <T> weak(initialValue: T? = null) = object : ReadWriteProperty<Any?, T?> {
    private var reference: WeakReference<T?> = WeakReference(initialValue)

    override fun getValue(thisRef: Any?, property: KProperty<*>): T? = reference.get()
    
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        reference = WeakReference(value)
    }
}
