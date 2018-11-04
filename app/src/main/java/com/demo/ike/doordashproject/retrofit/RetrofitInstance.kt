package com.demo.ike.doordashproject.retrofit

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val URL = "https://api.doordash.com"

object RetrofitInstance {

    val retrofit =
        Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()!!
}