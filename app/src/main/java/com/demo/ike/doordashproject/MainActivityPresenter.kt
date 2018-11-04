package com.demo.ike.doordashproject

import androidx.navigation.findNavController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit


private const val DOORDASH_LAT = 37.422740
private const val DOORDASH_LNG = -122.139956
private const val PAGE_SIZE = 50
private const val MAX_SIZE = 200

class MainActivityPresenter(private val activity: MainActivity, private val retrofit: Retrofit) :
    LifecycleHandler<MainActivityView> {
    private var view: MainActivityView? = null
    private var disposable: Disposable? = null
    private var listItems: MutableList<Restaurant> = ArrayList()

    override fun onViewAttached(view: MainActivityView) {
        this.view = view
        this.view?.updatePullToRefreshAndLoadMoreListener(::fetchData, ::onPullToRefresh)
        fetchData()
    }

    private fun fetchData() {
        // stop to fetch new data if the item list reach the maximum
        if (listItems.size >= MAX_SIZE) {
            return
        }
        disposable = retrofit.create(RestaurantListService::class.java)
            .getRestaurants(DOORDASH_LAT, DOORDASH_LNG, listItems.size, PAGE_SIZE)
            .subscribeOn(
                Schedulers.io()
            ).observeOn(AndroidSchedulers.mainThread()).subscribe({ result ->
                                                                      listItems.addAll(result)
                                                                      view?.updateList(
                                                                          listItems = listItems,
                                                                          onRestaurantClick = ::onRestaurantClick
                                                                      )
                                                                  }, { error ->
                                                                      view?.showError(
                                                                          message = error.message,
                                                                          onUserPressedRetry = ::fetchData
                                                                      )
                                                                  })
    }

    // clear the data and fetch the data again when Pull to refresh
    private fun onPullToRefresh() {
        listItems.clear()
        view?.resetAdapter()
        fetchData()
    }

    override fun onResume() {
    }

    private fun onRestaurantClick(id: String, name: String) {
        val graph =
            activity.findNavController(R.id.nav_host_fragment)
                .navInflater.inflate(R.navigation.restaurant_detail_nav_graph)
        graph.addDefaultArguments(
            RestaurantDetailFragment.Creator(
                id,
                name
            ).getBundle()
        )
        view?.launchFullScreenFlow(graph)
    }

    fun onBackPressed(): Boolean {
        view?.let {
            if (view!!.isShowingFullScreenFlow()) {
                view!!.closeFullScreenFlow()
                return true
            }
        }
        return false
    }

    override fun onPause() {
        disposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }

    override fun onDestroyView() {
        view = null
    }

    override fun onDestroy() {
    }
}