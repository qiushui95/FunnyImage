package me.yangcx.funnyimage.repository

import android.arch.lifecycle.MutableLiveData
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import me.yangcx.funnyimage.api.ApiService
import me.yangcx.funnyimage.db.FunnyDao
import me.yangcx.funnyimage.di.scope.RepositoryScope
import me.yangcx.funnyimage.entity.CollectInfo
import me.yangcx.funnyimage.entity.ImageInfo
import me.yangcx.funnyimage.extend.resultOnUi
import me.yangcx.funnyimage.extend.subscribeStatus
import me.yangcx.funnyimage.http.SingleStatusResult
import me.yangcx.funnyimage.http.StatusEnum
import timber.log.Timber
import javax.inject.Inject

@RepositoryScope
class SplashRepository @Inject constructor(private val retrofit: ApiService, private val dao: FunnyDao) {

    fun getSplashImage(data: MutableLiveData<SingleStatusResult<ImageInfo>>, collectStatus: MutableLiveData<SingleStatusResult<Boolean>>) {
        retrofit.getSplashImage()
                .resultOnUi()
                .subscribeStatus(data) { value, result ->
                    value.data = result.convertToImageInfo()
                    value.status = StatusEnum.SUCCESS
                    collectStatus.value = SingleStatusResult(StatusEnum.SUCCESS, data = false)
                }
    }

    fun collectImage(id: String, collectStatus: MutableLiveData<SingleStatusResult<Boolean>>) {
        Observable.just(id)
                .map {
                    dao.insertCollect(CollectInfo(id, true))
                }.resultOnUi()
                .subscribeStatus(collectStatus) { value, result ->
                    Timber.e("result:$result")
                }
    }
}