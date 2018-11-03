package com.demo.ike.doordashproject

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.restaurant_item_view.view.*

class RestaurantListAdapter : RecyclerView.Adapter<RestaurantListAdapter.ViewHolder>() {

    private var items: List<Restaurant> = emptyList()

    fun update(list: List<Restaurant>) {
        items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.restaurant_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(items[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Restaurant) {
            with(data) {
                Picasso.get()
                    .load(coverImgUrl)
                    .into(itemView.restaurant_image)
                itemView.restaurant_name.text = name
                itemView.restaurant_description.text = description
                itemView.restaurant_status.text = status
            }
        }
    }
}