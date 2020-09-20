package com.teko.hoangviet.androidtest.base.adapter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teko.hoangviet.androidtest.R
import kotlinx.android.synthetic.main.layout_base_recyclerview.view.*

class BaseRecyclerView : RelativeLayout {
    private var mAdapter: EndlessLoadingRecyclerViewAdapter<*>? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_base_recyclerview, this, true)
    }

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(
        context,
        attrs
    ) {
        setParams(attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        setParams(attrs)
    }

    private fun setParams(attrs: AttributeSet) {
        val a =
            context!!.theme.obtainStyledAttributes(attrs, R.styleable.BaseRecyclerView, 0, 0)
        a.getDrawable(R.styleable.BaseRecyclerView_brv_background)?.let {
            rcv_data.background = it
        }
        val padding = a.getDimension(R.styleable.BaseRecyclerView_brv_padding, 0f)
        val textNoResult =
            a.getString(R.styleable.BaseRecyclerView_brv_text_no_result)
        tv_no_result.text = textNoResult
        if (padding != 0f) {
            rcv_data.setPadding(padding.toInt(), padding.toInt(), padding.toInt(), padding.toInt())
        } else {
            val paddingStart =
                a.getDimension(R.styleable.BaseRecyclerView_brv_padding_start, 0f)
            val paddingEnd =
                a.getDimension(R.styleable.BaseRecyclerView_brv_padding_end, 0f)
            val paddingTop =
                a.getDimension(R.styleable.BaseRecyclerView_brv_padding_top, 0f)
            val paddingBottom =
                a.getDimension(R.styleable.BaseRecyclerView_brv_padding_bottom, 0f)
            rcv_data.setPadding(
                paddingStart.toInt(),
                paddingTop.toInt(),
                paddingEnd.toInt(),
                paddingBottom.toInt()
            )
        }
        val enableRefresh =
            a.getBoolean(R.styleable.BaseRecyclerView_brv_enable_refresh, true)
        swipeRefresh.isEnabled = enableRefresh
//        swipeRefresh.setColorSchemeColors(
//            ContextCompat.getColor(context!!, R.color.colorPrimary),
//            ContextCompat.getColor(context!!, R.color.colorPrimaryDark)
//        )
    }

    fun setEnableRefresh(enableRefresh: Boolean) {
        swipeRefresh.isEnabled = enableRefresh
    }

    fun enableLoadMore(enableLoadMore: Boolean) {
        mAdapter?.enableLoadingMore(enableLoadMore)
    }

    fun enableRefresh(enableRefresh: Boolean) {
        swipeRefresh.isRefreshing = enableRefresh
    }


    fun setListLayoutManager(orientation: Int) {
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(context, orientation, false)
        rcv_data.layoutManager = layoutManager
    }

    fun setListReserveLayoutManager(orientation: Int){
        val layoutManager = LinearLayoutManager(context, orientation, false)
//        layoutManager.stackFromEnd = true
        layoutManager.reverseLayout = true
        rcv_data.layoutManager = layoutManager
    }

    fun setGridLayoutManager(spanCount: Int) {
        val layoutManager = GridLayoutManager(context, spanCount)
        layoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (mAdapter!!.getItemViewType(position) == EndlessLoadingRecyclerViewAdapter.VIEW_TYPE_LOADING) {
                    spanCount
                } else 1
            }
        }
        rcv_data.layoutManager = layoutManager
    }

    fun refresh(data: List<Any>?) {
        if (data.isNullOrEmpty()) {
            layout_no_result.visibility = View.VISIBLE
        } else {
            layout_no_result.visibility = View.GONE
            mAdapter?.refresh(data)
        }
        swipeRefresh.isRefreshing = false
    }

    fun addItem(data: List<Any>) {
        mAdapter?.hideLoadingItem()
        mAdapter?.addModels(data, false)
    }

    fun showLoadingItem() {
        mAdapter?.showLoadingItem(true)
    }

    fun setOnRefreshListener(func: () -> Unit) {
        mAdapter?.clear()
        swipeRefresh.setOnRefreshListener {
            func.invoke()
        }
        swipeRefresh.isRefreshing = false
    }

    fun setOnLoadingMoreListener(loadingMoreListener: () -> Unit) {
        mAdapter?.setLoadingMoreListener(loadingMoreListener)
    }

    fun setOnRetryLoadingMoreListener(retryLoadingMoreListener: () -> Unit) {
        mAdapter?.setOnRetryLoadingMoreListener(retryLoadingMoreListener)
    }

    fun setOnItemClickListener(onItemClickListener: (RecyclerViewAdapter<*>, RecyclerView.ViewHolder?, Int, Int) -> Unit) {
        mAdapter?.addOnItemClickListener(onItemClickListener)
    }

    fun setAdapter(adapter: EndlessLoadingRecyclerViewAdapter<*>?) {
        mAdapter = adapter
        rcv_data.adapter = adapter
    }
}