package me.yangcx.xdialog.notice.common

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.xdialog_info.view.*
import me.yangcx.xdialog.R
import me.yangcx.xdialog.base.BaseDialogFragment
import me.yangcx.xdialog.notice.loading.LoadingHolder
import me.yangcx.xdialog.notice.notice.NoticeHolder
import me.yangcx.xdialog.notice.progress.ProgressHolder
import me.yangcx.xdialog.other.XDialogConfig

internal class InfoDialog : BaseDialogFragment(), NoticeHolder, LoadingHolder, ProgressHolder {
    private val config by lazy { arguments?.getParcelable<InfoConfig>(KEY_INFO_CONFIG) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInfoType(view, config?.infoType)
        initNoticeType(view, config?.noticeType)
        initProgress(view, config?.progress)
        initLoading(view, config?.infoType == InfoType.LOADING)
        initMessage(view, config?.message)
        initDismiss(view, config?.infoType, config?.dismissDelay)
    }

    private fun initInfoType(view: View, infoType: InfoType?) {
        when (infoType) {
            InfoType.PROGRESS -> {
                view.pvProgress.visibility = View.VISIBLE
                view.ivStatus.visibility = View.GONE
                view.lvLoading.visibility = View.GONE
                view.lvLoading.stoptAnimation()
            }
            InfoType.LOADING -> {
                view.pvProgress.visibility = View.GONE
                view.ivStatus.visibility = View.GONE
                view.lvLoading.visibility = View.VISIBLE
                view.lvLoading.startAnimation()
            }
            else -> {
                view.pvProgress.visibility = View.GONE
                view.ivStatus.visibility = View.VISIBLE
                view.lvLoading.visibility = View.GONE
                view.lvLoading.stoptAnimation()
            }
        }
    }

    private fun initNoticeType(view: View, noticeType: NoticeType?) {
        view.ivStatus.setImageResource(when (noticeType) {
            NoticeType.ERROR -> R.drawable.ic_status_error
            NoticeType.SUCCESS -> R.drawable.ic_status_success
            else -> R.drawable.ic_status_info
        })
    }

    private fun initProgress(view: View, progress: Float?) {
        progress?.apply {
            view.pvProgress.updateProgress(this)
        }
    }

    private fun initLoading(view: View, isLoading: Boolean) {
        if (isLoading) {
            view.lvLoading.startAnimation()
        } else {
            view.lvLoading.stoptAnimation()
        }
    }

    private fun initMessage(view: View, message: String?) {
        view.tvMessage.text = message
    }

    private fun initDismiss(view: View, infoType: InfoType?, dismissDelay: Long?) {
        if (infoType == InfoType.NOTICE && dismissDelay != null) {
            view.llInfo.postDelayed({
                dismiss()
            }, dismissDelay)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        arguments?.putParcelable(KEY_INFO_CONFIG, config)
        super.onSaveInstanceState(outState)
    }

    override fun updateMessage(message: String?) {
        config?.message = message
        view?.apply {
            initMessage(this, message)
        }
    }

    override fun updateProgress(progress: Float) {
        config?.progress = progress
        view?.apply {
            initProgress(this, progress)
        }
    }

    override fun changeProgress(): ProgressHolder {
        changeInfoType(InfoType.PROGRESS)
        return this
    }

    override fun changeLoading(): LoadingHolder {
        changeInfoType(InfoType.LOADING)
        return this
    }

    override fun changeNotice(noticeType: NoticeType, dismissDelay: Long): NoticeHolder {
        changeInfoType(InfoType.NOTICE)
        config?.dismissDelay = dismissDelay
        view?.apply {
            initDismiss(this, InfoType.NOTICE, dismissDelay)
        }
        return this
    }

    private fun changeInfoType(infoType: InfoType) {
        config?.infoType = InfoType.PROGRESS
        view?.apply {
            initInfoType(this, infoType)
            initLoading(this, infoType == InfoType.LOADING)
        }
    }

    override fun onCancel(dialog: DialogInterface?) {
        super.onCancel(dialog)
        config?.onCanceledListener?.onCanceled(config?.infoType)
    }

    companion object {
        private const val KEY_INFO_CONFIG = "infoConfig"
        fun getInstance(baseConfig: XDialogConfig, infoConfig: InfoConfig): InfoDialog {
            val dialog = InfoDialog()
            val bundle = Bundle()
            bundle.putParcelable(KEY_INFO_CONFIG, infoConfig)
            bundle.putParcelable(KEY_NORMAL_CONFIG, baseConfig)
            dialog.arguments = bundle
            return dialog
        }
    }
}