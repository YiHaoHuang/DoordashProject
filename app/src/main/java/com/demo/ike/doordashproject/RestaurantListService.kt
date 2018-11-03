package com.demo.ike.doordashproject

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantListService {

    @GET("/v2/restaurant/")
    fun getRestaurants(
        @Query("lat") lat: Double, @Query("lng") lng: Double, @Query("offset")
        offset: Int = 0, @Query("limit") limit: Int = 50
    ): Observable<List<Restaurant>>
}