package com.anifichadia.sampleapp.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.anifichadia.bootstrap.app.shared.ViewBindingUi

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
abstract class MvvmFragment<ViewBindingT : ViewBinding> : Fragment(), MvvmUi, ViewBindingUi<ViewBindingT> {

    override lateinit var binding: ViewBindingT


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = createBinding(layoutInflater, container, savedInstanceState)
        return binding.root
    }


    override fun onStart() {
        super.onStart()

        bindView(binding)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        configureDi()

        createViewModels()
        configureViewModels()
        observeViewModels()
    }


    open fun configureDi() {}
}
