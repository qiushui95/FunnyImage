package me.yangcx.forrecyclerview.holder

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.annotation.LayoutRes
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlin.reflect.KClass

abstract class BaseViewModelHolder<T, VM : ViewModel>(private val viewModelClass: KClass<VM>, @LayoutRes layoutRes: Int, inflater: LayoutInflater, parent: ViewGroup) : BaseHolder<T>(layoutRes, inflater, parent) {
    protected val viewModel by lazy {
        ViewModelProviders.of(itemView.context as FragmentActivity).get(viewModelClass.java)
    }
}