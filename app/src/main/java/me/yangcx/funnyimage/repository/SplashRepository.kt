package me.yangcx.funnyimage.repository

import android.arch.lifecycle.MutableLiveData
import me.yangcx.funnyimage.api.ApiService
import me.yangcx.funnyimage.db.FunnyDao
import me.yangcx.funnyimage.di.scope.RepositoryScope
import me.yangcx.funnyimage.entity.CollectInfo
import me.yangcx.funnyimage.entity.ImageInfo
import me.yangcx.funnyimage.entity.UnsplashContainer
import me.yangcx.funnyimage.extend.resultOnUi
import me.yangcx.funnyimage.extend.subscribeStatus
import me.yangcx.funnyimage.http.SingleStatusResult
import me.yangcx.funnyimage.http.StatusEnum
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject
import kotlin.concurrent.thread

@RepositoryScope
class SplashRepository @Inject constructor(private val retrofit: ApiService, private val dao: FunnyDao) {

    fun getSplashImage(data: MutableLiveData<SingleStatusResult<ImageInfo>>) {
        retrofit.getSplashImage()
                .resultOnUi()
                .subscribeStatus(data) { value, result ->
                    value.data = result.convertToImageInfo()
                    value.status = StatusEnum.SUCCESS
                }
    }

    fun collectImage(id: String) {
        dao.insertCollect(CollectInfo(id, true))
    }
}