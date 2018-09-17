package me.yangcx.funnyimage.ui.main

import android.arch.lifecycle.MutableLiveData
import me.yangcx.funnyimage.api.ApiService
import me.yangcx.funnyimage.db.FunnyDao
import me.yangcx.funnyimage.entity.ImageDetails
import me.yangcx.xfoundation.extend.subscribeOnIoObserveOnUi
import me.yangcx.xnetwork.callback.MultipleResponseObserver
import me.yangcx.xnetwork.entity.MultipleStatusResult
import me.yangcx.xnetwork.status.RequestStatus
import timber.log.Timber
import javax.inject.Inject

class HomeRepository @Inject constructor(private val retrofit: ApiService, private val dao: FunnyDao) {

    fun getNextPageImage(data: MutableLiveData<MultipleStatusResult<ImageDetails>>) {
        retrofit.getImageList((data.value?.dataList?.size ?: 0)/INT_COUNT+1, INT_COUNT)
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
                    override fun onSuccess(value: MultipleStatusResult<ImageDetails>, result: List<ImageDetails>) {
                        value.dataList.addAll(result)
                        value.status = RequestStatus.SUCCESS
                    }
                })
    }

    companion object {
        private const val INT_COUNT = 25
    }
}