package me.yangcx.funnyimage.ui.main

import android.arch.lifecycle.MutableLiveData
import me.yangcx.funnyimage.api.ApiService
import me.yangcx.funnyimage.db.FunnyDao
import me.yangcx.funnyimage.entity.ImageDetails
import me.yangcx.xfoundation.extend.subscribeOnIoObserveOnUi
import me.yangcx.xnetwork.callback.StatusObserver
import me.yangcx.xnetwork.entity.RequestResult
import me.yangcx.xnetwork.extend.addAll
import javax.inject.Inject

class HomeRepository @Inject constructor(private val retrofit: ApiService, private val dao: FunnyDao) {

    fun getNextPageImage(page: Int, pageSize: Int, data: MutableLiveData<MutableList<Any>>, status: MutableLiveData<RequestResult>) {
        retrofit.getImageList(page, pageSize)
                .map {
                    val imageInfoList = it.map { container ->
                        container.convertToImageInfo()
                    }
                    dao.insertImage(imageInfoList)
                    it.map { container ->
                        container.convertToImageDetails()
                    }
                }
                .subscribeOnIoObserveOnUi()
                .subscribe(object : StatusObserver<List<ImageDetails>>(status) {
                    override fun onSuccess(status: MutableLiveData<RequestResult>, result: List<ImageDetails>) {
                        data.value = mutableListOf<Any>()
                                .apply {
                                    addAll(data.value)
                                    addAll(result)
                                }
                        status.value = RequestResult.newSuccess()
                    }
                })
    }
}