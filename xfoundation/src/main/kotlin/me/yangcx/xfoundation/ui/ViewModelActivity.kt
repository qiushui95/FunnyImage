package me.yangcx.xfoundation.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.annotation.LayoutRes
import kotlin.reflect.KClass

abstract class ViewModelActivity<T : ViewModel>(@LayoutRes private val layoutRes: Int, clazz: KClass<T>) : FoundationActivity(layoutRes) {
    protected val viewModel by lazy {
        ViewModelProviders.of(this)
                .get(clazz.java)
    }
}