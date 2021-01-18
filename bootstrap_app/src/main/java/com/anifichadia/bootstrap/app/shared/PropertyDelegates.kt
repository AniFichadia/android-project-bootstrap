package com.anifichadia.bootstrap.app.shared

import java.lang.ref.WeakReference
import java.util.concurrent.atomic.AtomicReference
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

inline fun <T> atomic(initialValue: T) = object : ReadWriteProperty<Any?, T> {
    private val value = AtomicReference(initialValue)

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = value.get()

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value.set(value)
    }
}
