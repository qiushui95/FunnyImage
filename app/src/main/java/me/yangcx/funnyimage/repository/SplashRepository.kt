package me.yangcx.funnyimage.repository

import android.arch.lifecycle.MutableLiveData
import io.reactivex.Observable
import me.yangcx.funnyimage.api.ApiService
import me.yangcx.funnyimage.db.FunnyDao
import me.yangcx.funnyimage.di.scope.RepositoryScope
import me.yangcx.funnyimage.entity.CollectInfo
import me.yangcx.funnyimage.entity.ImageInfo
import me.yangcx.funnyimage.entity.UnsplashContainer
import me.yangcx.xfoundation.extend.subscribeOnIoObserveOnUi
import me.yangcx.xnetwork.callback.SingleResponseObserver
import me.yangcx.xnetwork.entity.SingleStatusResult
import me.yangcx.xnetwork.status.RequestStatus
import javax.inject.Inject

@RepositoryScope
class SplashRepository @Inject constructor(private val retrofit: ApiService, private val dao: FunnyDao) {

    fun getSplashImage(data: MutableLiveData<SingleStatusResult<ImageInfo>>) {
        retrofit.getSplashImage()
                .subscribeOnIoObserveOnUi()
                .subscribe(
                        object : SingleResponseObserver<UnsplashContainer, ImageInfo>(data) {
                            override fun onSuccess(value: SingleStatusResult<ImageInfo>, result: UnsplashContainer) {
                                value.data = result.convertToImageInfo()
                                value.status = RequestStatus.SUCCESS
                            }
                        })
    }

    fun collectImage(id: String, collectStatus: MutableLiveData<SingleStatusResult<Boolean>>) {
        Observable.just(id)
                .map {
                    dao.insertCollect(CollectInfo(id, !(collectStatus.value?.data == true)))
                }.subscribeOnIoObserveOnUi()
                .subscribe(object : SingleResponseObserver<Long, Boolean>(collectStatus) {
                    override fun onSuccess(value: SingleStatusResult<Boolean>, result: Long) {
                        if (result != -1L) {
                            value.data = !(value.data == true)
                            value.status = RequestStatus.SUCCESS
                        } else {
                            value.status = RequestStatus.ERROR
                            value.errorMessage = "发生错误"
                        }
                    }
                })
    }
}