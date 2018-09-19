package me.yangcx.funnyimage.ui.main

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.activity_home.*
import me.yangcx.forrecyclerview.adapter.BaseDataAdapter
import me.yangcx.funnyimage.R
import me.yangcx.funnyimage.entity.ImageDetails
import me.yangcx.xfoundation.ui.ViewModelActivity
import javax.inject.Inject

class HomeActivity : ViewModelActivity<HomeViewModel>(R.layout.activity_home, HomeViewModel::class) {
    @Inject
    lateinit var adapter: BaseDataAdapter

    private val layoutManager by lazy {
        FlexboxLayoutManager(this).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            alignItems = AlignItems.STRETCH
        }
    }

    override fun initThis() {
        bindRecycler()
        bindRefresh()
        bindViewModel()
        srlRefresh.autoRefresh()
    }

    private fun bindRecycler() {
        rvImage.layoutManager = layoutManager
        adapter.register(ImageDetails::class, HomeBinder())
        rvImage.adapter = adapter
    }

    private fun bindRefresh() {
        srlRefresh.setOnRefreshListener {
            viewModel.refreshData()
        }
        srlRefresh.setOnLoadMoreListener {
            viewModel
        }
    }

    private fun bindViewModel() {
        viewModel.dataList.observe(this, Observer {
            if (it != null) {
                adapter.items = it
            }
        })
        viewModel.status.observe(this, Observer {
            it?.also { result ->
                if (result.isFailed) {
                    finishRefresh(false)
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                } else if (result.isSuccess) {
                    finishRefresh(true)
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
            context.startActivity(intent)
        }
    }
}