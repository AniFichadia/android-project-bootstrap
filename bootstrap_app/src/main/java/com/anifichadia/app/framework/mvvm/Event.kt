package com.anifichadia.app.framework.mvvm

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer as AndroidXObserver

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-13
 */
class Event<DataT>(
    private val data: DataT
) {
    var consumed = false
        private set

    fun consume(): DataT? = if (!consumed) {
        consumed = true
        data
    } else {
        null
    }

    class Observer<DataT>(private val handler: (DataT) -> Unit) : AndroidXObserver<Event<DataT>> {
        override fun onChanged(event: Event<DataT>?) {
            event?.consume()?.let { data -> handler(data) }
        }
    }
}


fun <DataT> LiveData<Event<DataT>>.observeEvent(owner: LifecycleOwner, handler: (DataT) -> Unit) {
    observe(owner, Event.Observer(handler))
}
