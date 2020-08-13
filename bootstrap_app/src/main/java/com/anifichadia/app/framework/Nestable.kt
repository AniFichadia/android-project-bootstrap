package com.anifichadia.app.framework

import android.app.Activity
import android.view.View
import android.view.ViewParent
import androidx.fragment.app.Fragment
import kotlin.reflect.full.isSubclassOf

/**
 * Represents a element (such as a UI) that can be nested within something else
 *
 * @author Aniruddh Fichadia
 * @date 2017-07-24
 */
interface Nestable {
    val parent: Nestable?


    fun <ClassT> findParentWithImplementation(nestable: Nestable, clazz: Class<ClassT>): ClassT? {
        var parent = nestable.parent
        while (parent != null && !parent::class.isSubclassOf(clazz::class)) {
            parent = parent.parent
        }

        return if (parent != null) {
            @Suppress("UNCHECKED_CAST")
            parent as ClassT
        } else {
            null
        }
    }
}

fun Activity.getNestableParent(): Nestable? {
    val nestableParent = application
    return if (nestableParent is Nestable) {
        nestableParent
    } else {
        null
    }
}

fun Fragment.getNestableParent(): Nestable? {
    var nestableParent: Any? = parentFragment
    if (nestableParent == null) {
        nestableParent = activity
    }

    return if (nestableParent is Nestable) {
        nestableParent
    } else {
        null
    }
}

fun View.getNestableParent(): Nestable? {
    // Try the ViewParents
    var viewParent: ViewParent? = parent
    while (viewParent != null && viewParent !is Nestable) {
        viewParent = viewParent.parent
    }

    if (viewParent is Nestable) {
        return viewParent
    }

    // Try the context
    val context = context
    return if (context is Nestable) {
        context
    } else {
        null
    }
}
