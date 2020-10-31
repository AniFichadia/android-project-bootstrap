package com.anifichadia.sampleapp.feature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.anifichadia.bootstrap.app.shared.ViewBindingUi

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
abstract class MvvmActivity<ViewBindingT : ViewBinding> : AppCompatActivity(), MvvmUi, ViewBindingUi<ViewBindingT> {

    override lateinit var binding: ViewBindingT


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //region ViewBindingUi
        binding = createBinding(layoutInflater, null, savedInstanceState)
        setContentView(binding.root)
        bindView(binding)
        //endregion

        configureDi()

        createViewModels()
        configureViewModels()
        observeViewModels()
    }

    open fun configureDi() {}
}
