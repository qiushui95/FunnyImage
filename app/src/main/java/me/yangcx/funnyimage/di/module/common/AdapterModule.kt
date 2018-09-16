package me.yangcx.funnyimage.di.module.common

import dagger.Module
import dagger.Provides
import me.yangcx.forrecyclerview.adapter.BaseDataAdapter
import me.yangcx.forrecyclerview.adapter.BasePagingAdapter

@Module
class AdapterModule {
    @Provides
    fun provideBaseAdapter(): BaseDataAdapter {
        return BaseDataAdapter()
    }
}