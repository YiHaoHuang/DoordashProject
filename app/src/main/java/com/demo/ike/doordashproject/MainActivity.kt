package com.demo.ike.doordashproject

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.navigation.findNavController
import com.demo.ike.doordashproject.retrofit.RestaurantListService
import com.demo.ike.doordashproject.retrofit.RetrofitInstance

class MainActivity : AppCompatActivity() {
    private lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.presenter = MainActivityPresenter(
            this.getPreferences(Context.MODE_PRIVATE),
            RetrofitInstance.retrofit.create(
                RestaurantListService::class.java
            ), findNavController(R.id.nav_host_fragment)
                .navInflater
        )
        presenter.onViewAttached(
            MainActivityView(
                this,
                window.decorView.findViewById(android.R.id.content),
                findNavController(R.id.nav_host_fragment)
            )
        )
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onBackPressed() {
        if (!presenter.onBackPressed()) {
            super.onBackPressed()
        }
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroyView()
        presenter.onDestroy()
    }
}