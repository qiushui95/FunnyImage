package me.yangcx.funnyimage.ui.main

import android.arch.lifecycle.MutableLiveData
import me.yangcx.funnyimage.api.ApiService
import me.yangcx.funnyimage.db.FunnyDao
import me.yangcx.funnyimage.entity.ImageDetails
import me.yangcx.xfoundation.extend.subscribeOnIoObserveOnUi
import me.yangcx.xnetwork.callback.MultipleResponseObserver
import me.yangcx.xnetwork.entity.RequestResult
import me.yangcx.xnetwork.status.RequestStatus
import javax.inject.Inject

class HomeRepository @Inject constructor(private val retrofit: ApiService, private val dao: FunnyDao) {

    fun getNextPageImage(page: Int, pageSize: Int, data: MutableLiveData<RequestResult<ImageDetails>>) {
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
                .subscribe(object : MultipleResponseObserver<List<ImageDetails>, ImageDetails>(data) {
                    override fun onSuccess(value: RequestResult<ImageDetails>, result: List<ImageDetails>) {
                        value.dataList.addAll(result)
                        value.status = RequestStatus.SUCCESS
                    }
                })
    }
}