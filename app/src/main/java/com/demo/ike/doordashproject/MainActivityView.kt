package com.demo.ike.doordashproject

import android.support.design.widget.Snackbar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class MainActivityView(private val activity: MainActivity, override val containerView: View) :
    LayoutContainer {

    private val adapter = RestaurantListAdapter()
    private val layoutManager = LinearLayoutManager(containerView.context)
    private lateinit var scrollListener: EndlessScrollListener

    init {
        setupToolbar(containerView.toolbar)
        rv_list.layoutManager = layoutManager
        rv_list.addItemDecoration(
            DividerItemDecoration(
                activity,
                layoutManager.orientation
            )
        )
        rv_list.adapter = adapter
    }

    private fun setupToolbar(toolbar: Toolbar) {
        toolbar.title = containerView.resources.getString(R.string.discover)
        toolbar.setTitleTextColor(containerView.resources.getColor(R.color.white))
        activity.setSupportActionBar(toolbar)
    }

    fun updateList(listItems: List<Restaurant>) {
        adapter.update(listItems)
        swl_refresh.isRefreshing = false
    }

    fun showError(message: String?, onUserPressedRetry: () -> Unit = {}) {
        swl_refresh.isRefreshing = false
        val snackbar = Snackbar.make(containerView, message!!, Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction(R.string.retry) { v ->
            onUserPressedRetry()
            snackbar.dismiss()
        }
        snackbar.show()
    }

    fun updatePullToRefreshAndLoadMoreListener(
        onLoadMore: () -> Unit,
        onPullToRefresh: () -> Unit
    ) {
        scrollListener = object : EndlessScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                onLoadMore()
            }
        }
        rv_list.addOnScrollListener(scrollListener)
        swl_refresh.setOnRefreshListener { onPullToRefresh() }
    }

    fun resetScrollListener() {
        if (::scrollListener.isInitialized) {
            scrollListener.resetState()
        }
    }
}