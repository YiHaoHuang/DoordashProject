package com.demo.ike.doordashproject

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.demo.ike.doordashproject.RetrofitInstance.retrofit
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.restaurant_detail_layout.view.*

private const val RESTAURANT_ID = "id"
private const val RESTAURANT_NAME = "name"

/**
 * this Fragment is not MVP structure, since I just want to quickly
 * implement the Navigation Architecture Component from the main activity
 * to this Full screen Fragment
 */
class RestaurantDetailFragment : Fragment() {

    private lateinit var restaurantId: String
    private lateinit var restaurantName: String
    private var disposable: Disposable? = null

    class Creator {
        private val bundle: Bundle

        constructor(bundle: Bundle) {
            this.bundle = bundle
        }

        constructor(
            restaurantId: String,
            restaurantName: String
        ) {
            bundle = Bundle()
            bundle.putString(RESTAURANT_ID, restaurantId)
            bundle.putString(RESTAURANT_NAME, restaurantName)
        }

        fun getRestaurantId(): String {
            return if (bundle.getString(RESTAURANT_ID) != null) bundle.getString(RESTAURANT_ID) else ""
        }

        fun getRestaurantName(): String {
            return if (bundle.getString(RESTAURANT_NAME) != null) bundle.getString(RESTAURANT_NAME) else ""
        }

        fun getBundle(): Bundle {
            return bundle
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(Creator(arguments!!)) {
            restaurantId = this.getRestaurantId()
            restaurantName = this.getRestaurantName()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.restaurant_detail_layout, null, false).also { view ->
            view.toolbar.title = restaurantName
            view.toolbar.setTitleTextColor(resources.getColor(R.color.white))
        }
    }

    override fun onResume() {
        super.onResume()
        disposable = retrofit.create(RestaurantListService::class.java)
            .getRestaurantsDetail(restaurantId)
            .subscribeOn(
                Schedulers.io()
            ).observeOn(AndroidSchedulers.mainThread()).subscribe({ result ->
                                                                      updateUi(result)
                                                                  }, { error ->

                                                                  })
    }

    private fun updateUi(result: RestaurantDetail) {
        with(result) {
            Picasso.get()
                .load(coverImgUrl)
                .into(view?.restaurant_image)
            view?.restaurant_address!!.text = getPrintableAddress()
            view?.restaurant_delivery_or_not!!.text =
                    if (deliveryAvailable) this@RestaurantDetailFragment.getString(R.string.yes) else this@RestaurantDetailFragment.getString(
                        R.string.no
                    )
            view?.restaurant_delivery_fee!!.text =
                    if (deliveryFee == 0) this@RestaurantDetailFragment.getString(R.string.free) else deliveryFee.toString()
            view?.restaurant_average_rating!!.text = rating.toString()
        }
    }
}