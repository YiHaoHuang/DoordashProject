package com.demo.ike.doordashproject

import androidx.navigation.NavInflater
import com.demo.ike.doordashproject.data.Restaurant
import com.demo.ike.doordashproject.retrofit.RestaurantListService
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor


class MainActivityPresenterTest {
    init {
        MockitoAnnotations.initMocks(this)
    }

    private val restaurantListService: RestaurantListService = mock()
    private val navInflater: NavInflater = mock()
    private lateinit var presenter: MainActivityPresenter
    private val view: MainActivityView = mock()
    private val restaurantOne: Restaurant = mock()
    private val restaurantTwo: Restaurant = mock()

    private fun createPresenter() {
        presenter = MainActivityPresenter(
            restaurantListService, navInflater
        )
    }

    @Test
    fun testShowingRecyclerViewWhenRequestSuccessful() {
        assignScheduler()
        mockAPIRequestSuccessful()
        createPresenter()
        presenter.onViewAttached(view)
        verify(view, never()).showError(any(), any())
        argumentCaptor<List<Restaurant>>().apply {
            verify(view).updateList(capture(), any())
            assertEquals(2, firstValue.size)
        }
    }

    @Test
    fun testShowingMoreItemsWhenOnLoadMoreTriggered() {
        assignScheduler()
        mockAPIRequestSuccessful()
        createPresenter()
        presenter.onViewAttached(view)
        presenter.onLoadMore()
        verify(view, never()).showError(any(), any())
        argumentCaptor<List<Restaurant>>().apply {
            verify(view, times(2)).updateList(capture(), any())
            assertEquals(4, lastValue.size)
        }
    }

    @Test
    fun testShowingErrorSnackBarWhenAPIRequestFailure() {
        assignScheduler()
        mockAPIRequestFailure()
        createPresenter()
        presenter.onViewAttached(view)
        verify(view).showError(any(), any())
    }

    private fun mockAPIRequestSuccessful() {
        whenever(
            restaurantListService.getRestaurants(
                any(),
                any(),
                any(),
                any()
            )
        ).thenReturn(Observable.just(listOf(restaurantOne, restaurantTwo)))
    }

    private fun mockAPIRequestFailure() {
        whenever(
            restaurantListService.getRestaurants(
                any(),
                any(),
                any(),
                any()
            )
        ).thenReturn(Observable.error<List<Restaurant>>(Exception("oops! something went wrong!")))
    }

    private fun assignScheduler() {
        val immediate = object : Scheduler() {
            override fun createWorker(): Scheduler.Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }
        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }
}