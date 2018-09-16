package me.yangcx.funnyimage.ui.main

import android.arch.lifecycle.MutableLiveData
import me.yangcx.funnyimage.api.ApiService
import me.yangcx.funnyimage.db.FunnyDao
import me.yangcx.funnyimage.entity.ImageDetails
import me.yangcx.funnyimage.entity.ImageInfo
import me.yangcx.funnyimage.entity.UnsplashContainer
import me.yangcx.xfoundation.extend.subscribeOnIoObserveOnUi
import me.yangcx.xnetwork.callback.MultipleResponseObserver
import me.yangcx.xnetwork.entity.MultipleStatusResult
import me.yangcx.xnetwork.status.RequestStatus
import timber.log.Timber
import javax.inject.Inject

class HomeRepository @Inject constructor(private val retrofit: ApiService, private val dao: FunnyDao) {

    fun getNextPageImage(data: MutableLiveData<MultipleStatusResult<ImageDetails>>) {
        retrofit.getImageList(INT_COUNT)
                .subscribeOnIoObserveOnUi()
                .subscribe(object : MultipleResponseObserver<List<UnsplashContainer>, ImageDetails>(data) {
                    override fun onSuccess(value: MultipleStatusResult<ImageDetails>, result: List<UnsplashContainer>) {
                        result.mapTo(value.dataList) {
                            Timber.e("======Repository=====${it.id}")
                            it.convertToImageDetails()
                        }
                        Timber.e("======list=====${value.dataList.map { it.id }}")
                        value.status = RequestStatus.SUCCESS
                    }
                })
    }

    companion object {
        private const val INT_COUNT = 25
    }
}