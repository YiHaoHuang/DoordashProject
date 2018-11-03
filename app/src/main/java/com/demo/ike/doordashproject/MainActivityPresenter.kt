package com.demo.ike.doordashproject

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import java.util.*

private const val DOORDASH_LAT = 37.422740
private const val DOORDASH_LNG = -122.139956

class MainActivityPresenter(val retrofit: Retrofit) : LifecycleHandler<MainActivityViewHolder> {
    private var view: MainActivityViewHolder? = null
    private var disposable: Disposable? = null
    private var listItems: List<Restaurant> = Collections.emptyList()

    override fun onViewAttached(view: MainActivityViewHolder) {
        this.view = view
        disposable = retrofit.create(RestaurantListService::class.java)
            .getRestaurants(DOORDASH_LAT, DOORDASH_LNG).subscribeOn(
                Schedulers.io()
            ).observeOn(AndroidSchedulers.mainThread()).subscribe({ result ->
                                                                      listItems = result
                                                                  }, { error ->
                                                                      showError(error.message)
                                                                  })
    }

    private fun showError(message: String?) {
    }

    override fun onResume() {

    }

    override fun onPause() {
    }

    override fun onDestroyView() {
        view = null
    }

    override fun onDestroy() {
    }
}








