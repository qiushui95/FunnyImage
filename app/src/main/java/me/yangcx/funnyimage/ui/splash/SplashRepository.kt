package me.yangcx.funnyimage.ui.splash

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.Observable
import me.yangcx.funnyimage.api.ApiService
import me.yangcx.funnyimage.db.FunnyDao
import me.yangcx.funnyimage.di.scope.RepositoryScope
import me.yangcx.funnyimage.entity.CollectInfo
import me.yangcx.funnyimage.entity.ImageDetails
import me.yangcx.funnyimage.entity.ImageInfo
import me.yangcx.funnyimage.entity.UnsplashContainer
import me.yangcx.xfoundation.extend.observeOnIo
import me.yangcx.xfoundation.extend.subscribeOnIo
import me.yangcx.xfoundation.extend.subscribeOnIoObserveOnUi
import me.yangcx.xnetwork.callback.SimpleObserver
import me.yangcx.xnetwork.callback.StatusObserver
import me.yangcx.xnetwork.entity.RequestResult
import me.yangcx.xnetwork.status.RequestStatus
import javax.inject.Inject

@RepositoryScope
class SplashRepository @Inject constructor(private val retrofit: ApiService, private val dao: FunnyDao) {

    fun getSplashImage(status: MutableLiveData<RequestResult>): LiveData<ImageInfo> {
        retrofit.getSplashImage()
                .map {
                    it.convertToImageInfo()
                }
                .subscribeOnIo()
                .observeOnIo()
                .subscribe(
                        object : StatusObserver<ImageInfo>(status) {
                            override fun onSuccess(status: MutableLiveData<RequestResult>, result: ImageInfo) {
                                val insertCount = dao.insertImage(result) > 0
                                status.postValue(if (insertCount) {
                                    RequestResult.newSuccess()
                                } else {
                                    RequestResult.newFailed("插入失败")
                                })
                            }
                        })
        return dao.getSplash(System.currentTimeMillis())
    }

    fun collectImage(id: String, collectStatus: MutableLiveData<Boolean>) {
        Observable.just(id)
                .map {
                    dao.insertCollect(CollectInfo(id, !(collectStatus.value == true)))
                }.subscribeOnIoObserveOnUi()
                .subscribe(object : SimpleObserver<Long>() {
                    override fun onSuccess(result: Long) {
                        if (result != -1L) {
                            collectStatus.value = !(collectStatus.value == true)
                        }
                    }
                })
    }
}