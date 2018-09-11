package me.yangcx.funnyimage.repository

import android.arch.lifecycle.MutableLiveData
import io.reactivex.Observable
import me.yangcx.funnyimage.api.ApiService
import me.yangcx.funnyimage.db.FunnyDao
import me.yangcx.funnyimage.di.scope.RepositoryScope
import me.yangcx.funnyimage.entity.CollectInfo
import me.yangcx.funnyimage.entity.ImageInfo
import me.yangcx.funnyimage.extend.resultOnUi
import me.yangcx.funnyimage.extend.subscribeStatus
import me.yangcx.funnyimage.http.SingleStatusResult
import me.yangcx.funnyimage.http.StatusEnum
import javax.inject.Inject

@RepositoryScope
class SplashRepository @Inject constructor(private val retrofit: ApiService, private val dao: FunnyDao) {

    fun getSplashImage(data: MutableLiveData<SingleStatusResult<ImageInfo>>, collectStatus: MutableLiveData<SingleStatusResult<Boolean>>) {
        retrofit.getSplashImage()
                .resultOnUi()
                .subscribeStatus(data) { value, result ->
                    value.data = result.convertToImageInfo()
                    value.status = StatusEnum.SUCCESS
                }
    }

    fun collectImage(id: String, collectStatus: MutableLiveData<SingleStatusResult<Boolean>>) {
        Observable.just(id)
                .map {
                    dao.insertCollect(CollectInfo(id, !(collectStatus.value?.data == true)))
                }.resultOnUi()
                .subscribeStatus(collectStatus) { value, result ->
                    if (result != -1L) {
                        value.data = !(value.data == true)
                        value.status = StatusEnum.SUCCESS
                    } else {
                        value.status = StatusEnum.ERROR
                        value.errorMessage = "发生错误"
                    }
                }
    }
}