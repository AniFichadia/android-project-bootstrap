package com.anifichadia.bootstrap.app.shared

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

/**
 * Android [ViewBinding] compatible UI
 *
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
interface ViewBindingUi<ViewBindingT : ViewBinding> {
    val binding: ViewBindingT


    fun createBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): ViewBindingT

    fun bindView(binding: ViewBindingT)
}
