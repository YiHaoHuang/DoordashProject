package com.demo.ike.doordashproject

import android.content.SharedPreferences
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.demo.ike.doordashproject.data.Restaurant
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.restaurant_item_view.view.*

class RestaurantListAdapter(private val preferences: SharedPreferences) :
    RecyclerView.Adapter<RestaurantListAdapter.ViewHolder>() {

    private var items: List<Restaurant> = emptyList()
    private lateinit var onRestaurantClick: (String, String) -> Unit

    fun update(
        list: List<Restaurant>,
        onRestaurantClick: (String, String) -> Unit = { s: String, s1: String -> }
    ) {
        items = list
        this.onRestaurantClick = onRestaurantClick
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.restaurant_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(items[position], onRestaurantClick, preferences)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            data: Restaurant,
            onRestaurantClick: (String, String) -> Unit,
            preferences: SharedPreferences
        ) {
            with(data) {
                Picasso.get()
                    .load(coverImgUrl)
                    .into(itemView.restaurant_image)
                itemView.restaurant_name.text = name
                itemView.restaurant_description.text = description
                itemView.restaurant_status.text = status
                itemView.setOnClickListener { onRestaurantClick(id.toString(), name) }
                itemView.checkbox.setOnCheckedChangeListener(null)
                val set = HashSet<String>(preferences.getStringSet("fav", emptySet()))
                itemView.checkbox.isChecked = set.contains(id.toString())
                itemView.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        val set = HashSet<String>(preferences.getStringSet("fav", emptySet()))
                        set.add(data.id.toString())
                        preferences.edit().putStringSet("fav", set).apply()
                    } else {
                        val set = HashSet<String>(preferences.getStringSet("fav", emptySet()))
                        set.remove(data.id.toString())
                        preferences.edit().putStringSet("fav", set).apply()
                    }
                }
            }
        }
    }
}