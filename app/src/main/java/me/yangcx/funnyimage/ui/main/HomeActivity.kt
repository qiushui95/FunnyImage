package me.yangcx.funnyimage.ui.main

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*
import me.yangcx.forrecyclerview.adapter.BaseDataAdapter
import me.yangcx.funnyimage.R
import me.yangcx.funnyimage.entity.ImageDetails
import me.yangcx.xfoundation.ui.ViewModelActivity
import javax.inject.Inject

class HomeActivity : ViewModelActivity<HomeViewModel>(R.layout.activity_home, HomeViewModel::class) {
    @Inject
    lateinit var adapter: BaseDataAdapter

    override fun initThis() {
        bindRecycler()
        bindRefresh()
        bindViewModel()
        srlRefresh.autoRefresh()
    }

    private fun bindRecycler() {
        adapter.register(ImageDetails::class, HomeViewHolder::class)
        rvImage.adapter = adapter
    }

    private fun bindRefresh() {
        srlRefresh.setOnRefreshListener {
            viewModel.refreshData()
        }
        srlRefresh.setOnLoadMoreListener {
            viewModel.getNextPage()
        }
    }

    private fun bindViewModel() {
        viewModel.init()
        viewModel.dataList.observe(this, Observer {
            it?.also { result ->
                if (result.isNotLoading()) {
                    if (result.isSuccess()) {
                        adapter.items = result.dataList
                        finishRefresh(true)
                    } else if (result.isFailed()) {
                        finishRefresh(false)
                        Toast.makeText(this, result.errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun finishRefresh(success: Boolean) {
        srlRefresh.finishRefresh(success)
        srlRefresh.finishLoadMore(success)
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context.applicationContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}