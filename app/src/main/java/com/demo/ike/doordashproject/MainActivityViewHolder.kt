package com.demo.ike.doordashproject

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_main.*


class MainActivityViewHolder(override val containerView: View) : LayoutContainer {

    private val adapter = RestaurantListAdapter()

    init {
        rv_list.layoutManager = LinearLayoutManager(containerView.context)
        rv_list.adapter = adapter
    }

    fun updateList(listItems: List<Restaurant>) {
        adapter.update(listItems)
    }

    fun showError(message: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}